package com.cy.store.mapper;

import com.cy.store.entity.User;

import java.util.Date;

public interface UserMapper {
    /**
     * 用户注册
     * @param user 用户注册的信息
     * @return 根据返回数值来确定注册是否成功（增、删、改）
     */
    Integer insertUser(User user);

    /**
     * 根据用户名来查找用户（用户名是唯一的）
     * @return 返回查找到的用户
     * @param zhangsan
     */
     User findUserByUsername(String zhangsan);

    /**
     * 根据uid修改密码
     * @return 受影响的行数
     */
     Integer updatePasswordByUid(Integer uid, String password, String modifiedUser, Date modifiedTime);

    /**
     * 根据uid来查找用户
     * @param uid
     * @return 返回找到的用户
     */
     User findByUid(Integer uid);

    /**
     * 更新用户信息
     * @param user 封装用户id和新的个人资料信息
     * @return 受影响的行数
     */
     Integer updateInfoByUid(User user);

    /**
     * 更换头像
     * @param uid
     * @param avatar
     * @param modifiedUser
     * @param modifiedTime
     * @return
     */
     Integer updateAvatarByUid(Integer uid,String avatar,String modifiedUser, Date modifiedTime);
}
