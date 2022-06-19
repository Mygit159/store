package com.cy.store.service.impl;

import com.cy.store.entity.Address;
import com.cy.store.entity.Order;
import com.cy.store.mapper.OrderMapper;
import com.cy.store.service.IAddressService;
import com.cy.store.service.ICartService;
import com.cy.store.service.IOrderService;
import com.cy.store.service.exception.AccessDeniedException;
import com.cy.store.service.exception.InsertException;
import com.cy.store.vo.CartVO;
import com.cy.store.vo.OrderItem;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class IOrderServiceImpl implements IOrderService {
    @Resource
    private OrderMapper orderMapper;
    @Resource
    private IAddressService addressService;
    @Resource
    private ICartService cartService;

    @Transactional
    @Override
    public Order create(Integer aid, Integer uid, String username, Integer[] cids) {
//        即将下单的列表
        List<CartVO> list = cartService.getListByCid(uid, cids);
//        计算产品总价
        Long totalPrice=0L;
        for (CartVO cartVO :list) {
             totalPrice+=cartVO.getRealPrice()*cartVO.getNum();
        }
        Address address = addressService.getByAid(aid, uid);
        Order order = new Order();
        order.setUid(uid);
        order.setRecvName(address.getName());
        order.setRecvProvince(address.getProvinceName());
        order.setRecvCity(address.getCityName());
        order.setRecvArea(address.getAreaName());
        order.setRecvAddress(address.getAddress());
        order.setRecvPhone(address.getPhone());
//        补充总价、状态、创建时间
        order.setTotalPrice(totalPrice);
        order.setStatus(0);
        order.setOrderTime(new Date());
//        补全四项日志
        order.setCreatedTime(new Date());
        order.setCreatedUser(username);
        order.setModifiedTime(new Date());
        order.setModifiedUser(username);
//        插入数据
        Integer rows = orderMapper.insertOrder(order);
        if (rows!=1){
            throw new InsertException("添加订单失败");
        }
//        创建订单详细项的数据
        for (CartVO cartVO :list) {
//            创建一个订单对象
            OrderItem orderItem = new OrderItem();
            orderItem.setOid(order.getOid());
            orderItem.setPid(cartVO.getPid());
            orderItem.setTitle(cartVO.getTitle());
            orderItem.setImage(cartVO.getImage());
            orderItem.setPrice(cartVO.getPrice());
            orderItem.setNum(cartVO.getNum());
//            补全日志
            orderItem.setCreatedUser(username);
            orderItem.setModifiedUser(username);
            orderItem.setCreatedTime(new Date());
            orderItem.setModifiedTime(new Date());
//            插入订单详情页
            rows = orderMapper.insertOrderItem(orderItem);
            if (rows!=1){
                throw new InsertException("插入订单异常");
            }
        }
        return order;
    }


    @Override
    public Order findOrderInfo(Integer oid, Integer uid, String username) {
        Order order=orderMapper.selectByOid(oid);
        if (order.getUid()!=uid){
            throw new AccessDeniedException("非法用户访问");
        }
        return order;
    }

    @Override
    public List<OrderItem> getOrderItem(Integer uid) {
        Integer[] oids = orderMapper.findOidByUid(uid);
        if (oids.length==0){
            return  null;
        }
        return orderMapper.findDetailByOid(oids);
    }

    @Override
    public List<OrderItem> find(String search,Integer uid) {
        Integer[] oids = orderMapper.findOidByUid(uid);
        List<OrderItem> list = orderMapper.selectSearch(oids, search);
        System.out.println(list);
        return list;
    }

}
