package com.cy.store.service;

import com.cy.store.vo.CartVO;


import java.util.List;

/**
 * 购物车业务层
 */
public interface ICartService {
    /**
     * 加入购物车
     * @param uid 用户id
     * @param pid 商品id
     * @param amount 加入商品的数量
     * @param username 用户名
     */
    void addToCart(Integer uid, Integer pid, Integer amount,String username);

    /**
     * 根据用户id显示所有加入购物车的数据
     * @param uid 用户id
     * @return 返回加入购物车数据的列表
     */
    List<CartVO> getByUid(Integer uid);

    /**
     * 增加购物车中商品数量
     * @param cid
     * @param uid
     * @param username
     * @return 返回加入购物车的商品数量
     */
    Integer addNum(Integer cid,Integer uid,String username);

    /**
     * 减少购物车中商品数量
     * @param cid
     * @param uid
     * @param username
     * @return 返回加入购物车的商品数量
     */
    public Integer redNum(Integer cid, Integer uid, String username);

    /**
     * 根据用户选择的商品的标识id来获取所有的商品
     * @param cid 物品id的集合
     * @return 返回查询到的商品信息集合List
     */
    List<CartVO> getListByCid(Integer uid,Integer[] cid);

    /**
     * 根据用户选择的商品的标识id来删除
     * @param cid 商品的标识
     * @param uid 用户标识
     */
    void movByCid(Integer cid,Integer uid);

    /**
     * 根据用户选择的商品的标识id数组来删除
     * @param cid 物品id的数组
     * @param cid 用户id
     */
    void movListByCid(Integer[] cid,Integer uid);
}
