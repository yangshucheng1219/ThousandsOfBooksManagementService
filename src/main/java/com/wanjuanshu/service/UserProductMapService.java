package com.wanjuanshu.service;

import com.wanjuanshu.dto.UserProductMapExecution;
import com.wanjuanshu.entity.UserProductMap;

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
    UserProductMapExecution listUserProductMap(UserProductMap userProductCondition, Integer pageIndex, Integer pageSize);

    UserProductMapExecution addUserProductMap(UserProductMap userProductMap);
}
