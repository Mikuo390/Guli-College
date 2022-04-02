package com.yjh.educenter.controller;

import com.google.gson.Gson;
import com.yjh.commonutils.JwtUtils;
import com.yjh.educenter.entity.UcenterMember;
import com.yjh.educenter.utils.ConstantWxUtils;
import com.yjh.educenter.service.UcenterMemberService;
import com.yjh.educenter.utils.HttpClientUtils;
import com.yjh.servicebase.exceptionhandler.GuliException;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;

@CrossOrigin
@Controller//注意这里没有配置 @RestController
@RequestMapping("/api/ucenter/wx")
@Api(description = "微信登录")
public class WxApiController {

    @Autowired
    private UcenterMemberService memberService;

    //生成微信二维码
    @GetMapping("login")
    public String genQrConnect(HttpSession session) {

        // 微信开放平台授权baseUrl
        String baseUrl = "https://open.weixin.qq.com/connect/qrconnect" +
                "?appid=%s" +
                "&redirect_uri=%s" +
                "&response_type=code" +
                "&scope=snsapi_login" +
                "&state=%s" +
                "#wechat_redirect";

        // 回调地址
        String redirectUrl = ConstantWxUtils.WX_OPEN_REDIRECT_URL; //获取业务服务器重定向地址
        try {
            redirectUrl = URLEncoder.encode(redirectUrl, "UTF-8"); //url编码
        } catch (UnsupportedEncodingException e) {
            throw new GuliException(20001, e.getMessage());
        }

        // 防止csrf攻击（跨站请求伪造攻击）
        //String state = UUID.randomUUID().toString().replaceAll("-", "");//一般情况下会使用一个随机数
        String state = "imhelen";//为了让大家能够使用我搭建的外网的微信回调跳转服务器，这里填写你在ngrok的前置域名
        System.out.println("state = " + state);

        // 采用redis等进行缓存state 使用sessionId为key 30分钟后过期，可配置
        //键："wechar-open-state-" + httpServletRequest.getSession().getId()
        //值：satte
        //过期时间：30分钟

        //生成qrcodeUrl
        String qrcodeUrl = String.format(
                baseUrl,
                ConstantWxUtils.WX_OPEN_APP_ID,
                redirectUrl,
                state);

        return "redirect:" + qrcodeUrl;
    }

    //获取扫描人信息,添加数据
    @GetMapping("callback")
    public String callback(String code,String state){
        //1.获取code值,临时票据,类似于验证码
        String baseAccessTokenUrl = "https://api.weixin.qq.com/sns/oauth2/access_token" +
                "?appid=%s" +
                "&secret=%s" +
                "&code=%s" +
                "&grant_type=authorization_code";
        String accessTokenUrl = String.format(baseAccessTokenUrl,
                ConstantWxUtils.WX_OPEN_APP_ID,
                ConstantWxUtils.WX_OPEN_APP_SECRET,
                code);
        //请求这个拼接好的地址，得到返回两个值 accsess_token和openid
        //使用httpclent发送请求，得到返回结果
        String result = null;
        try {
            //从accessTokenInfo字符串获取出来两个值accsess_token和 openid
            result = HttpClientUtils.get(accessTokenUrl);
            System.out.println("accessToken=============" + result);
        } catch (Exception e) {
            throw new GuliException(20001, "获取access_token失败");
        }

        //2.拿着code请求微信固定的地址,得到两个值 access_token 和openid
        //从accessTokenInfo字符串获取出来两个值accsess_token 和 openid
        //把accessTokenInfo字符串转换map集合，
        // 根据map里面key获取对应值使用json转换工具
        //解析json字符串
        Gson gson = new Gson();
        HashMap map = gson.fromJson(result, HashMap.class);
        String accessToken = (String)map.get("access_token");
        String openid = (String)map.get("openid");

        //获取数据
        UcenterMember member = memberService.getByOpenid(openid);

            if(member == null){
                //向数据库中插入一条记录
                //3拿着得到accsess_token 和 openid，再去请求微信提供固定的地址，获取到扫描人信息
                //访问微信的资源服务器，获取用户信息
                String baseUserInfoUrl = "https://api.weixin.qq.com/sns/userinfo" +
                        "?access_token=%s" +
                        "&openid=%s";
                String userInfoUrl = String.format(baseUserInfoUrl, accessToken, openid);
                String resultUserInfo = null;
                try {
                    resultUserInfo = HttpClientUtils.get(userInfoUrl);
                    System.out.println("resultUserInfo==========" + resultUserInfo);
                } catch (Exception e) {
                    throw new GuliException(20001, "获取用户信息失败");
                }

                //获取返回userinfo字符串扫描人信息
                HashMap<String, Object> mapUserInfo = gson.fromJson(resultUserInfo, HashMap.class);
                String nickname = (String)mapUserInfo.get("nickname");
                String headimgurl = (String)mapUserInfo.get("headimgurl");

                member = new UcenterMember();
                member.setNickname(nickname);
                member.setOpenid(openid);
                member.setAvatar(headimgurl);
                memberService.save(member);
            }
            System.out.println(member);
            String jwtToken = JwtUtils.getJwtToken(member.getId(), member.getNickname());
            System.out.println(jwtToken);
            return "redirect:http://localhost:3000?token="+jwtToken;

    }
}
