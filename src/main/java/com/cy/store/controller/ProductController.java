package com.cy.store.controller;

import com.cy.store.entity.Product;
import com.cy.store.service.IProductService;
import com.cy.store.util.JsonResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * 商品相关视图层
 */
@RequestMapping("/products")
@RestController
public class ProductController extends BaseController {
    @Resource
    private IProductService productService;

    @RequestMapping("/hotList")
    public JsonResult<List<Product>> hotList() {
        List<Product> data = productService.findHotProduct();
        return new JsonResult<>(OK, data);
    }

    @RequestMapping("/newList")
    public JsonResult<List<Product>> newList() {
        List<Product> data = productService.findNewProduct();
        return new JsonResult<>(OK, data);
    }

    @GetMapping("/{id}/details")
    public JsonResult<Product> findById(@PathVariable("id") Integer id) {
        Product data = productService.findById(id);
        return new JsonResult<>(OK, data);
    }

}
