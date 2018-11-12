package com.zhangyuwei.people.entities;

import java.util.Date;

public class People {
    int pid;
    String pname;
    String pphone;
    String pemail;
    String ppassword;
    Date ptime;

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getPphone() {
        return pphone;
    }

    public void setPphone(String pphone) {
        this.pphone = pphone;
    }

    public String getPemail() {
        return pemail;
    }

    public void setPemail(String pemail) {
        this.pemail = pemail;
    }

    public String getPpassword() {
        return ppassword;
    }

    public void setPpassword(String ppassword) {
        this.ppassword = ppassword;
    }

    public Date getPtime() {
        return ptime;
    }

    public void setPtime(Date ptime) {
        this.ptime = ptime;
    }

    @Override
    public String toString() {
        return "People{" +
                "pid=" + pid +
                ", pname='" + pname + '\'' +
                ", pphone='" + pphone + '\'' +
                ", pemail='" + pemail + '\'' +
                ", ppassword='" + ppassword + '\'' +
                ", ptime=" + ptime +
                '}';
    }
}
