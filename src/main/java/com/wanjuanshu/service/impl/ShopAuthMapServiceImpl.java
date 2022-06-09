package com.wanjuanshu.service.impl;

import com.wanjuanshu.dao.ShopAuthMapDao;
import com.wanjuanshu.dto.ShopAuthMapExecution;
import com.wanjuanshu.enums.ShopAuthMapStateEnum;
import com.wanjuanshu.entity.ShopAuthMap;
import com.wanjuanshu.exceptions.ShopAuthMapOperationException;
import com.wanjuanshu.service.ShopAuthMapService;
import com.wanjuanshu.util.PageCalculator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @author yangshucheng
 * @create 2021-05-31 19:29
 */
@Service
public class ShopAuthMapServiceImpl implements ShopAuthMapService {
    @Autowired
    private ShopAuthMapDao shopAuthMapDao;

    @Override
    public ShopAuthMapExecution listShopAuthMapByShopId(Long shopId, Integer pageIndex, Integer pageSize) {
        //空值判断
        if (shopId != null && pageIndex != null && pageSize != null){
            //页转行
            int beginIndex = PageCalculator.calculateRowIndex(pageIndex,pageSize);
            //查询返回该店铺的授权信息列表
            List<ShopAuthMap> shopAuthMapList = shopAuthMapDao.queryShopAuthMapListByShopId(shopId,beginIndex,pageSize);
            //返回总数
            int count = shopAuthMapDao.queryShopAuthCountByShopId(shopId);
            ShopAuthMapExecution se = new ShopAuthMapExecution();
            se.setShopAuthMapList(shopAuthMapList);
            se.setCount(count);
            return se;
        }else{
            return null;
        }
    }

    @Override
    public ShopAuthMap getShopAuthMapById(Long shopAuthId) {
        return shopAuthMapDao.queryShopAuthMapById(shopAuthId);
    }

    @Override
    @Transactional
    public ShopAuthMapExecution addShopAuthMap(ShopAuthMap shopAuthMap) throws ShopAuthMapOperationException {
        //空值判断，主要是对店铺Id和员工Id做校验
        if(shopAuthMap != null && shopAuthMap.getShop() != null && shopAuthMap.getShop().getShopId() != null
                && shopAuthMap.getEmployee() != null && shopAuthMap.getEmployee().getUserId() != null){
            shopAuthMap.setCreateTime(new Date());
            shopAuthMap.setLastEditTime(new Date());
            shopAuthMap.setEnableStatus(1);
            try{
                //添加授权信息
                int effectedNum = shopAuthMapDao.insertShopAuthMap(shopAuthMap);
                if(effectedNum <= 0){
                    throw new ShopAuthMapOperationException("添加授权失败");
                }
                return new ShopAuthMapExecution(ShopAuthMapStateEnum.SUCCESS,shopAuthMap);
            }catch (Exception e){
                throw new ShopAuthMapOperationException("添加授权失败：" + e.toString());
            }
        }else{
            return new ShopAuthMapExecution(ShopAuthMapStateEnum.NULL_SHOPAUTH_INFO);
        }
    }

    @Override
    public ShopAuthMapExecution modifyShopAuthMap(ShopAuthMap shopAuthMap) throws ShopAuthMapOperationException {
        //空值判断
        if (shopAuthMap == null || shopAuthMap.getShopAuthId() == null){
            return new ShopAuthMapExecution(ShopAuthMapStateEnum.NULL_SHOPAUTH_ID);
        }else {
            try{
                shopAuthMap.setLastEditTime(new Date());
                int effectedNum = shopAuthMapDao.updateShopAuthMap(shopAuthMap);
                if (effectedNum <= 0){
                    return new ShopAuthMapExecution(ShopAuthMapStateEnum.INNER_ERROR);
                }else {
                    //创建成功
                    return new ShopAuthMapExecution(ShopAuthMapStateEnum.SUCCESS,shopAuthMap);
                }
            }catch (Exception e){
                throw new ShopAuthMapOperationException("modifyShopAuthMap error: " + e.getMessage());
            }
        }
    }
}
