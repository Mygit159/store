package com.cy.store.service;

import com.cy.store.entity.Address;

import java.util.List;

/**
 * 地址模块业务接口
 */
public interface IAddressService {
    /**
     * 用户添加收货地址
     * @param uid 用户id
     * @param username  用户名
     * @param address  用户地址
     */
       void addNewAddress(Integer uid, String username, Address address);

    /**
     * 根据用户id获取用户的所有收货地址
     * @param uid 用户id
     * @return 当前用户所有收货地址
     */
    List<Address> getByUid(Integer uid);

    /**
     * 更新用户默认收货地址
     * @param aid 收货地址id
     * @param uid 用户id
     * @param username 修改者
     */
     void modifyDefault(Integer aid,Integer uid,String username);

    /**
     * 删除指定aid的地址
     * @param aid 要删除地址的id
     * @param uid 用户的id
     * @param username 修改者
     */
     void delete(Integer aid,Integer uid,String username);

    /**
     * 根据收货地址id获取地址信息
     * @param aid 地址id
     * @param uid 用户id
     * @return  返回完整地址信息用于回显数据
     */
     Address getByAid(Integer aid,Integer uid);

    /**
     * 更新收货地址信息
     * @param address 需要更新的信息
     * @param uid 用来确定用户身份
     * @param username 用来确定补全日志数据
     */
    void update(Address address,Integer uid,String username);
}
