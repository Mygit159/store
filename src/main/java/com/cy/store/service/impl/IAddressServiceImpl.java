package com.cy.store.service.impl;

import com.cy.store.entity.Address;
import com.cy.store.mapper.AddressMapper;
import com.cy.store.mapper.DistrictMapper;
import com.cy.store.service.IAddressService;
import com.cy.store.service.exception.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.xml.crypto.Data;
import java.util.Date;
import java.util.List;

/**
 * 新增收货地址的实现类
 */
@Service
public class IAddressServiceImpl implements IAddressService {
    @Resource
    private AddressMapper addressMapper;
//    调用地区mapper，来获取地区相关的数据封装到地址中
    @Resource
    private DistrictMapper districtMapper;
   @Value("${user.address.max-count}")
    private Integer maxCount;

    @Override
    public List<Address> getByUid(Integer uid) {
        List<Address> list = addressMapper.findByUid(uid);
        for (Address address :list) {
//            address.setAid(null);
//            address.setUid(null);
            address.setProvinceCode(null);
            address.setCityCode(null);
            address.setAreaCode(null);
            address.setTel(null);
            address.setIsDefault(null);
            address.setModifiedTime(null);
            address.setModifiedUser(null);
            address.setCreatedTime(null);
            address.setCreatedUser(null);
        }
        return list;
    }


    @Override
    public void addNewAddress(Integer uid, String username, Address address) {
//        判断用户地址是否超出规定数量
        Integer rows = addressMapper.countAddressByUid(uid);
        if (rows>=maxCount){
            throw new AddressCountLimitException("收货地址数量超过上限");
        }
//        补全省市区
        String provinceName=districtMapper.findDistrictByCode(address.getProvinceCode());
        String cityName=districtMapper.findDistrictByCode(address.getCityCode());
        String areaName=districtMapper.findDistrictByCode(address.getAreaCode());

        address.setProvinceName(provinceName);
        address.setCityName(cityName);
        address.setAreaName(areaName);
//        补全 uid、isDefault
        address.setUid(uid);
        Integer isDefault=rows==0? 1:0;
        address.setIsDefault(isDefault);

//      补全四项日志
        address.setCreatedUser(username);
        address.setCreatedTime(new Date());
        address.setModifiedUser(username);
        address.setModifiedTime(new Date());

        Integer result = addressMapper.insertAddress(address);
        if (result!=1){
            throw new InsertException("添加用户地址时发生未知异常");
        }
    }

    @Override
    public void modifyDefault(Integer aid, Integer uid, String username) {
//        根据用户传递过来的aid判断数据库中该地址是否存在
        Address result = addressMapper.findByAid(aid);
        if (result ==null){
            throw new AddressNotFoundException("收货地址不存在");
        }
//        检测当前获取到的收货地址数据的归属
        if (!result.getUid().equals(uid)){
            throw new AccessDeniedException("非法数据访问");
        }

//        将当前用户的所有收货地址设为非默认
        Integer rows = addressMapper.updateNonDefault(uid);
        if (rows<1){
            throw new UpdateException("更新数据产生未知异常");
        }
//        将用户选择的某条收货地址设置为默认收货地址
        Integer num = addressMapper.updateDefaultByAid(aid,username,new Date());
        if (num!=1){
            throw new UpdateException("更新数据产生未知异常");
        }
    }

    @Override
    public void delete(Integer aid, Integer uid, String username) {
//      判断当前aid是否存在
        Address address = addressMapper.findByAid(aid);
        if (address==null){
            throw new AddressNotFoundException("收货地址数据不存在");
        }
//       检查当前获取到是数据的归属
        if (!address.getUid().equals(uid)){
            throw new AccessDeniedException("非法数据访问");
        }
//        删除指定aid的地址
        Integer rows = addressMapper.deleteByAid(aid);
        if (rows!=1){
            throw new DeleteException("删除数据产生未知的异常");
        }
//        根据用户id，判断是否还存在地址（如果不存在则直接退出，不在进行后续设置默认地址的问题）
        Integer result = addressMapper.countAddressByUid(uid);
        if (result==0){
            return;
        }
//        判断用户删除的收货地址是否为默认收货地址(是默认收货地址且删除的不是最后一条地址则更改最后一次更新的数据为默认收货地址)
         if (address.getIsDefault()==1){
//             获取最后一次更新的收货地址
             Address lastModified = addressMapper.findLastModified(uid);
//             更改这条数据为默认收货地址
             Integer num = addressMapper.updateDefaultByAid(lastModified.getAid(), username, new Date());
             if (num!=1){
                 throw new UpdateException("更新数据时产生未知异常");
             }
         }
    }

    @Override
    public Address getByAid(Integer aid,Integer uid) {
//      判断当前aid是否存在
        Address address = addressMapper.findByAid(aid);
        if (address==null){
            throw new AddressNotFoundException("收货地址数据不存在");
        }
        if (!address.getUid().equals(uid)){
            throw new AccessDeniedException("非法数据访问");
        }
        address.setCreatedUser(null);
        address.setCreatedTime(null);
        address.setModifiedUser(null);
        address.setModifiedTime(null);
        return address;
    }

    @Override
    public void update(Address address,Integer uid,String username) {
        //      判断当前aid是否存在
        Address addr = addressMapper.findByAid(address.getAid());
        if (addr==null){
            throw new AddressNotFoundException("收货地址数据不存在");
        }
//       检查当前获取到是数据的归属
        if (!addr.getUid().equals(uid)){
            throw new AccessDeniedException("非法数据访问");
        }

//        补全省市区
        String provinceName=districtMapper.findDistrictByCode(address.getProvinceCode());
        String cityName=districtMapper.findDistrictByCode(address.getCityCode());
        String areaName=districtMapper.findDistrictByCode(address.getAreaCode());

        address.setProvinceName(provinceName);
        address.setCityName(cityName);
        address.setAreaName(areaName);
//        补全日志信息
        address.setCreatedTime(new Date());
        address.setModifiedTime(new Date());
        address.setModifiedUser(username);
        address.setCreatedUser(username);

//      避免更新信息，更换默认收货地址
        if (addr.getIsDefault()!=1){
            address.setIsDefault(0);
        }else{
            address.setIsDefault(1);
        }
//       更新指定aid的收货地址
        Integer rows = addressMapper.updateInfoByAid(address);
        if (rows!=1){
            throw new UpdateException("更新收货地址时产生未知的异常!");
        }
    }
}
