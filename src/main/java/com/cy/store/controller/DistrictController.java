package com.cy.store.controller;

import com.cy.store.entity.District;
import com.cy.store.service.IDistrictService;
import com.cy.store.util.JsonResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * 地区类控制层
 */
@RequestMapping("/district")
@RestController
public class DistrictController extends BaseController {
    @Resource
    private IDistrictService districtService;
    @RequestMapping({"/",""})//可以传一个数组作为请求路径
    public JsonResult<List> getByParent(String parent){
        List<District> list = districtService.findByParent(parent);
        return  new JsonResult<>(OK,list);
    }

}
