package com.se.service.admin;


import com.se.config.JwtConfig;
import com.se.dao.StaffDao;
import com.se.dao.UserDao;
import com.se.model.po.StaffPO;
import com.se.model.vo.StaffVO;
import com.se.model.vo.UserVO;
import com.se.service.user.UserServiceImpl;
import com.se.service.user.UserService;
import lombok.Data;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class AdminCreateGMServiceImpl implements AdminCreateGMService {
    private final StaffDao staffDao;
    private final UserDao userDao;
    private final JwtConfig jwtConfig;

    @Autowired
    public AdminCreateGMServiceImpl(StaffDao staffDao, UserDao userDao, JwtConfig jwtConfig){
        this.staffDao = staffDao;
        this.userDao=userDao;
        this.jwtConfig=jwtConfig;
    }
    //注册
    @Override
    @Transactional
    public void register(StaffVO staffVO) throws ParseException {
        //通过lab6的userService注册账号
        UserService userService=new UserServiceImpl(userDao,jwtConfig);
        UserVO userVO=new UserVO();
        userVO.setName(staffVO.getName());
        userVO.setPassword(staffVO.getPassword());
        userVO.setRole(staffVO.getJob());
        //TODO delete
        userService.register(userVO);

        StaffPO staffPO =new StaffPO();
        BeanUtils.copyProperties(staffVO, staffPO);
        staffPO.setPassword("123456");
        Date date=staffVO.getBirth();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String dateString = sdf.format(date);
        Date date1=new SimpleDateFormat("yyyy-MM-dd").parse(dateString);
        staffPO.setBirth(date1);
        staffDao.save(staffPO);
    }

    @Override
    public void registerAll(List<StaffVO> staffVOList) throws ParseException {
        for(StaffVO staffVO: staffVOList){
            UserService userService=new UserServiceImpl(userDao,jwtConfig);
            UserVO userVO=new UserVO();
            userVO.setName(staffVO.getName());
            userVO.setPassword(staffVO.getPassword());
            userVO.setRole(staffVO.getJob());
            //TODO delete
            userService.register(userVO);

            StaffPO staffPO =new StaffPO();
            BeanUtils.copyProperties(staffVO, staffPO);
            staffPO.setPassword("123456");
            //staffPO.setJob();
            Date date=staffVO.getBirth();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String dateString = sdf.format(date);
            Date date1=new SimpleDateFormat("yyyy-MM-dd").parse(dateString);
            staffPO.setBirth(date1);
            staffDao.save(staffPO);
        }
    }

    @Override
    public List<StaffVO> getAllStaff() {
        List<StaffPO> staffPOS = staffDao.getAllStaff();
        List<StaffVO> res=new ArrayList<>();
        for (StaffPO spo: staffPOS) {
            StaffVO staffVO =new StaffVO();
            BeanUtils.copyProperties(spo, staffVO);
            res.add(staffVO);
        }
        return res;
    }

}