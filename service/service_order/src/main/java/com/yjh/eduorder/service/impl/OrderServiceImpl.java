package com.yjh.eduorder.service.impl;

import com.yjh.commonutils.ordervo.CourseWebVoOrder;
import com.yjh.commonutils.ordervo.UcenterMemberOrder;
import com.yjh.eduorder.client.EduClient;
import com.yjh.eduorder.client.UcenterClient;
import com.yjh.eduorder.entity.Order;
import com.yjh.eduorder.mapper.OrderMapper;
import com.yjh.eduorder.service.OrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yjh.eduorder.utils.OrderNoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 订单 服务实现类
 * </p>
 *
 * @author Mikuo
 * @since 2022-03-30
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

    @Autowired
    private EduClient eduClient;

    @Autowired
    private UcenterClient ucenterClient;

    /**
     * 生成订单
     * @param courseId
     * @param memberId
     * @return
     */
    @Override
    public String createOrders(String courseId, String memberId) {
        //通过远程调用根据用户id获取用户信息
        UcenterMemberOrder userInfoOrder = ucenterClient.getUserInfoOrder(memberId);
        //通过远程调用获取课信息
        CourseWebVoOrder courseInfoOrder = eduClient.getCourseInfoOrder(courseId);

        //创建order对象,设置数据
        Order order = new Order();
        order.setOrderNo(OrderNoUtil.getOrderNo());
        order.setCourseId(courseId);
        order.setCourseTitle(courseInfoOrder.getTitle());
        order.setCourseCover(courseInfoOrder.getCover());
        order.setTeacherName(courseInfoOrder.getTeacherName());
        order.setTotalFee(courseInfoOrder.getPrice());
        order.setMemberId(memberId);
        order.setMobile(userInfoOrder.getMobile());
        order.setNickname(userInfoOrder.getNickname());

        order.setStatus(0);
        order.setPayType(1);
        int insert = baseMapper.insert(order);
        //返回顶点你好
        return order.getOrderNo();
    }
}
