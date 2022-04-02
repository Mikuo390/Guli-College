package com.yjh.eduorder.service;

import com.yjh.eduorder.entity.PayLog;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 支付日志表 服务类
 * </p>
 *
 * @author Mikuo
 * @since 2022-03-30
 */
public interface PayLogService extends IService<PayLog> {

    /**
     * 生成微信支付二维码接口
     * @param orderNo
     * @return
     */
    Map createNative(String orderNo);

    /**
     * 查询订单状态
     * @param orderNo
     * @return
     */
    Map<String, String> queryPayStatus(String orderNo);

    /**
     * 添加记录,并更新订单状态
     * @param map
     */
    void updateOrderStatus(Map<String, String> map);
}
