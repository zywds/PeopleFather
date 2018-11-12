package com.zhangyuwei.people.dao;

import com.zhangyuwei.people.commons.util.MD5Utils;
import com.zhangyuwei.people.entities.People;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@ContextConfiguration(locations = { "classpath:applicationContext.xml"})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
@Rollback
public class peopleDaoTest {
    @Autowired
    IpeopleDao dao;
    /*查询所有的用户*/
    @Test
    public void selectAllPeople(){
        System.out.println(dao.selectAllPeople());
    }
    /*登录*/
    @Test
    public void selectByNameandPassword(){
        People people=new People();
        people.setPname("65");
        people.setPpassword("5b1b68a9abf4d2cd155c81a9225fd158");
        if(dao.selectByNameandPassword(people).size()>0){
            System.out.println("ok");
        }
    }
    /*添加*/
    @Test
    public void insertPeople(){
        People people=new People();
        people.setPname("老张");people.setPphone("12312324321");people.setPemail("434354432@qq.com");
        people.setPpassword("123456");
        dao.insertPeople(people);
    }
    /*删除*/
    @Test
    public void deletePeople(){
        int pid=1;
        dao.deletePeople(pid);
    }
    /*修改*/
    @Test
    public void updatePeople(){
        People people=new People();
        people.setPname("老张");people.setPphone("12312324321");people.setPemail("434354432@qq.com");
        people.setPpassword("123456");people.setPid(1);
        dao.updatePeople(people);
    }
    /*查询数量*/
    @Test
    public void selectPeopleCount(){
        Map<String,Object> map=new HashMap<String, Object>();
        map.put("pname","");map.put("pphone","13465767654");map.put("pemail","");
        int count=dao.selectPeopleCount(map);
    }
    /*查询与分页的组合*/
    @Test
    public void selectPeoplePageByIf(){
        Map<String,Object> map=new HashMap<String, Object>();
        map.put("pname","");map.put("pphone","13465767654");map.put("pemail","");
        map.put("page",0);map.put("limit",5);
        System.out.println(dao.selectPeoplePageByIf(map));
    }
    /*重置密码*/
    @Test
    public void updatePassword(){
        People people=new People();
        people.setPid(1);
        people.setPpassword(MD5Utils.getMD5String("000000"));
        int row=dao.updatePassword(people);
        if(row>0){
            System.out.println("重置密码成功！");
        }
    }
}
