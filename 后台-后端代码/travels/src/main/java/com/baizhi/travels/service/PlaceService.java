package com.baizhi.travels.service;

import com.baizhi.travels.entity.Place;

import java.util.List;

public interface PlaceService {


    List<Place> findByProvinceIdPage(Integer page,Integer rows,String provinceId);


    Integer findByProvinceIdCounts(String provinceId);


    void save(Place place);

    void delete(String id);

    Place findOne(String id);

    void update(Place place);
}
