package com.cy.store.service.impl;

import com.cy.store.entity.Product;
import com.cy.store.mapper.ProductMapper;
import com.cy.store.service.IProductService;
import com.cy.store.service.exception.ProductNotFindException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class IProductServiceImpl implements IProductService {
    @Resource
    private ProductMapper productMapper;
    @Override
    public List<Product> findHotProduct() {
        List<Product> hotList = productMapper.findHotList();
        for (Product product :hotList) {
            product.setPriority(null);
            product.setCreatedTime(null);
            product.setCreatedUser(null);
            product.setModifiedTime(null);
            product.setModifiedUser(null);
        }
        return hotList;
    }

    @Override
    public List<Product> findNewProduct() {
        List<Product> hotList = productMapper.findNewList();
        for (Product product :hotList) {
            product.setPriority(null);
            product.setCreatedTime(null);
            product.setCreatedUser(null);
            product.setModifiedTime(null);
            product.setModifiedUser(null);
        }
        return hotList;
    }

    @Override
    public Product findById(Integer id) {
        Product product = productMapper.findById(id);
        if (product==null){
            throw new ProductNotFindException("商品未找到");
        }
        product.setPriority(null);
        product.setCreatedTime(null);
        product.setCreatedUser(null);
        product.setModifiedTime(null);
        product.setModifiedUser(null);
        return product;
    }

    @Override
    public List<Product> findBySearch(String search) {
        return productMapper.findByLike(search);
    }

    @Override
    public List<Product> findByPrice(Integer lprice, Integer hprice) {
        return productMapper.selectByPrice(lprice,hprice);
    }
}
