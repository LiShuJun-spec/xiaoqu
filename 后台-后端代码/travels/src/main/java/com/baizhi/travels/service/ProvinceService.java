package com.baizhi.travels.service;

import com.baizhi.travels.entity.Province;

import java.util.List;

public interface ProvinceService {


    //参数1:当前页 //参数2:每页显示记录数
    List<Province> findByPage(Integer page,Integer rows);


    //查询总跳数
    Integer findTotals();

    //保存省份方法
    void save(Province province);

    //删除省份的方法
    void delete(String id);

    //查询省份信息
    Province findOne(String id);

    //修改省份信息
    void update(Province province);

}
