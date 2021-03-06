package com.wanjuanshu.service;

import com.wanjuanshu.dto.ProductCategoryExecution;
import com.wanjuanshu.entity.ProductCategory;
import com.wanjuanshu.exceptions.ProductCategoryOperationException;

import java.util.List;

public interface ProductCategoryService {
	/**
	 * 查询指定某个店铺下的所有商品类别信息
	 * @return List<ProductCategory>
	 */
	List<ProductCategory> getProductCategoryList(long shopId);

	ProductCategoryExecution batchInsertProductCategory(List<ProductCategory> productCategoryList)
			throws ProductCategoryOperationException;

	/**
	 * 将此类别下的商品里的类别id置为空，再删除该商品类别
	 * @param productCategoryId
	 * @param shopId
	 * @return
	 * @throws ProductCategoryOperationException
	 */
	ProductCategoryExecution deleteProductCategory(long productCategoryId,long shopId)
			throws ProductCategoryOperationException;

}
