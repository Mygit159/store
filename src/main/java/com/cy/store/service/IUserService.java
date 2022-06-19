package com.cy.store.service;

import com.cy.store.entity.User;

/**
 * 用户模块业务接口
 */
public interface IUserService {
    /**
     * 用户注册功能
     * @param user 注册用户的信息
     */
    void reg(User user);

    /**
     * 用户登录功能
     * @param username 用户名
     * @param password 密码
     * @return User对象
     */
    User login(String username,String password);

    /**
     * 修改密码
     * @param uid 根据uid查看用户是否存在
     * @param username modifiedUser修改人
     * @param oldPassword 旧密码
     * @param newPassword 新密码
     */
    void changePassword(Integer uid,String username,String oldPassword,String newPassword);

    /**
     * 关于打开页面时显示当前登录的用户的信息，可能会因为用户数据不存在、用户被标记为“已删除”而无
     * 法正确的显示页面，则抛出UserNotFoundException异常。
     * 用于自动显示页面信息。
     * @param uid 用户id
     * @return 封装好的user对象
     */
    User getByUid(Integer uid);

    /**
     * 修改用户资料
     * @param uid 当前登录的用户的id
     * @param username 当前登录的用户名
     * @param user 用户的新的数据
     */
    void changeInfo(Integer uid,String username,User user);

    /**
     * 修改头像
     * @param uid 用户的id
     * @param avatar 用户上传头像的路径
     * @param username 修改者的姓名
     */
    void changeAvatar(Integer uid,String avatar,String username);
}
