package com.zhangyuwei.people.dao;

import com.zhangyuwei.people.entities.People;

import java.util.List;
import java.util.Map;

public interface IpeopleDao {
    /*查询所有的用户*/
    List<People> selectAllPeople();
    /*登录*/
    List<People> selectByNameandPassword(People people);
    /*添加用户*/
    int insertPeople(People people);
    /*删除用户*/
    int deletePeople(int pid);
    /*修改用户*/
    int updatePeople(People people);
    /*查询与分页的组合*/
    List<People> selectPeoplePageByIf(Map<String,Object> map);
    /*查询数量，并有查询*/
    int selectPeopleCount(Map<String,Object> map);
    /*重置密码*/
    int updatePassword(People people);
}
