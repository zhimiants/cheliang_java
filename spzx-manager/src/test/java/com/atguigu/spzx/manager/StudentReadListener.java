package com.atguigu.spzx.manager;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;


//读excel的监听器
public class StudentReadListener extends AnalysisEventListener<StudentExcelVo> {
    @Override
    public void invoke(StudentExcelVo studentExcelVo, AnalysisContext context) {
        //读取excle文件的每行数据 读一行执行一次invoke方法
        //StudentExcelVo 参数表示读取的行
        System.out.println(studentExcelVo.getSid() +","+ studentExcelVo.getName()+","+studentExcelVo.getClazz());
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext context) {
        //文档读取完毕执行的
        System.out.println("读取完毕");
    }
}
