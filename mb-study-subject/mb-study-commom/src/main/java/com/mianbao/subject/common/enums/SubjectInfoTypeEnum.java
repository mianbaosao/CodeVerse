package com.mianbao.subject.common.enums;

/**
 * @Description: 题目类型的枚举
 * @Author:bread
 * @Date: 2024-08-26 19:48
 */
public enum SubjectInfoTypeEnum {
    RADIO(1, "单选"),
    MULTIPLE(2, "多选"),
    JUDGE(3, "判断"),
    BRIEF(4, "简答"),
    CODE(5,"编程");

    public int code;
    public String desc;

    SubjectInfoTypeEnum(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public static SubjectInfoTypeEnum getByCode(int codeVal){
        for(SubjectInfoTypeEnum resultCodeEnum : SubjectInfoTypeEnum.values()){
            if(resultCodeEnum.code == codeVal){
                return resultCodeEnum;
            }
        }
        return null;
    }
    public int getCode() {
        return this.code;
    }

    public String getDesc() {
        return this.desc;
    }
}
