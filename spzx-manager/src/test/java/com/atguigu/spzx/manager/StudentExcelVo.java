package com.atguigu.spzx.manager;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class StudentExcelVo {
    //index表示第几列,value表示excel的表头
    @ExcelProperty(index = 0,value = "学生学号")
    private Integer sid;

    @ExcelProperty(index = 1,value = "姓名")
    private String name;

    @ExcelProperty(index = 2,value = "学生班级")
    private String clazz;
}
