package com.wanjuanshu.service.impl;

import com.wanjuanshu.dao.ProductDao;
import com.wanjuanshu.dao.ProductImgDao;
import com.wanjuanshu.dto.ImageHolder;
import com.wanjuanshu.dto.ProductExecution;
import com.wanjuanshu.entity.ProductImg;
import com.wanjuanshu.enums.ProductStateEnum;
import com.wanjuanshu.entity.Product;
import com.wanjuanshu.exceptions.ProductOperationException;
import com.wanjuanshu.service.ProductService;
import com.wanjuanshu.util.ImageUtil;
import com.wanjuanshu.util.PageCalculator;
import com.wanjuanshu.util.PathUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author yangshucheng
 * @create 2021-05-09 11:29
 */
@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductDao productDao;
    @Autowired
    protected ProductImgDao productImgDao;

    @Override
    public ProductExecution getProductList(Product productCondition, int pageIndex, int pageSize) {
        // 页码转换成数据库的行码，并调用dao层取回指定页码的商品列表
        int rowIndex = PageCalculator.calculateRowIndex(pageIndex,pageSize);
        List<Product> productList = productDao.queryProductList(productCondition,rowIndex,pageSize);
        //基于同样的查询条件返回该查询条件下的商品总数
        int count = productDao.queryProductCount(productCondition);
        ProductExecution pe = new ProductExecution();
        pe.setProductList(productList);
        pe.setCount(count);
        return pe;
    }

    @Override
    public Product getProductById(long productId) {
        return productDao.queryProductById(productId);
    }

    @Override
    @Transactional
    // 1.处理缩略图，获取缩略图相对路径并赋值给product
    // 2.往tb_product写入商品信息，获取productId
    // 3.结合productId批量处理商品详情图
    // 4.将商品详情图列表批量插入tb_product_img中
    public ProductExecution addProduct(Product product, ImageHolder thumbnail, List<ImageHolder> productImgHolderList) throws ProductOperationException {
        //空值判断
        if (product != null && product.getShop() != null && product.getShop().getShopId() != null) {
            //给商品设置上默认属性
            product.setCreateTime(new Date());
            product.setLastEditTime(new Date());
            //默认为上架的状态
            product.setEnableStatus(1);
            //若商品缩略图不为空则添加
            if (thumbnail != null) {
                addThumbnail(product, thumbnail);
            }
            try {
                int effectNum = productDao.insertProduct(product);
                if (effectNum <= 0){
                    throw new ProductOperationException("创建商品失败");
                }
            } catch (ProductOperationException e) {
                throw new ProductOperationException("创建商品失败" + e.toString());
            }
            //若商品详情图不为空则添加
            if (productImgHolderList != null && productImgHolderList.size() > 0) {
                addProductImgList(product,productImgHolderList);
            }
            return new ProductExecution(ProductStateEnum.SUCCESS,product);
        }else{
            //传参为空则返回空值错误信息
            return new ProductExecution(ProductStateEnum.EMPTY);
        }
    }

    private void addThumbnail(Product product,ImageHolder imageHolder){
        String dest = PathUtil.getShopImagePath(product.getShop().getShopId());
        String thumbnailAddr = ImageUtil.generateThumbnail(imageHolder,dest);
        product.setImgAddr(thumbnailAddr);
    }

    private void addProductImgList(Product product,List<ImageHolder> productImgHolderList){
        //获取图片存储路径，这里直接存放到相应店铺的文件夹底下
        String dest = PathUtil.getShopImagePath(product.getShop().getShopId());
        List<ProductImg> productImgList = new ArrayList<>();
        //遍历图片一次去处理，并添加进productImg实体类里
        for (ImageHolder productImgHolder: productImgHolderList) {
            String imgAddr = ImageUtil.generateNormalImg(productImgHolder,dest);
            ProductImg productImg = new ProductImg();
            productImg.setImgAddr(imgAddr);
            productImg.setProductId(product.getProductId());
            productImg.setCreateTime(new Date());
            productImgList.add(productImg);
        }
        //如果确实是有图片需要添加的，就执行批量添加操作
        if (productImgList.size() > 0){
            try {
                int effectNum = productImgDao.batchInsertProductImg(productImgList);
                if (effectNum <= 0){
                    throw new ProductOperationException("创建商品详情图片失败");
                }
            }catch (Exception e){
                throw new ProductOperationException("创建商品详情图片失败" + e.toString());
            }
        }
    }

    @Override
    @Transactional
    // 1.若缩略图参数有值，则处理缩略图，
    // 若原先存在缩略图则先删除再添加新图，之后获取缩略图相对路径并赋值给product
    // 2.若商品详情图列表参数有值，对商品详情图片列表进行同样的操作
    // 3.将tb_product_img下面的该商品原先的商品详情图记录全部清除
    // 4.更新tb_product_img以及tb_product的信息
    public ProductExecution modifyProduct(Product product, ImageHolder thumbnail, List<ImageHolder> productImgHolderList) throws ProductOperationException {
        //空值判断
        if (product != null && product.getShop() != null && product.getShop().getShopId() != null) {
            //给商品设置上默认属性
            product.setLastEditTime(new Date());
            //若商品缩略图不为空则添加
            if (thumbnail != null) {
                //先获取一遍原有信息，因为原来的信息里有原图片的地址
                Product tempProduct = productDao.queryProductById(product.getProductId());
                if (tempProduct.getImgAddr() != null){
                    ImageUtil.deleteFileOrPath(tempProduct.getImgAddr());
                }
                addThumbnail(product, thumbnail);
            }

            //若有新存入的商品详情图，则将原先的删除，并添加新的图片
            if (productImgHolderList != null && productImgHolderList.size() > 0) {
                deleteProductImgList(product.getProductId());
                addProductImgList(product,productImgHolderList);
            }
            try {
                //更新商品信息
                int effectNum = productDao.updateProduct(product);
                if (effectNum <= 0){
                    throw new ProductOperationException("更新商品失败");
                }
                return new ProductExecution(ProductStateEnum.SUCCESS,product);
            } catch (ProductOperationException e) {
                throw new ProductOperationException("更新商品失败" + e.toString());
            }
        }else{
            //传参为空则返回空值错误信息
            return new ProductExecution(ProductStateEnum.EMPTY);
        }
    }
    private void deleteProductImgList(long productId){
        //根据productId获取原来的图片
        List<ProductImg> productImgList = productImgDao.queryProductImgList(productId);
        //删除原来的图片
        for (ProductImg productImg : productImgList) {
            ImageUtil.deleteFileOrPath(productImg.getImgAddr());
        }
        //删除数据库里原来有图片的信息
        productImgDao.deleteProductImgByProductId(productId);
    }
}
