package com.cy.store.controller.other;

import com.cy.store.controller.BaseController;
import com.cy.store.entity.Order;
import com.cy.store.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
public class payOnlineController extends BaseController {

    @Autowired
    private IOrderService orderService;

    /**
     * 根据订单编号oid查询订单信息
     * @param oid 订单编号
     * @return 返回查找到的订单编号信息
     */
    @RequestMapping("/payOnline")
    public  String payOnline(Integer oid, HttpSession session,Model model){
        Integer uid = getUidFromSession(session);
        String username = getUsernameFromSession(session);
        Order order = orderService.findOrderInfo(oid, uid, username);
        model.addAttribute("order",order);
        return  "payment";
    }
}

