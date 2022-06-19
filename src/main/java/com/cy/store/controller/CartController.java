package com.cy.store.controller;

import com.cy.store.service.ICartService;
import com.cy.store.util.JsonResult;
import com.cy.store.vo.CartVO;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * 购物车控制层
 */
@RequestMapping("/carts")
@RestController
public class CartController extends BaseController {
    @Resource
    private ICartService cartService;
    @RequestMapping("/addToCart")
    public JsonResult<Void> addToCart(HttpSession session,Integer pid,Integer amount){
        Integer uid = getUidFromSession(session);
        String username = getUsernameFromSession(session);
        cartService.addToCart(uid,pid,amount,username);
        return new JsonResult<>(OK);
    }
    @RequestMapping({"/",""})
    public JsonResult<List<CartVO>> getVoByUid(HttpSession session){
        Integer uid = getUidFromSession(session);
        List<CartVO> data = cartService.getByUid(uid);
        return new JsonResult<>(OK,data);
    }
    @RequestMapping("/add/num/{cid}")
    public JsonResult<Integer> addNum(@PathVariable Integer cid, HttpSession session){
        Integer uid = getUidFromSession(session);
        String username = getUsernameFromSession(session);
        Integer data = cartService.addNum(cid, uid, username);
        return new JsonResult<>(OK,data);
    }
    @RequestMapping("/red/num/{cid}")
    public JsonResult<Integer> redNum(@PathVariable Integer cid, HttpSession session){
        Integer uid = getUidFromSession(session);
        String username = getUsernameFromSession(session);
        Integer data = cartService.redNum(cid, uid, username);
        return new JsonResult<>(OK,data);
    }

    @RequestMapping("/list")
    public JsonResult<List<CartVO>> getListByCid(Integer[] cids, HttpSession session){
        Integer uid = getUidFromSession(session);
        List<CartVO> data = cartService.getListByCid(uid, cids);
        return new JsonResult<>(OK,data);
    }
    @RequestMapping("/del")
    public JsonResult<Void> delByCid(Integer cid,HttpSession session){
        Integer uid = getUidFromSession(session);
        cartService.movByCid(cid,uid);
        return new JsonResult<>(OK);
    }
    @RequestMapping("/delList")
    public JsonResult<Void> delListByCid(Integer[] cids, HttpSession session){
        Integer uid = getUidFromSession(session);
        cartService.movListByCid(cids,uid);
        return new JsonResult<>(OK);
    }
}
