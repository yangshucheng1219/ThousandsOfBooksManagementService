package com.wanjuanshu.o2o.service.impl;

import com.wanjuanshu.o2o.dao.ShopAuthMapDao;
import com.wanjuanshu.o2o.dao.ShopDao;
import com.wanjuanshu.o2o.dto.ImageHolder;
import com.wanjuanshu.o2o.dto.ShopExecution;
import com.wanjuanshu.o2o.entity.Shop;
import com.wanjuanshu.o2o.entity.ShopAuthMap;
import com.wanjuanshu.o2o.enums.ShopStateEnum;
import com.wanjuanshu.o2o.exceptions.ShopOperationException;
import com.wanjuanshu.o2o.service.ShopService;
import com.wanjuanshu.o2o.util.ImageUtil;
import com.wanjuanshu.o2o.util.PageCalculator;
import com.wanjuanshu.o2o.util.PathUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @author yangshucheng
 * @create 2021-04-06 15:40
 */
@Service
public class ShopServiceImpl implements ShopService {

    @Autowired
    private ShopDao shopDao;
    @Autowired
    private ShopAuthMapDao shopAuthMapDao;
    private final static Logger LOG = LoggerFactory.getLogger(ShopServiceImpl.class);

    @Override
    public ShopExecution getShopList(Shop shopCondition, int pageIndex, int pageSize) {
        int rowIndex = PageCalculator.calculateRowIndex(pageIndex,pageSize);
        List<Shop> shopList = shopDao.queryShopList(shopCondition,rowIndex,pageSize);
        int count = shopDao.queryShopCount(shopCondition);
        ShopExecution se = new ShopExecution();//初始化请new一个新对象
        if (shopList != null){
            se.setShopList(shopList);
            se.setCount(count);
        }else {
            se.setState(ShopStateEnum.INNER_ERROR.getState());
        }
        return se;
    }

    @Override
    public Shop getByShopId(long shopId) {
        return shopDao.queryByShopId(shopId);
    }

    @Override
    public ShopExecution modifyShop(Shop shop, ImageHolder thumbnail) throws ShopOperationException {
        if (shop == null || shop.getShopId() == null){
            return new ShopExecution(ShopStateEnum.NULL_SHOP);
        }else {
            try {
                //1.判断是否需要处理图片
                if (thumbnail.getImage() != null && thumbnail.getImageName() != null && !"".equals(thumbnail.getImageName())){
                    Shop tempShop = shopDao.queryByShopId(shop.getShopId());
                    if (tempShop.getShopImg() != null){
                        ImageUtil.deleteFileOrPath(tempShop.getShopImg());
                    }
                    addShopImg(shop,thumbnail);
                }
                //2.更新店铺信息
                shop.setLastEditTime(new Date());
                int effectedNum = shopDao.updateShop(shop);
                if (effectedNum <= 0){
                    return new ShopExecution(ShopStateEnum.INNER_ERROR);
                }else {
                    shop = shopDao.queryByShopId(shop.getShopId());
                    return new ShopExecution(ShopStateEnum.SUCCESS,shop);
                }
            } catch (Exception e) {
                throw new ShopOperationException("modifyShop error:" + e.getMessage());
            }
        }
    }

    @Override
    @Transactional
    public ShopExecution addShop(Shop shop, ImageHolder thumbnail) {
        //空值判断
        if (shop == null) {
            return new ShopExecution(ShopStateEnum.NULL_SHOP);
        }
//        if (shop.getShopId() == null) {
//            return new ShopExecution(ShopStateEnum.NULL_SHOPID);
//        }

        try {
            //给店铺信息赋初始值
            shop.setEnableStatus(0);
            shop.setCreateTime(new Date());
            shop.setLastEditTime(new Date());
            shop.setAdvice("审核中");
            //添加店铺信息
            int effectedNum = shopDao.insertShop(shop);
            if (effectedNum <= 0) {
                throw new ShopOperationException("店铺创建失败");
            }else {
                if (thumbnail.getImage() != null) {
                    //存储图片
                    try {
                        addShopImg(shop, thumbnail);
                    } catch (Exception e) {
                        throw new ShopOperationException("添加店铺照片失败" + e.getMessage());
                    }
                    //更新图片地址
                    effectedNum = shopDao.updateShop(shop);
                    if (effectedNum <= 0){
                        LOG.error("插入店铺信息的时候，返回了0条变更");
                        throw new ShopOperationException("店铺照片地址更新失败");
                    }
                    // 执行增加shopAuthMap操作
                    ShopAuthMap shopAuthMap = new ShopAuthMap();
                    shopAuthMap.setEmployee(shop.getOwner());
                    shopAuthMap.setShop(shop);
                    shopAuthMap.setTitle("店家");
                    shopAuthMap.setTitleFlag(0);
                    shopAuthMap.setCreateTime(new Date());
                    shopAuthMap.setLastEditTime(new Date());
                    shopAuthMap.setEnableStatus(1);
                    try {
                        effectedNum = shopAuthMapDao.insertShopAuthMap(shopAuthMap);
                        if (effectedNum <= 0) {
                            LOG.error("addShop:授权创建失败");
                            throw new ShopOperationException("授权创建失败");
                        }
                    } catch (Exception e) {
                        LOG.error("insertShopAuthMap error: " + e.getMessage());
                        throw new ShopOperationException("授权创建失败");
                    }
                }
            }
        } catch (Exception e) {
            LOG.error("addShop error: " + e.getMessage());
            throw new ShopOperationException("添加店铺失败，请联系管理员");
        }
        return new ShopExecution(ShopStateEnum.CHECK,shop);
    }

    private void addShopImg(Shop shop, ImageHolder thumbnail) {
        //获取shop的相对值路径
        String dest = PathUtil.getShopImagePath(shop.getShopId());
        String shopImgAddr = ImageUtil.generateThumbnail(thumbnail,dest);
        shop.setShopImg(shopImgAddr);
    }
}
