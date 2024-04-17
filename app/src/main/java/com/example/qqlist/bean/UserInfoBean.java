package com.example.qqlist.bean;

import org.litepal.crud.DataSupport;

/**
 * @Author: Paper
 */
public class   UserInfoBean extends DataSupport {

    private String name;
    private String pwd;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }
}
