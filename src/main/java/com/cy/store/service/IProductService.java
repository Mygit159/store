package com.cy.store.service;

import com.cy.store.entity.Product;

import java.util.List;

/**
 * 商品操作业务层
 */
public interface IProductService {
    /**
     * 查找热销商品
     */
    List<Product> findHotProduct();

    /**
     * 查找最新上架商品
     */
    List<Product> findNewProduct();

    /**
     * 按照商品id查找商品
     * @param id 商品id
     * @return 返回查找到的商品信息
     */
    Product findById(Integer id);

    /**
     * 根据用户输入查询商品
     * @param search 用户输入
     * @return 返回查询到的所有信息
     */
    List<Product> findBySearch(String search);

    /**
     * 根据价格区间查询商品
     * @param lprice
     * @param hprice
     * @return
     */
    List<Product> findByPrice(Integer lprice, Integer hprice);
}
