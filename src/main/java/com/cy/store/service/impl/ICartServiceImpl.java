package com.cy.store.service.impl;


import com.cy.store.entity.Cart;
import com.cy.store.entity.Product;
import com.cy.store.mapper.CartMapper;
import com.cy.store.mapper.ProductMapper;
import com.cy.store.service.ICartService;
import com.cy.store.service.exception.*;
import com.cy.store.vo.CartVO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class ICartServiceImpl implements ICartService {
    //    加入购物车的功能要依赖购物车的持久层以及商品的持久层
    @Resource
    private CartMapper cartMapper;
    @Resource
    private ProductMapper productMapper;
    @Override
    public void addToCart(Integer uid, Integer pid,Integer amount, String username) {
//        判断要加入购物车的商品在购物车中是否存在
        Cart result = cartMapper.findByUidAndPid(uid, pid);
        if (result==null){
//            商品在购物车中不存在，执行插入功能
            Cart cart = new Cart();
//            补全数据
            cart.setUid(uid);
            cart.setPid(pid);
            cart.setNum(amount);
            cart.setModifiedUser(username);
            cart.setModifiedTime(new Date());
            cart.setCreatedTime(new Date());
            cart.setCreatedUser(username);
//            通过商品持久层补全商品价格信息
            Product product = productMapper.findById(pid);
            cart.setPrice(product.getPrice());
//            插入购物车
            Integer rows = cartMapper.insert(cart);
            if (rows!=1){
                throw new InsertException("加入购物车时产生未知异常");
            }
        }else {
//            商品购物车中已经存在，执行更新数量功能
            Integer num=result.getNum()+amount;
            Integer nums = cartMapper.updateByCid(result.getCid(), num, username, new Date());
            if (nums!=1){
                throw new UpdateException("更新数据时产生未知异常");
            }
        }
    }

    @Override
    public List<CartVO> getByUid(Integer uid) {
        return cartMapper.findByUid(uid);
    }

    @Override
    public Integer addNum(Integer cid, Integer uid, String username) {
        Cart cart = cartMapper.findByCid(cid);
        if (cart==null){
            throw new CartNotFindException("数据不存在");
        }
        if (!cart.getUid().equals(uid)){
            throw new AccessDeniedException("非法数据访问");
        }
        Integer num=cart.getNum()+1;
        Integer rows = cartMapper.updateByCid(cid, num, username, new Date());
        if (rows!=1){
            throw new UpdateException("更新数据失败");
        }
        return num;
    }

    @Override
    public Integer redNum(Integer cid, Integer uid, String username) {
        Cart cart = cartMapper.findByCid(cid);
        Integer num=cart.getNum();
        if (cart==null){
            throw new CartNotFindException("数据不存在");
        }
        if (!cart.getUid().equals(uid)){
            throw new AccessDeniedException("非法数据访问");
        }

        if (cart.getNum()>=2){
            num=cart.getNum()-1;
        }
        Integer rows = cartMapper.updateByCid(cid, num, username, new Date());
        if (rows!=1){
            throw new UpdateException("更新数据失败");
        }
        return num;
    }

    @Override
    public List<CartVO> getListByCid(Integer uid,Integer[] cid) {
        List<CartVO> list = cartMapper.findListByCid(cid);
        for (CartVO cartVO :list) {
            if (!cartVO.getUid().equals(uid)){//表示当前购物车数据不属于该用户
                list.remove(cartVO);
            }
            return list;
        }

        return null;
    }

    @Override
    public void movByCid(Integer cid,Integer uid) {
        Cart cart = cartMapper.findByCid(cid);
        if (cart==null){
            throw new CartNotFindException("商品信息不存在");
        }
        if (!cart.getUid().equals(uid)){
            throw new AccessDeniedException("非法数据访问");
        }
        Integer rows = cartMapper.delByCid(cid);
        if (rows!=1){
            throw new DeleteException("删除商品信息出错");
        }
    }

    @Override
    public void movListByCid(Integer[] cid,Integer uid) {
        List<CartVO> list = cartMapper.findListByCid(cid);
        for (CartVO cartVO :list) {
            if (!cartVO.getUid().equals(uid)){
                list.remove(cartVO);
            }
        }
        Integer rows = cartMapper.delListByCid(cid);
        if (rows<1){
            throw new DeleteException("删除商品信息错误");
        }
    }
}
