package com.wanjuanshu.o2o.service;

import com.wanjuanshu.o2o.dto.UserProductMapExecution;
import com.wanjuanshu.o2o.entity.UserProductMap;

/**
 * @author yangshucheng
 * @create 2021-06-02 10:59
 */
public interface UserProductMapService {

    /**
     * 通过传入的查询条件分页列出用户消费信息列表
     * @param userProductCondition
     * @param pageIndex
     * @param pageSize
     * @return
     */
    UserProductMapExecution listUserProductMap(UserProductMap userProductCondition,Integer pageIndex,Integer pageSize);

    UserProductMapExecution addUserProductMap(UserProductMap userProductMap);
}
