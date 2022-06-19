package com.cy.store.controller.other;

import com.cy.store.controller.BaseController;
import com.cy.store.entity.Product;
import com.cy.store.service.IProductService;
import com.cy.store.util.JsonResult;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class SearchProsController extends BaseController {
    @Resource
    private IProductService productService;

    @RequestMapping("/search")
    public String findBySearch(String search, HttpServletRequest request){
        List<Product> productList = productService.findBySearch(search);
        request.setAttribute("productList",productList);
        return "search";
    }

    @RequestMapping("/searchByPrice")
    public String searchByPrice(Integer lprice,Integer hprice,HttpServletRequest request){
        List<Product> productList=productService.findByPrice(lprice, hprice);
        request.setAttribute("productList",productList);
        return "search";
    }
}
