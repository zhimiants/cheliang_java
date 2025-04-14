package com.atguigu.spzx.h5.user.service;

import com.atguigu.spzx.model.dto.h5.UserLoginDto;
import com.atguigu.spzx.model.dto.h5.UserRegisterDto;
import com.atguigu.spzx.model.entity.base.Region;
import com.atguigu.spzx.model.entity.user.UserAddress;
import com.atguigu.spzx.model.vo.h5.UserInfoVo;

import java.util.List;


public interface UserService {
    void sendCode(String phone);

    void userRegistry(UserRegisterDto userRegisterDto);

    String login(UserLoginDto userLoginDto);

    UserInfoVo getCurrentUserInfo(String token);

    List<UserAddress> findUserAddressList();

    void removeAddressById(Long addressId);

    List<Region> findByParentCode(Long parentCode);

    void addAddress(UserAddress userAddress);
}
