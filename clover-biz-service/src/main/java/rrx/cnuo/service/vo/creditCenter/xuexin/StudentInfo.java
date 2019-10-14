package rrx.cnuo.service.vo.creditCenter.xuexin;

import java.io.Serializable;

import lombok.Data;

/*******************************************************************************
 * Copyright 2018 renrenxin, Inc. All Rights Reserved
 * credit_center
 * credit.vo
 * Created by bob on 18-7-6.
 * Description:  学信信息
 *******************************************************************************/
@Data
public class StudentInfo implements Serializable {

    private static final long serialVersionUID = -7108347093060710526L;

    private String c_enter_img;// 入学照片
    private String name;// 姓名
    private String idCardNo; // 身份证号
    private String c_graduate_img;// 毕业时间
    private String c_university;// 学校
    private String c_major;// 专业
    private String c_student_begin_time;// 入学时间
    private String c_student_end_time;// 入学时间
    private String c_student_level;// 学历
    private String c_student_status;// 学生状态
    private String c_full_time;// 全日制

}