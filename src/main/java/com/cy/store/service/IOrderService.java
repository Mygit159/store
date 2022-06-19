package com.cy.store.service;

import com.cy.store.entity.Order;
import com.cy.store.vo.OrderItem;

import java.util.List;

/**
 * 订单业务层
 */
public interface IOrderService {
    /**
     * 创建订单
     * @param aid 地址id
     * @param uid 用户id
     * @param username 创建者
     * @param cids 物品id
     * @return 返回创建的订单对象
     */
    Order create(Integer aid, Integer uid, String username, Integer[] cids);

    /**
     * 根据订单编号查询oid订单信息
     * @param oid 订单编号
     * @param uid 用户id
     * @param username 用户名
     */
     Order findOrderInfo(Integer oid, Integer uid, String username);

    /**
     * 根据用户uid找出所有的订单oid
     * 在根据oid找出所有的订单详细项orderItem
     * @param uid
     * @return
     */
    List<OrderItem> getOrderItem(Integer uid);

    /**
     * 根据用户输入的信息模糊查找
     * @param search 用户输入的信息
     * @return 查找到的订单信息列表
     */
    List<OrderItem> find(String search,Integer uid);
}
