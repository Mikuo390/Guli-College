package xom.yjh.msmservice.controller;

import com.yjh.commonutils.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import xom.yjh.msmservice.service.MsmService;
import xom.yjh.msmservice.utils.RandomUtil;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/eduMsm/msm")
@CrossOrigin
@Api(description ="短信测试功能")
public class MsmController {

    @Autowired
    private MsmService msmService;

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    //发送短信的方法
    @ApiOperation("发送短信的方法")
    @GetMapping("send/{phone}")
    public R sendMsm(@PathVariable String phone){
        //1.从redis获取验证码,如果获取的直接返回
        String code = redisTemplate.opsForValue().get(phone);
        if(!StringUtils.isEmpty(code)){
            return R.ok();
        }

        //2如果redis获取不到,生成随机的值,传递给阿里云进行发送
        code = RandomUtil.getFourBitRandom();
        Map<String,Object> param = new HashMap<>();
        param.put("code",code);
        //调用service发送短信方法
//        boolean isSend = msmService.send(param,phone);
        boolean isSend = true;
        if(isSend){
            //发送成功,吧发送成功的验证码放到redis里面
            //设置有效时间
            redisTemplate.opsForValue().set(phone,code,5, TimeUnit.MINUTES);
            return R.ok();
        }else {
            return R.error().message("短信发送失败");
        }
    }
}
