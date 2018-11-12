package com.zhangyuwei.people.commons.util;

public class MD5UtilTest {
    /**
     * MD5加密
     * MD5验证
     * */
    public static void main(String[] args) {
        String password=MD5Utils.getMD5String("736375819");
        System.out.println(password);
        if(MD5Utils.isEqualsToMd5("736375819",password)){
            System.out.println("匹配成功！");
        }else{
            System.out.println("匹配失败!");
        }
    }
}
