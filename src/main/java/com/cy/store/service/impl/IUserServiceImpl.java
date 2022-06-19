package com.cy.store.service.impl;

import com.cy.store.entity.User;
import com.cy.store.mapper.UserMapper;
import com.cy.store.service.IUserService;
import com.cy.store.service.exception.*;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.UUID;

@Service
public class IUserServiceImpl implements IUserService {
    @Resource
    private UserMapper userMapper;


    /**
     * 执行密码加密
     * @param password 原始密码
     * @param salt  随机获取的盐值
     * @return 加密后的密文
     */
    private String getMD5Password(String password,String salt){
        for (int i=0;i<3;i++){
            /*
             * 加密规则：
             * 1、无视原始密码的强度
             * 2、使用UUID作为盐值，在原始密码的左右两侧拼接
             * 3、循环加密3次
             */
            password = DigestUtils.md5DigestAsHex((salt + password + salt).getBytes()).toUpperCase();
        }
        return password;
    }

    @Override
    public void reg(User user) {
        /* 判断用户是否已经注册*/
        User result = userMapper.findUserByUsername(user.getUsername());
        if (result!=null){
            throw new UsernameDuplicateException("用户名被占用");
        }else if (user.getUsername().equals("")||user.getPassword().equals("")){
            throw new InsertException("用户名和密码不能为空");
        }

        /*
         * 密码加密的实现：md5算法的形式：
         * （串+password+串）----md5算法进行加密，连续加载三次
         * 盐值+password+盐值----盐值就是一种随机的字符串
         */
        String oldPassword = user.getPassword();
//        获取盐值（生成一个随机的字符串）
        String salt = UUID.randomUUID().toString().toUpperCase();
//        补全数据：盐值
        user.setSalt(salt);
//        将密码和盐值作为一个整体进行加密处理
        String md5Password= getMD5Password(oldPassword,salt);
//        将加密之后的密码重新补全设置到user对象中
        user.setPassword(md5Password);

//      设置账号为可用状态 0：表示可用 1：表示不可用
        user.setIsDelete(0);
//        补全数据【四个日志字段信息】
        Date date = new Date();
        user.setCreatedUser(user.getUsername());
        user.setCreatedTime(date);
        user.setModifiedUser(user.getUsername());
        user.setModifiedTime(date);

        /*
         *        执行注册业务功能的实现
         */
        Integer rows = userMapper.insertUser(user);
        if (rows!=1){
            throw new InsertException("注册时产生未知异常，请联系管理员");
        }
    }


    @Override
    public User login(String username, String password) {
        //判断用户是否存在，若不存在则抛出用户不存在异常
        User user = userMapper.findUserByUsername(username);
        if (user==null||user.getIsDelete()==1){
            throw new UserNotFindException("用户不存在");
        }
       /*
               根据用户传递过来的密码，经过md5算法加密，验证是否与数据库中密码相匹配
               1、获取用户盐值
               2.获取用户输入的密码
               3、加密
               4、比对,如若比对错误则抛出密码不正确异常
       */
        String salt=user.getSalt();
        String md5Password = getMD5Password(password, salt);
        if (!md5Password.equals(user.getPassword())){
            throw new PasswordNotMatchException("密码不正确");
        }
//        新建一个用户，包装少量必要数据进行传递，提高系统的性能
        User result = new User();
        result.setUid(user.getUid());
        result.setUsername(user.getUsername());
        result.setAvatar(user.getAvatar());

//        将当前的用户数据返回，返回的数据是为了辅助其他页面数据展示使用（uid，username，avatar）
        return result;
    }


    @Override
    public void changePassword(Integer uid, String username, String oldPassword, String newPassword) {
//          根据uid判断用户是否存在
        User user = userMapper.findByUid(uid);
        if (user==null || user.getIsDelete()==1){
            throw new UserNotFindException("用户不存在");
        }
//       旧密码验证
        String oldMd5Password = getMD5Password(oldPassword, user.getSalt());
        if (!user.getPassword().equals(oldMd5Password)){
            throw new PasswordNotMatchException("密码错误");
        }
//        将新的密码加入到数据库中，将新的密码进行加密再去更新
        String newMd5Password = getMD5Password(newPassword, user.getSalt());

        Integer result = userMapper.updatePasswordByUid(uid, newMd5Password, username, new Date());
        if (result!=1){
            throw new UpdateException("更新数据产生未知异常");
        }
    }


    @Override
    public User getByUid(Integer uid) {
        User result = userMapper.findByUid(uid);
        if (result==null || result.getIsDelete()==1){
            throw new UserNotFindException("用户数据不存在");
        }
        User user=new User();
        user.setUsername(result.getUsername());
        user.setPhone(result.getPhone());
        user.setEmail(result.getEmail());
        user.setGender(result.getGender());
        return user;
    }

    @Override
    public void changeInfo(Integer uid, String username, User user) {
        User result = userMapper.findByUid(uid);
        if (result==null || result.getIsDelete()==1){
            throw new UserNotFindException("用户数据不存在");
        }
        user.setUid(uid);
        user.setModifiedTime(new Date());
        user.setModifiedUser(username);
        Integer rows = userMapper.updateInfoByUid(user);
        if (rows!=1){
            throw new UpdateException("更新数据时产生未知异常");
        }
    }


    @Override
    public void changeAvatar(Integer uid, String avatar, String username) {
        User user = userMapper.findByUid(uid);
        if (user==null || user.getIsDelete()==1){
            throw new UserNotFindException("用户信息不存在");
        }
        Integer rows = userMapper.updateAvatarByUid(uid, avatar, username, new Date());
        if (rows!=1){
            throw new UpdateException("更新用户头像时出现未知异常");
        }
    }
}
