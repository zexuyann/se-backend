package com.se.service;



import com.se.config.JwtConfig;
import com.se.dao.StuffDao;
import com.se.dao.UserDao;
import com.se.model.po.StuffPO;
import com.se.model.vo.StuffVO;
import com.se.model.vo.UserVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class HRCreateUserServiceImpl implements HRCreateUserService {
    private final StuffDao stuffDao;
    private final UserDao userDao;
    private final JwtConfig jwtConfig;

    @Autowired
    public HRCreateUserServiceImpl(StuffDao stuffDao, UserDao userDao, JwtConfig jwtConfig){
        this.stuffDao =stuffDao;
        this.userDao=userDao;
        this.jwtConfig=jwtConfig;
    }
    //注册
    @Override
    @Transactional
    public void register(StuffVO stuffVO){
        System.out.println(stuffVO.getJob());
        //通过lab6的userService注册账号
        UserService userService=new UserServiceImpl(userDao,jwtConfig);
        UserVO userVO=new UserVO();
        userVO.setName(stuffVO.getName());
        userVO.setPassword(stuffVO.getPassword());
        userVO.setRole(stuffVO.getJob());
        //TODO delete
//        System.out.println();
//        System.out.println(userVO.getName());
//        System.out.println(userVO.getPassword());
//        System.out.println(userVO.getRole());
//        System.out.println();
        userService.register(userVO);
        //通过rCreateUserDao建立一张表，记录lab6的userService没有记录的属性
        StuffPO stuffPO=new StuffPO();
        BeanUtils.copyProperties(stuffVO, stuffPO);
        //为了测试方便，我们设定该员工初始打卡次数为15次
        stuffPO.setCheckIn(15);
        stuffDao.save(stuffPO);
    }

    @Override
    public List<StuffVO> getAllStuff() {
        List<StuffPO> stuffPOS= stuffDao.getAllStuff();
        List<StuffVO> res=new ArrayList<>();
        for (StuffPO spo:stuffPOS) {
            StuffVO stuffVO=new StuffVO();
            BeanUtils.copyProperties(spo,stuffVO);
            res.add(stuffVO);
        }
        return res;
    }

}
