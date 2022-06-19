package com.cy.store.mapper;

import com.cy.store.vo.CartVO;
import com.cy.store.entity.Cart;

import java.util.Date;
import java.util.List;

/**
 * 购物车持久层方法
 */
public interface CartMapper {
    /**
     * 插入购物车的数据
      * @param cart 要插入的数据
     * @return 返回受影响的行数
     */
    Integer insert(Cart cart);

    /**
     * 更新购物车某件商品的数量
     * @param cid 购物车数据id
     * @param num 更新的数量
     * @param modifiedUser 修改者
     * @param modifiedTime 修改时间
     * @return 返回受影响的行数
     */
    Integer updateByCid(Integer cid, Integer num, String modifiedUser, Date modifiedTime);

    /**
     * 根据用户的id和商品的id来查询购物车中的数据
     * @param uid 用户id
     * @param pid 商品id
     * @return 返回查询到的购物车的数据
     */
    Cart findByUidAndPid(Integer uid,Integer pid);

    /**
     * 根据用户id显示所有加入购物车的数据
     * @param uid 用户id
     * @return 返回加入购物车数据的列表
     */
    List<CartVO> findByUid(Integer uid);

    /**
     * 根据购物车中购物数据的id值查询购物商品
     * @param cid 物品id
     * @return 返回查询到的商品信息
     */
    Cart findByCid(Integer cid);

    /**
     * 根据用户选择的商品id来获取所有的商品
     * @param cid 物品id的集合
     * @return 返回查询到的商品信息集合List
     */
    List<CartVO> findListByCid(Integer[] cid);

    /**
     * 根据用户选择的商品的标识id来删除
     * @param cid 商品的标识
     * @return 返回操作是否成功
     */
    Integer delByCid(Integer cid);

    /**
     * 根据用户选择的商品的标识id数组来删除
     * @param cid 物品id的数组
     * @return 返回受影响的行数
     */
    Integer delListByCid(Integer[] cid);
}
