package com.wanjuanshu.service;

import com.wanjuanshu.entity.Area;

import java.util.List;

/**
 * @author yangshucheng
 * @create 2021-04-05 18:27
 */
public interface AreaService {
    public static final String AREALISTKEY = "arealist";
    List<Area> getAreaList();
}
