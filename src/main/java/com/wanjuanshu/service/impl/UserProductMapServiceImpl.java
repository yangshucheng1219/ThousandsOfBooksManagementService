package com.wanjuanshu.service.impl;

import com.wanjuanshu.dao.UserProductMapDao;
import com.wanjuanshu.dao.UserShopMapDao;
import com.wanjuanshu.dto.UserProductMapExecution;
import com.wanjuanshu.entity.UserShopMap;
import com.wanjuanshu.enums.UserProductMapStateEnum;
import com.wanjuanshu.entity.PersonInfo;
import com.wanjuanshu.entity.Shop;
import com.wanjuanshu.entity.UserProductMap;
import com.wanjuanshu.exceptions.UserProductMapOperationException;
import com.wanjuanshu.service.UserProductMapService;
import com.wanjuanshu.util.PageCalculator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.Date;
import java.util.List;

/**
 * @author yangshucheng
 * @create 2021-06-02 10:59
 */
@Service
public class UserProductMapServiceImpl implements UserProductMapService {

    @Autowired
    private UserProductMapDao userProductMapDao;

    @Autowired
    private UserShopMapDao userShopMapDao;

    @Override
    public UserProductMapExecution listUserProductMap(UserProductMap userProductCondition, Integer pageIndex, Integer pageSize) {
        //空值判断
        if (userProductCondition != null && pageIndex != null && pageSize != null) {
            //页转行
            int beginIndex = PageCalculator.calculateRowIndex(pageIndex, pageSize);
            //依据查询条件分页取出列表
            List<UserProductMap> userProductMapList = userProductMapDao.queryUserProductMapList(userProductCondition, pageIndex, pageSize);
            //按照同等的查询条件获取总数
            int count = userProductMapDao.queryUserProductMapCount(userProductCondition);
            UserProductMapExecution se = new UserProductMapExecution();
            se.setUserProductMapList(userProductMapList);
            se.setCount(count);
            return se;
        } else {
            return null;
        }
    }

    public UserProductMapExecution addUserProductMap(UserProductMap userProductMap) throws UserProductMapOperationException {
        //空值判断，主要确保顾客Id，店铺Id以及操作员Id非空
        if (userProductMap != null && userProductMap.getUser().getUserId() != null
                && userProductMap.getShop().getShopId() != null && userProductMap.getOperator().getUserId() != null) {
            //设定默认值
            userProductMap.setCreateTime(new Date());
            try {
                //添加消费记录
                int effectNum = userProductMapDao.insertUserProductMap(userProductMap);
                if (effectNum <= 0) {
                    throw new UserProductMapOperationException("添加消费记录失败");
                }
                //若本次消费能够积分
                if (userProductMap.getPoint() != null && userProductMap.getPoint() > 0) {
                    //查询该顾客是否在店铺消费过
                    UserShopMap userShopMap = userShopMapDao.queryUserShopMap(
                            userProductMap.getUser().getUserId(),
                            userProductMap.getShop().getShopId());
                    if (userShopMap != null && userShopMap.getUserShopId() != null) {
                        //若之前有消费过，既有过积分记录，则进行总积分的更新操作
                        userShopMap.setPoint(userShopMap.getPoint() + userProductMap.getPoint());
                        effectNum = userShopMapDao.updateUserShopMapPoint(userShopMap);
                        if (effectNum <= 0) {
                            throw new UserProductMapOperationException("更新积分信息失败");
                        }
                    } else {
                        //在店铺没有过消费记录，添加一条店铺积分信息（与初始化会员一样）
                        userShopMap = compactUserShopMap4Add(userProductMap.getUser().getUserId(),
                                userProductMap.getShop().getShopId(), userProductMap.getPoint());
                        effectNum = userShopMapDao.insertUserShopMap(userShopMap);
                        if (effectNum <= 0 ){
                            throw new UserProductMapOperationException("积分信息创建失败");
                        }
                    }
                }
                return new UserProductMapExecution(UserProductMapStateEnum.SUCCESS,userProductMap);
            }catch (Exception e){
                throw new UserProductMapOperationException("添加授权信息失败" + e.toString());
            }
        }else{
            return new UserProductMapExecution(UserProductMapStateEnum.NULL_USERPRODUCT_INFO);
        }
    }

    /**
     * 封装顾客积分信息
     * @param userId
     * @param shopId
     * @param point
     * @return
     */
    private UserShopMap compactUserShopMap4Add(Long userId,Long shopId,Integer point){
        UserShopMap userShopMap = null;
        //空值判断
        if (userId != null && shopId != null){
            userShopMap = new UserShopMap();
            PersonInfo customer = new PersonInfo();
            customer.setUserId(userId);
            Shop shop = new Shop();
            shop.setShopId(shopId);
            userShopMap.setUser(customer);
            userShopMap.setShop(shop);
            userShopMap.setCreateTime(new Date());
            userShopMap.setPoint(point);
        }
        return userShopMap;
    }
}
