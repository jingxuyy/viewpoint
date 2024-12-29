package com.xu.viewpoint.service.v2.impl;

import com.mysql.cj.util.StringUtils;

import com.xu.viewpoint.dao.domain.User;
import com.xu.viewpoint.dao.domain.UserInfo;
import com.xu.viewpoint.dao.domain.constant.UserConstant;
import com.xu.viewpoint.dao.domain.exception.ConditionException;

import com.xu.viewpoint.dao.v2.UserDao;
import com.xu.viewpoint.service.util.MD5Util;
import com.xu.viewpoint.service.util.RSAUtil;
import com.xu.viewpoint.service.util.TokenUtil;
import com.xu.viewpoint.service.v2.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author: xuJing
 * @date: 2024/12/28 16:27
 */

@Service
public class UserService implements IUserService {


    @Autowired
    private UserDao userDao;



    /**
     * 用户注册
     * @param user
     */
    @Override
    public void register(User user) {

        // 1. 验证手机号是否为空
        // TODO 是否要验证手机号是否合法
        String phone = user.getPhone();
        if(StringUtils.isNullOrEmpty(phone)){
            throw new ConditionException("手机号不能为空！");
        }

        // 2. 验证手机号是否被注册
        User dbUser = this.getUserByPhone(phone);
        if(dbUser!=null){
            throw new ConditionException("该手机号已经被注册！");
        }

        // 3. 根据当前时间生成盐值
        Date date = new Date();
        String salt = String.valueOf(date.getTime());

        // 4.1 获取用户注册密码（前端已加密）
        // 4.2 将密码进行解码
        String password = user.getPassword();
        String originalPassword;
        try {
            originalPassword = RSAUtil.decrypt(password);
        }catch (Exception e){
            throw new ConditionException("注册失败，请稍后再试！");
        }

        // 5. 将解码后的密码使用MD5加密
        String mdPassword = MD5Util.sign(originalPassword, salt, "UTF-8");

        // 6. 设置密码、盐值、创建时间，存入用户表
        user.setPassword(mdPassword);
        user.setSalt(salt);
        user.setCreateTime(date);
        userDao.addUser(user);

        // 7. 创建用户信息对象，设置对应属性，存入用户信息表
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(user.getId());
        userInfo.setNick(UserConstant.DEFAULT_NICK);
        userInfo.setBirth(UserConstant.DEFAULT_BIRTH);
        userInfo.setGender(UserConstant.GENDER_UNKNOWN);
        userInfo.setCreateTime(date);
        userDao.addUserInfo(userInfo);

        // TODO 涉及到两次数据库操作，是否需要加上事务管理

//        // 添加用户默认角色
//        userAuthService.addUserDefaultRole(user.getId());

    }


    /**
     * 双令牌登录
     *
     * @param user
     */
    @Override
    public Map<String, Object> loginForDts(User user) throws Exception {

        // 1. 验证手机号是否为空
        // TODO 是否要验证手机号是否合法
        String phone = user.getPhone() == null ? "" : user.getPhone();
        String email = user.getEmail() == null ? "" : user.getEmail();
        if(StringUtils.isNullOrEmpty(phone) && StringUtils.isNullOrEmpty(email)){
            throw new ConditionException("参数异常！");
        }

        String phoneOrEmail = phone + email;


        // 2. 验证用户是否存在
        User dbUser = userDao.getUserByPhoneOrEmail(phoneOrEmail);
        if(dbUser==null){
            throw new ConditionException("当前用户不存在！");
        }

        // 3. 获取登录的密码（加密后）
        String password = user.getPassword();

        // 4. 对登陆密码进行解密
        String originalPassword;
        try {
            originalPassword = RSAUtil.decrypt(password);
        }catch (Exception e){
            throw new ConditionException("登录失败，请稍后再试！");
        }

        // 5.1 获取登录用户的盐值
        // 5.2 根据盐值和登录的解密密码进行MD5加密 与 数据库对应用户的密码比较
        String salt = dbUser.getSalt();
        String mdPassword = MD5Util.sign(originalPassword, salt, "UTF-8");
        if(!mdPassword.equals(dbUser.getPassword())){
            throw new ConditionException("账号或密码错误！");
        }
        Long userId = dbUser.getId();
        String accessToken = TokenUtil.generateToken(userId);
        String refreshToken = TokenUtil.generateRefreshToken(userId);

        HashMap<String, Object> result = new HashMap<>();
        result.put("accessToken", accessToken);
        result.put("refreshToken", refreshToken);

        // TODO 查询权限 存储到redis中
        return result;
    }



    /**
     * 根据id查询用户 详细信息
     *
     * @param userId
     */
    @Override
    public User getCurrentUser(Long userId) {

        // 1. 根据id查询用户
        User user = userDao.getUserById(userId);

        // 2. 根据id查询用户信息
        UserInfo userInfo =userDao.getUserInfoById(userId);

        // 3. 将用户信息封装到用户对象返回
        user.setUserInfo(userInfo);
        return user;
    }

    /**
     * 更新用户 基本 信息
     *
     * @param user
     */
    @Override
    public void updateUserBasicInfo(User user) throws Exception {
        // TODO 用户更新可以更新 ：1. phone  2. password  3. email
        // TODO 其中 phone 如果不为空，说明要更新phone信息 此时需要检查phone是否符合规范，同理email如此
        // TODO 如果 1 2 3 都为空 则不需要更新
        // 1. 获取需要更新的用户id
        Long id = user.getId();

        // 2. 根据id查询用户，不存在抛出异常
        User dbUser = userDao.getUserById(id);
        if(dbUser == null){
            throw new ConditionException("用户不存在！");
        }

        // 3. 获取提交的密码信息，若提交的密码不为空，说明需要修改密码
        String password = user.getPassword();
        if(!StringUtils.isNullOrEmpty(password)){
            // 3.1 将提交的密码解密，还原
            String originalPassword = RSAUtil.decrypt(password);
            // 3.2 将还原的密码加密，并设置到用户中
            String md5Password = MD5Util.sign(originalPassword, dbUser.getSalt(), "UTF-8");
            user.setPassword(md5Password);
        }

        // 4. 设置更新的时间
        user.setUpdateTime(new Date());

        // 5. 更新用户信息
        userDao.updateUser(user);
    }



    /**
     * 更新用户详细信息
     *
     * @param userInfo
     */
    @Override
    public void updateUserInfo(UserInfo userInfo) {
        userInfo.setUpdateTime(new Date());
        userDao.updateUserInfo(userInfo);
    }



    /**
     * 退出登录
     *
     * @param refreshToken
     * @param userId
     */
    @Override
    public void logout(String refreshToken, Long userId) {
        // TODO 删除redis中权限
//        userDao.deleteRefreshToken(refreshToken, userId);
    }



    // ----------------------------------------------- private -----------------------------------------------

    /**
     * 根据手机号查询用户
     * @param phone
     * @return User
     */
    private User getUserByPhone(String phone){
        return userDao.getUserByPhone(phone);
    }


    // ---------------------------------------------other-------------------------


    /**
     * 根据id查询用户基本信息
     *
     * @param id
     */
    @Override
    public User getUserById(Long id) {
        return userDao.getUserById(id);
    }


    /**
     * 根据id集合批量查询用户详细信息
     *
     * @param ids
     */
    @Override
    public List<UserInfo> getUserInfoByUserIds(Set<Long> ids) {
        return userDao.getUserInfoByUserIds(ids);
    }
}