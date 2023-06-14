package com.se.web.controller;

import com.alibaba.fastjson.JSONObject;
import com.se.auth.Authorized;
import com.se.enums.Role;
import com.se.model.vo.PerformanceVO;
import com.se.service.staff.PerformanceService;
import com.se.web.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/performance")
public class PerformanceController {
    private final PerformanceService performanceService;

    @Autowired
    public PerformanceController(PerformanceService performanceService){this.performanceService=performanceService;}

    @PostMapping("/save")
    @Authorized(roles={Role.GM})
    public Response save(@RequestBody List<PerformanceVO> performanceVOList){
        performanceService.writeIn(performanceVOList);
        return Response.buildSuccess();
    }

    @GetMapping("/findByName")
    public Response findByName(@RequestParam String name,@RequestParam String month){
        JSONObject json1 = JSONObject.parseObject(name);
        String name1=String.valueOf(json1.get("name"));
        JSONObject json2 = JSONObject.parseObject(name);
        String month1=String.valueOf(json2.get("month"));
        int month11=Integer.parseInt(month1);
        return Response.buildSuccess(performanceService.readOutByName(name1,month11));
    }

    @GetMapping("/findAll")
    public Response findAll(@RequestParam String month){
        JSONObject json1 = JSONObject.parseObject(month);
        String month1=String.valueOf(json1.get("month"));
        int month11=Integer.parseInt(month1);
        return Response.buildSuccess(performanceService.readOut(month11));
    }
}
