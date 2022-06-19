package com.cy.store.mapper;


import com.cy.store.entity.Order;
import com.cy.store.vo.OrderItem;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 订单
 */
public interface OrderMapper {
    /**
     * 插入订单
     * @param order 订单数据
     * @return 返回受影响的行数
     */
    Integer insertOrder(Order order);

    /**
     * 插入订单项
     * @param orderItem 订单项数据
     * @return 返回受影响的行数
     */
    Integer insertOrderItem(OrderItem orderItem);

    /**
     * 根据订单编号oid查找订单信息
     * @param oid
     * @return 查询到的订单
     */
    Order selectByOid(Integer oid);

    /**
     * 根据订单编号oid查找详细订单信息
     */
    List<OrderItem> findDetailByOid(Integer[] oids);

    /**
     * 根据用户uid查找属于他的订单oid
     * @param uid
     * @return
     */
    Integer[] findOidByUid(Integer uid);

    /**
     * 根据用户输入在用户的oid中查找订单信息
      * @param search
     * @return 返回查找到的信息
     */
    List<OrderItem> selectSearch(@Param("oids") Integer[] oids,@Param("search") String search);
}
