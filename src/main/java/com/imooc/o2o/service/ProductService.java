package com.wanjuanshu.o2o.service;

import com.wanjuanshu.o2o.dto.ImageHolder;
import com.wanjuanshu.o2o.dto.ProductExecution;
import com.wanjuanshu.o2o.entity.Product;
import com.wanjuanshu.o2o.exceptions.ProductOperationException;

import java.util.List;

/**
 * @author yangshucheng
 * @create 2021-05-09 11:28
 */
public interface ProductService {

    /**
     * 查询商品列表并分页，可输入的条件有：商品名（模糊），商品状态，店铺Id，商品类别
     * @param productCondition
     * @param pageIndex
     * @return
     */
    ProductExecution getProductList(Product productCondition,int pageIndex,int pageSize);

    /**
     * 通过Id查询唯一的商品信息
     * @param productId
     * @return
     */
    Product getProductById(long productId);

    /**
     * 添加商品信息和图片处理
     * @param product
     * @param thumbnail
     * @param productImgHolderList
     * @return
     * @throws ProductOperationException
     */
    ProductExecution addProduct(Product product, ImageHolder thumbnail, List<ImageHolder> productImgHolderList)
            throws ProductOperationException;

    /**
     * 修改商品信息以及图片处理
     * @param product
     * @param thumbnail
     * @param productImgHolderList
     * @return
     * @throws ProductOperationException
     */
    ProductExecution modifyProduct(Product product,ImageHolder thumbnail,List<ImageHolder> productImgHolderList)
            throws ProductOperationException;

}
