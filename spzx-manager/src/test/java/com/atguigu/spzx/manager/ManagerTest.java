package com.atguigu.spzx.manager;

import com.alibaba.excel.EasyExcel;
import com.atguigu.spzx.manager.mapper.SysUserMapper;
import com.atguigu.spzx.manager.service.SysRoleService;
import com.atguigu.spzx.manager.service.SysUserService;
import com.atguigu.spzx.model.dto.system.AssginRoleDto;
import com.atguigu.spzx.model.dto.system.SysUserDto;
import com.atguigu.spzx.model.entity.system.SysUser;
import io.minio.BucketExistsArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;

import io.minio.PutObjectArgs;
import io.minio.errors.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.*;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.List;


@SpringBootTest
public class ManagerTest {
    @Autowired
    SysUserMapper sysUserMapper;

    @Autowired
    SysUserService sysUserService;

    @Autowired
    SysRoleService sysRoleService;

    @Test
    public void testByReadExcel() throws FileNotFoundException {
        FileInputStream fis = new FileInputStream("C:\\Users\\lenovo\\Desktop\\学生信息表.xlsx");
        EasyExcel.read(fis,StudentExcelVo.class,new StudentReadListener())
                .sheet("武汉校区")
                .doRead();
    }

    @Test
    public void testByWriteExcel() throws FileNotFoundException {
       StudentExcelVo studentExcelVo1 = new StudentExcelVo(001,"张三","wh0911");
       StudentExcelVo studentExcelVo2 = new StudentExcelVo(002,"李四","wh1106");
       StudentExcelVo studentExcelVo3 = new StudentExcelVo(003,"王五","wh1206");
        List<StudentExcelVo> studentExcelVos = Arrays.asList(studentExcelVo1, studentExcelVo2, studentExcelVo3);
        EasyExcel.write(new FileOutputStream("C:\\Users\\lenovo\\Desktop\\学生信息表.xlsx"),StudentExcelVo.class)
               .sheet("武汉校区")
               .doWrite(studentExcelVos);
    }


    @Test
    public void testMinIO() {
        try {
            //建立minio客户端对象
            MinioClient minioClient =
                    MinioClient.builder()
                            .endpoint("http://192.168.80.128:9000") //minio的ip和端口号
                            .credentials("admin", "admin123456")
                            .build();


            boolean found =
                    minioClient.bucketExists(BucketExistsArgs.builder().bucket("spzx-sysuser-avatar").build());
            if (!found) {
                minioClient.makeBucket(MakeBucketArgs.builder().bucket("spzx-sysuser-avatar").build());
            } else {
                System.out.println("Bucket 'spzx-sysuser-avatar' already exists.");
            }

            //构建一个文件输入流
            InputStream inputStream = new FileInputStream("C:\\Users\\lenovo\\Desktop\\panhu.JPG");
            //基于文件流的方式，进行文件上传
            minioClient.putObject(
                    PutObjectArgs.builder().bucket("spzx-sysuser-avatar").object("2024/01/07/panhu.png").stream(
                                    inputStream, inputStream.available(), -1)
                            .contentType("image/jpeg") //上传的文件的类型
                            .build());


        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    @Test
    public void testByUsername() {
        SysUser admin = sysUserMapper.selectByUsername("admin");
        System.out.println(admin);
    }


    @Test
    public void testFindAll() {
        SysUserDto sysUserDto = new SysUserDto();
        sysUserService.findByPage(2, 5, sysUserDto);
    }

    @Test
    public void testAssginRole() {
        List<Long> roleIdList = Arrays.asList(1L, 5L, 9L);
        Long userId = 12L;
        AssginRoleDto assginRoleDto = new AssginRoleDto();
        assginRoleDto.setUserId(userId);
        assginRoleDto.setRoleIdList(roleIdList);

        sysRoleService.doAssign(assginRoleDto);
    }
}