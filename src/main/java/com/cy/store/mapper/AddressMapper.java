package com.cy.store.mapper;

import com.cy.store.entity.Address;

import java.util.Date;
import java.util.List;

/**
 * 收货地址
 */
public interface AddressMapper {
    /**
     * 添加收货地址
     * @param address 收货地址
     * @return 受影响的行数
     */
    Integer insertAddress(Address address);

    /**
     * 根据用户的id统计收货地址数量
     * @Param uid 用户的id
     * @return  返回当前用户收货地址的数量
     */
    Integer countAddressByUid(Integer uid);

    /**
     * 根据用户的id查找用户的所有收货地址
     * @param uid 用户id
     * @return 返回用户的所有收货地址
     */
    List<Address> findByUid(Integer uid);

    /**
     * 根据aid查询收货地址
     * @param aid 收货地址aid
     * @return 返回收货地址数据，如果没有找到则返回null值
     */
    Address findByAid(Integer aid);

    /**
     * 根据用户的uid值来修改用户的收货地址为非默认
     * @param uid 用户的id值
     * @return 返回受影响的行数
     */
    Integer updateNonDefault(Integer uid);

    /**
     * 根据aid来设置用户默认收货地址
     * @param aid 收货地址的id
     * @param modifiedUser 修改人
     * @param modifiedTime 修改时间
     * @return 返回受影响的行数
     */
    Integer updateDefaultByAid(Integer aid, String modifiedUser, Date modifiedTime);

    /**
     * 根据收货地址id删除地址
     * @param aid 收货地址id
     * @return 返回受影响的行数
     */
    Integer deleteByAid(Integer aid);

    /**
     * 查找出当前用户最后一次修改的数据
     * uid 用户id
     * @return 返回地址数据
     */
    Address findLastModified(Integer uid);

    /**
     * 根据收货地址id更新地址
     * @param address 更新的地址信息
     * @return 受影响的行数
     */
    Integer updateInfoByAid(Address address);
}
