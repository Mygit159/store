package com.cy.store.controller;

import com.cy.store.entity.Order;
import com.cy.store.service.IOrderService;
import com.cy.store.util.JsonResult;
import com.cy.store.vo.OrderItem;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * 订单控制层
 */
@RestController
@RequestMapping("/orders")
public class OrderController extends BaseController {
    @Resource
    private IOrderService orderService;

    @RequestMapping("/create")
    public JsonResult<Order> create(Integer aid, Integer[] cids, HttpSession session){
        Integer uid = getUidFromSession(session);
        String username = getUsernameFromSession(session);
        Order data = orderService.create(aid, uid, username, cids);
        return new JsonResult<>(OK,data);
    }

    @RequestMapping("/findItem")
    public JsonResult<OrderItem> findDetailsOrders(HttpSession session){
        Integer uid = getUidFromSession(session);
        List<OrderItem> OrderItems=orderService.getOrderItem(uid);
        return new JsonResult(OK,OrderItems);
    }

    @RequestMapping("/find")
    public JsonResult<OrderItem> find(HttpSession session,String search){
        Integer uid = getUidFromSession(session);
        List<OrderItem> list=orderService.find(search,uid);
        return new JsonResult(OK,list);
    }

}
