package com.atguigu.spzx.h5.user.mapper;

import com.atguigu.spzx.model.entity.base.Region;
import com.atguigu.spzx.model.entity.user.UserAddress;
import com.atguigu.spzx.model.entity.user.UserInfo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;


@Mapper
public interface UserMapper {
    UserInfo selectByUsername(String username);

    void insert(UserInfo userInfo);

    void updateLoginInfo(UserInfo userInfo);

    List<UserAddress> selectAddressListByUserId(Long userId);

    void removeAddressById(Long addressId);

    List<Region> findByParentCode(Long parentCode);

    void updateAddressDefaultToZero(Long userId);

    String getPcdName(String provinceCode, String cityCode, String districtCode);

    void addAddress(UserAddress userAddress);
}
