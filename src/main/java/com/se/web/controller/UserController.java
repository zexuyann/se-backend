package com.se.web.controller;

import com.alibaba.fastjson.JSONObject;
import com.se.dao.UserDao;
import com.se.web.Response;
import com.se.config.JwtConfig;
import com.se.model.vo.UserVO;
import com.se.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping(path = "/user")
public class UserController {

    private final UserDao userDao;

    private JwtConfig jwtConfig;

    private UserService userService;

    @Autowired
    public UserController(UserDao userDao, JwtConfig jwtConfig, UserService userService) {
        this.userDao = userDao;
        this.jwtConfig = jwtConfig;
        this.userService = userService;
    }

    @PostMapping("/login")
    public Response userLogin(@RequestBody UserVO userVO) {

        return Response.buildSuccess(userService.login(userVO));
    }

    @PostMapping("/register")
    public Response userRegister(@RequestBody UserVO userVO) {
        userService.register(userVO);
        return Response.buildSuccess();
    }

    @GetMapping("/auth")
    public Response userAuth(@RequestParam(name = "token") String token) {
        return Response.buildSuccess(userService.auth(token));
    }

    @PostMapping("/edit")
    public Response userEdit(@RequestBody String name,@RequestBody String oldPw,@RequestBody String newPw) {
        JSONObject json1 = JSONObject.parseObject(name);
        String name1=String.valueOf(json1.get("name"));
        JSONObject json2 = JSONObject.parseObject(oldPw);
        String oldPw1=String.valueOf(json2.get("oldPassword"));
        JSONObject json3 = JSONObject.parseObject(newPw);
        String newPw1=String.valueOf(json3.get("newPassword"));
        userService.editPw(name1,oldPw1,newPw1);
        return Response.buildSuccess();
    }
}
