package com.cy.store.controller;

import com.cy.store.entity.Address;
import com.cy.store.service.IAddressService;
import com.cy.store.util.JsonResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * 用户添加收货地址控制层
 */
@RequestMapping("/address")
@RestController
public class AddressController extends BaseController{
    @Resource
    private IAddressService addressService;
    @RequestMapping("/addNewAddress")
    public JsonResult<Void> addNewAddress(Address address,HttpSession session){
        Integer uid = getUidFromSession(session);
        String username = getUsernameFromSession(session);
        addressService.addNewAddress(uid,username,address);
        return new JsonResult<>(OK);
    }

    @RequestMapping({"/",""})
    public JsonResult<List<Address>> getByUid(HttpSession session){
        Integer uid = getUidFromSession(session);
        List<Address> data = addressService.getByUid(uid);
        return  new JsonResult<>(OK,data);
    }

//    Restful请求风格
    @RequestMapping("/setDefault/{aid}")
    public JsonResult<Void> setDefault(@PathVariable("aid") Integer  aid, HttpSession session){
        Integer uid = getUidFromSession(session);
        String username = getUsernameFromSession(session);
        addressService.modifyDefault(aid,uid,username);
        return  new JsonResult<>(OK);
    }
    @RequestMapping("/delete/{aid}")
    public JsonResult<Void> delete(@PathVariable("aid") Integer  aid, HttpSession session){
        Integer uid = getUidFromSession(session);
        String username = getUsernameFromSession(session);
        addressService.delete(aid,uid,username);
        return  new JsonResult<>(OK);
    }

    @RequestMapping("/updateAddressInfo/{aid}")
    public JsonResult<Void> updateAddressInfo(Address address,HttpSession session){
        Integer uid = getUidFromSession(session);
        String username = getUsernameFromSession(session);
        addressService.update(address,uid,username);
        return  new JsonResult<>(OK);
    }
}
