package com.wanjuanshu.o2o.service;

import com.wanjuanshu.o2o.dto.ImageHolder;
import com.wanjuanshu.o2o.dto.ShopExecution;
import com.wanjuanshu.o2o.entity.Shop;
import com.wanjuanshu.o2o.exceptions.ShopOperationException;

/**
 * @author yangshucheng
 * @create 2021-04-06 15:39
 */
public interface ShopService {

    /**
     * 根据shopCondition分页返回相应店铺列表数据
     * @param shopCondition
     * @param pageIndex
     * @param pageSize
     * @return
     */
    ShopExecution getShopList(Shop shopCondition,int pageIndex,int pageSize);
    /**
     * 通过店铺Id获取店铺信息
     * @param shopId
     * @return
     */
    Shop getByShopId(long shopId);

    /**
     * 修改商品信息
     * @param shop
     * @param thumbnail
     * @return
     * @throws ShopOperationException
     */
    ShopExecution modifyShop(Shop shop, ImageHolder thumbnail) throws ShopOperationException;
    /**
     * 添加商品信息
     * @param shop
     * @param thumbnail
     * @return
     */
    ShopExecution addShop(Shop shop,ImageHolder thumbnail);
}
