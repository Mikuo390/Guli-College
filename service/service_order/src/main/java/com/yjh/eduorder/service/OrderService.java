package com.yjh.eduorder.service;

import com.yjh.eduorder.entity.Order;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 订单 服务类
 * </p>
 *
 * @author Mikuo
 * @since 2022-03-30
 */
public interface OrderService extends IService<Order> {

    /**
     * 生成订单
     * @param courseId
     * @param memberId
     * @return
     */
    String createOrders(String courseId, String memberId);
}
