//package com.xu.viewpoint.service.impl;
//
//import com.xu.viewpoint.dao.UserCoinDao;
//import com.xu.viewpoint.service.UserCoinService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.Date;
//
///**
// * @author: xuJing
// * @date: 2024/12/5 19:48
// */
//
//@Service
//public class UserCoinServiceImpl implements UserCoinService {
//
//    @Autowired
//    private UserCoinDao userCoinDao;
//
//    /**
//     * 根据用户id查询，当前用户总的硬币数
//     *
//     * @param userId
//     */
//    @Override
//    public Integer getUserCoinsAmount(Long userId) {
//        return userCoinDao.getUserCoinsAmount(userId);
//    }
//
//
//    /**
//     * 修改用户硬币数
//     *
//     * @param userId
//     * @param amount
//     */
//    @Override
//    public Integer updateUserCoinsAmount(Long userId, Integer amount) {
//        Date date = new Date();
//        return userCoinDao.updateUserCoinsAmount(userId, amount, date);
//    }
//}
