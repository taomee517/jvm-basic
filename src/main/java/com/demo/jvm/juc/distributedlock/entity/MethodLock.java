package com.demo.jvm.juc.distributedlock.entity;

import lombok.Data;

import java.util.Date;

@Data
public class MethodLock {
    private Integer id;

    private String methodName;

    private Boolean state;

    private Date updateTime;

    private Integer version;
}