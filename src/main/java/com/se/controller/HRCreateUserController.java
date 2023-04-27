package com.se.controller;


import com.se.auth.Authorized;
import com.se.enums.Role;
import com.se.model.vo.StuffVO;
import com.se.service.HRCreateUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/HR-create-user")
//此处为通过HR新建员工账户的controller
public class HRCreateUserController {
    private final HRCreateUserService hrCreateUserService;
    @Autowired
    public HRCreateUserController(HRCreateUserService hrCreateUserService){
        this.hrCreateUserService=hrCreateUserService;
    }

    @Authorized(roles = {Role.HR,Role.ADMIN})
    @PostMapping(value = "/create")
    public Response createNewUser(@RequestBody StuffVO stuffVO) {
        hrCreateUserService.register(stuffVO);
        return Response.buildSuccess();
    }

    @Authorized(roles = {Role.HR,Role.ADMIN})
    @PostMapping(value = "/showAll")
    public Response getAllStuff() {
        return Response.buildSuccess(hrCreateUserService.getAllStuff());
    }

}