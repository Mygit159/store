package com.cy.store.controller;

import com.cy.store.controller.ex.*;
import com.cy.store.service.exception.*;
import com.cy.store.util.JsonResult;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpSession;

/**
 * 统一处理异常
 */
@ControllerAdvice
public class BaseController {
    public static final  int OK=200;

    /**
     * 请求处理方法，这个方法的返回值就是需要传递给前端的数据
     * 自动将异常对象传递给此方法的参数列表上
     * 当项目中产生了异常，被统一拦截到此方法中，这个方法此时就充当于请求处理方法，方法的返回值直接给到前端
     * @return 返回值直接返回给前端
     *
     * @ExceptionHandler注解：用于统一处理方法抛出的异常。当我们使用这个注解时，需要定义一个
     * 异常的处理方法，再给这个方法加上@ExceptionHandler注解，这个方法就会处理类中其他方法
     * （被@RequestMapping注解）抛出的异常。@ExceptionHandler注解中可以添加参数，参数是某
     * 个异常类的class，代表这个方法专门处理该类异常。
     */
    @ExceptionHandler({ServiceException.class, FileUpLoadException.class})
    public JsonResult<Void> handleException(Throwable e){
        JsonResult<Void> result = new JsonResult<>(e);
        if (e instanceof UsernameDuplicateException){
            result.setState(4000);
            result.setMessage("用户名已经被占用");
        }else if (e instanceof InsertException){
            result.setState(5000);
            result.setMessage("插入数据时产生未知的异常");
        }else if (e instanceof UserNotFindException){
            result.setState(5001);
            result.setMessage("用户不存在");
        }else if (e instanceof PasswordNotMatchException){
            result.setState(5002);
            result.setMessage("密码不正确");
        }else if (e instanceof UpdateException){
            result.setState(5003);
            result.setMessage("更新数据时产生未知异常");
        }else if (e instanceof FileEmptyException){
            result.setState(6001);
            result.setMessage("上传头像为空异常");
        }else if (e instanceof FileSizeException){
            result.setState(6002);
            result.setMessage("上传文件大小异常");
        }else if (e instanceof FileStateException){
            result.setState(6003);
            result.setMessage("上传文件状态异常");
        }else if (e instanceof FileTypeException){
            result.setState(6004);
            result.setMessage("上传文件类型异常");
        }else if (e instanceof FileUpLoadIoException){
            result.setState(6005);
            result.setMessage("上传文件数据异常");
        }else if (e instanceof AddressCountLimitException){
            result.setState(7001);
            result.setMessage("用户收货地址超出上限的异常");
        }else if (e instanceof AddressNotFoundException){
            result.setState(8001);
            result.setMessage("收货地址不存在异常");
        }else if (e instanceof AccessDeniedException){
            result.setState(8002);
            result.setMessage("非法数据访问异常");
        }else if (e instanceof DeleteException){
            result.setState(8003);
            result.setMessage("删除数据异常");
        }else if (e instanceof ProductNotFindException){
            result.setState(8004);
            result.setMessage("商品数据不存在异常");
        }else if (e instanceof CartNotFindException){
            result.setState(8005);
            result.setMessage("购物车数据不存在");
        }
        return result;
    }

    /**
     * 获取session对象中的uid
     * @param session session对象
     * @return 当前登录用户的uid值
     */
    protected final Integer getUidFromSession(HttpSession session){
       return Integer.valueOf(session.getAttribute("uid").toString());
    }

    /**
     * 获取当前对象的usernamem值
     * @param session session对象
     * @return 当前登录用户的用户名
     */
    protected final String getUsernameFromSession(HttpSession session){
        return session.getAttribute("username").toString();
    }
}
