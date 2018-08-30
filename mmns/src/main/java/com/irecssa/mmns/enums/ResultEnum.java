package com.irecssa.mmns.enums;

/**
 * Created by Ma.li.ran on 2017/09/23 17:31
 */
public enum ResultEnum {
    UNKNOWN_ERROR(-1,"UNKNOWN_ERROR"),
    SUCCESS(0,"SUCCESS"),
    SAVEFAILED(1,"Sql Exception，Operation Database Failed"),
    ARGSISNULL(2,"Tags parameter is null"),
    FILEISNULL(4000,"The upload file is empty"),
    SIGLEFILEBEYONDMAXSIZE(4001,"The single file exceeds the maximum limit"),
    FILEBEYONDMAXSIZE(4002,"The total documents exceed the maximum limit"),
    FILETYPEERROR(4003,"File type error or partial file is empty"),
    SAVEFILEEXCEPTION(4004,"The file holds mall exceptions"),
    PRODUCTTYPEISEXIST(3000,"The product type is exist"),
;
    private Integer code;
    private String msg;

    ResultEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
