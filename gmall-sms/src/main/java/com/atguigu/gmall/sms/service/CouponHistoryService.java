package com.atguigu.gmall.sms.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.atguigu.gmall.sms.entity.CouponHistoryEntity;
import com.atguigu.core.bean.PageVo;
import com.atguigu.core.bean.QueryCondition;


/**
 * 优惠券领取历史记录
 *
 * @author zhaiyadong
 * @email lxf@atguigu.com
 * @date 2020-01-06 20:21:01
 */
public interface CouponHistoryService extends IService<CouponHistoryEntity> {

    PageVo queryPage(QueryCondition params);
}

