package com.wanjuanshu.service.impl;

import java.util.List;

import com.wanjuanshu.dao.UserShopMapDao;
import com.wanjuanshu.dto.UserShopMapExecution;
import com.wanjuanshu.entity.UserShopMap;
import com.wanjuanshu.util.PageCalculator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.wanjuanshu.service.UserShopMapService;

@Service
public class UserShopMapServiceImpl implements UserShopMapService {
    @Autowired
    private UserShopMapDao userShopMapDao;

    @Override
    public UserShopMapExecution listUserShopMap(UserShopMap userShopMapCondition, int pageIndex, int pageSize) {
        // 空值判断
        if (userShopMapCondition != null && pageIndex != -1 && pageSize != -1) {
            // 页转行
            int beginIndex = PageCalculator.calculateRowIndex(pageIndex, pageSize);
            // 根据传入的查询条件分页返回用户积分列表信息
            List<UserShopMap> userShopMapList = userShopMapDao.queryUserShopMapList(userShopMapCondition, beginIndex,
                    pageSize);
            // 返回总数
            int count = userShopMapDao.queryUserShopMapCount(userShopMapCondition);
            UserShopMapExecution ue = new UserShopMapExecution();
            ue.setUserShopMapList(userShopMapList);
            ue.setCount(count);
            return ue;
        } else {
            return null;
        }

    }

    @Override
    public UserShopMap getUserShopMap(long userId, long shopId) {
        return userShopMapDao.queryUserShopMap(userId, shopId);
    }
}
