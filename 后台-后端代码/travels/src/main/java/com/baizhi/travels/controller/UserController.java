package com.baizhi.travels.controller;

import com.baizhi.travels.entity.Result;
import com.baizhi.travels.entity.User;
import com.baizhi.travels.service.UserService;
import com.baizhi.travels.utils.CreateImageCode;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Base64Utils;
import org.springframework.web.bind.annotation.*;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user")
@CrossOrigin //允许跨域
@Slf4j
public class UserController {

    @Autowired
    private UserService userService;



    @RequestMapping("/login")
    public Result login(@RequestBody User user,HttpServletRequest request) {
        Result result = new Result();
        log.info("user: " + user);
        try {
            User userDB = userService.login(user);
            //登录成功之后保存用户的标记 ServletContext application Redis userid userdb
            request.getServletContext().setAttribute(userDB.getId(),userDB);
            result.setMsg("登录成功~~").setUserId(userDB.getId());
        } catch (Exception e) {
            result.setState(false).setMsg(e.getMessage());
        }
        return result;
    }

    /**
     * 用户注册
     *
     * @param code
     * @param user
     * @return
     */
    @PostMapping("register")
    public Result register(String code, String key, @RequestBody User user, HttpServletRequest request) {
        Result result = new Result();
        log.info("接收的验证码: " + code);
        log.info("接收的验证码的key: " + key);
        log.info("接收到user对象: " + user);
        //验证验证码
        String keyCode = (String) request.getServletContext().getAttribute(key);
        log.info(keyCode);
        try {
            if (code.equalsIgnoreCase(keyCode)) {
                //注册用户
                userService.register(user);
                result.setMsg("注册成功!!!");
            } else {
                throw new RuntimeException("验证码错误!!!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            result.setMsg(e.getMessage()).setState(false);
        }
        return result;
    }

    /**
     * 生成验证码
     *
     * @throws IOException
     */
    @GetMapping("getImage")
    public Map<String, String> getImage(HttpServletRequest request) throws IOException {
        Map<String, String> result = new HashMap<>();
        CreateImageCode createImageCode = new CreateImageCode();
        //获取验证码
        String securityCode = createImageCode.getCode();
        //验证码存入session
        String key = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        request.getServletContext().setAttribute(key, securityCode);
        //生成图片
        BufferedImage image = createImageCode.getBuffImg();
        //进行base64编码
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ImageIO.write(image, "png", bos);
        String string = Base64Utils.encodeToString(bos.toByteArray());
        result.put("key", key);
        result.put("image", string);
        return result;
    }

}
