package com.warsbull.system.common.entity;

import java.io.Serializable;

/**
 * 响应实体
 *
 * @author ShulicTian
 * @date 2020/01/15
 */
public class ResponseEntity<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    public static final int FAILURE_CODE = 0;
    public static final int SUCCESS_CODE = 1;

    private int code;
    private String desc;
    private T data;

    public ResponseEntity() {
    }

    public ResponseEntity(int code, String desc, T data) {
        this.code = code;
        this.desc = desc;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

}
