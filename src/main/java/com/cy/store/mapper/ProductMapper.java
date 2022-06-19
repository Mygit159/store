package com.cy.store.mapper;

import com.cy.store.entity.Product;

import java.util.List;

/**
 * 商品相关数据库操作
 */
public interface ProductMapper {
    /**
     * 按照优先级（售卖数量）倒序排序，查询数据库的前四件商品作为热销展示
     * @return 返回最热销的前四件商品
     */
    List<Product> findHotList();

    /**
     * 按照优先级（创建时间）倒序排序，查询数据库的前四件商品作为新到好货展示
     * @return 返回最新创建的的前四件商品
     */
    List<Product> findNewList();

    /**
     * 按照商品的id查找商品
     * @param id 商品id
     * @return 返回查找到的商品
     */
    Product findById(Integer id);

    /**
     * 根据用户输入模糊查询商品
     * @param search 用户输入信息
     * @return 查询到的所有商品
     */
    List<Product> findByLike(String search);

    /**
     * 根据价格区间查询商品
     * @param lprice
     * @param hprice
     * @return
     */
    List<Product> selectByPrice(Integer lprice, Integer hprice);
}
