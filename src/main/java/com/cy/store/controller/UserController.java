package com.cy.store.controller;

import com.cy.store.controller.ex.*;
import com.cy.store.entity.User;
import com.cy.store.service.IUserService;
import com.cy.store.util.JsonResult;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(value = "/user")
public class UserController extends BaseController {
    @Resource
    private IUserService userService;

    /**
     * 用户注册
     *
     * @param user
     * @return
     */
    @RequestMapping("/reg")
    public JsonResult<Void> reg(User user,String rePassword) {
//        倘若有异常抛出则会自动跳到父类的handleException方法中捕获
//          倘若没有异常抛出，返回200状态
        JsonResult<Void> result = new JsonResult<>();
        if (!rePassword.equals(user.getPassword())){
            result.setState(9000);
            result.setMessage("两次输入密码不一致");
        }else {
            userService.reg(user);
            result.setState(OK);
            result.setMessage("用户注册成功");
        }
        return result;
    }
 /*   @RequestMapping("/reg")
    public JsonResult<Void> reg(User user){
        JsonResult<Void> result = new JsonResult<>();
        try {
            userService.reg(user);
            result.setState(200);
            result.setMessage("用户注册成功");
        } catch (UsernameDuplicateException e) {
            result.setState(4000);
            result.setMessage("用户名被占用");
        }catch (InsertException e){
            result.setState(5000);
            result.setMessage("注册时产生未知的异常");
        }
        return result;
    }*/

    /**
     * 用户登录
     *
     * @param username
     * @param password
     * @return
     */
    @RequestMapping("/login")
    public JsonResult<User> login(String username, String password, HttpSession session, Model model) {
        User data = userService.login(username, password);
//        将用户信息添加到session中
        session.setAttribute("uid", data.getUid());
        session.setAttribute("username", data.getUsername());
        return new JsonResult<User>(OK, data);
    }

    /**
     * 修改密码
     *
     * @param oldPassword
     * @param newPassword
     * @param session
     * @return
     */
    @RequestMapping("/changePassword")
    public JsonResult<Void> changePassword(String oldPassword, String newPassword, HttpSession session) {
        Integer uid = getUidFromSession(session);
        String username = getUsernameFromSession(session);
        userService.changePassword(uid, username, oldPassword, newPassword);
        JsonResult<Void> result = new JsonResult<>(OK);
        return result;
    }

    /**
     * 根据uid获取用户数据
     *
     * @param session
     * @return
     */
    @RequestMapping("/getByUid")
    public JsonResult<User> getByUid(HttpSession session) {
        User data = userService.getByUid(getUidFromSession(session));
        return new JsonResult<User>(OK, data);
    }

    /**
     * 修改个人信息
     *
     * @param session
     * @param user
     * @return
     */
    @RequestMapping("/changeInfo")
    public JsonResult<Void> changeInfo(HttpSession session, User user) {
        Integer uid = getUidFromSession(session);
        String username = getUsernameFromSession(session);
        userService.changeInfo(uid, username, user);
        return new JsonResult<>(OK);
    }


    /*设置上传文件的最大值*/
    public static final int AVATAR_MAX_SIZE = 10 * 1024 * 1024;
    /*限制上传文件的类型*/
    public static final List<String> AVATAR_TYPE = new ArrayList<>();

    static {
        AVATAR_TYPE.add("image/jpeg");
        AVATAR_TYPE.add("image/png");
        AVATAR_TYPE.add("image/bmp");
        AVATAR_TYPE.add("image/gif");
    }


    /**
     * 修改用户头像
     * @param session session对象，用来获取用户id和用户名
     * @param file  用户上传的头像文件
     * @return  返回头像文件在电脑中路径
     */
//   MultipartFile是springMVC提供的的一个接口，这个接口为我们包装了文件类型的处理
//    获取文件类型的数据（任何类型的file都可以接收），springboot它又整合了springmvc只需要在处理请求的方法参数列表上
//    声明一个参数类型为MultipartFile的参数，然后springboot自动将传递给服务器的文件数据赋值给这个参数。
//   @RequestParam
    @RequestMapping("/changeAvatar")
    public JsonResult<String> changeAvatar(HttpSession session, @RequestParam("file") MultipartFile file) {
//        判断文件是否为null
        if (file.isEmpty()){
            throw new FileEmptyException("文件为空");
        }else if (file.getSize()>AVATAR_MAX_SIZE){
            throw new FileSizeException("文件大小超出限制");
        }
//       判断文件类型是否是我们规定的后缀形式
//        获取文件的类型  例如： image/jepg image/png
        String contentType = file.getContentType();
        if (!AVATAR_TYPE.contains(contentType)){
            throw new FileTypeException("文件类型不支持");
        }
//        上传的文件.../upload/文件.png【上传到当前项目的绝对路径下的upload文件】
        String parent = session.getServletContext().getRealPath("upload");
        /*System.out.println(parent);*/
//        File对象指定这个路径，检测这个文件是否存在
        File dir = new File(parent);
        if (!dir.exists()){//检测目录是否存在
            dir.mkdirs();//创建当前目录
        }
//        获取到这个文件的名称，使用UUID工具来生成一个新的字符串作为文件名
        String originalFilename = file.getOriginalFilename();
//        获取"."在该文件中的位置
        int index = originalFilename.lastIndexOf(".");
//        截取到文件的后缀.png
        String suffix = originalFilename.substring(index);
//        使用UUID生成随机串+文件后缀----组成文件名
        String fileName=UUID.randomUUID().toString().toUpperCase()+suffix;
//        创建一个文件来接收用户上传的头像数据
        File dest = new File(dir, fileName);
//        将参数file中的数据写入到dest这个空文件中
        try {
//           transferTo方法： 将file文件中的数据写入到dest文件中
            file.transferTo(dest);
        }catch (FileStateException e) {
            throw new FileStateException("文件状态异常");
        }catch (IOException e) {
            throw new FileUpLoadIoException("文件读写异常");
        }
        Integer uid = getUidFromSession(session);
        String username = getUsernameFromSession(session);
//        返回头像的路径 /upload/test.png
        String avatar="/upload/"+fileName;
        userService.changeAvatar(uid,avatar,username);
//        返回用户头像的路径给前端页面，将来用于头像的展示使用
        return new JsonResult<>(OK,avatar);
    }


}
