package com.wanjuanshu.service.impl;

import com.wanjuanshu.dao.ProductCategoryDao;
import com.wanjuanshu.dto.ProductCategoryExecution;
import com.wanjuanshu.enums.ProductCategoryStateEnum;
import com.wanjuanshu.entity.ProductCategory;
import com.wanjuanshu.exceptions.ProductCategoryOperationException;
import com.wanjuanshu.service.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductCategoryServiceImpl implements ProductCategoryService {
	@Autowired
	private ProductCategoryDao productCategoryDao;
	@Override
	public List<ProductCategory> getProductCategoryList(long shopId) {
		return productCategoryDao.queryProductCategoryList(shopId);
	}
	@Override
	public ProductCategoryExecution batchInsertProductCategory(List<ProductCategory> productCategoryList) throws ProductCategoryOperationException {
		if (productCategoryList != null && productCategoryList.size() > 0) {
			try {
				int effectNum = productCategoryDao.batchInsertProductCategory(productCategoryList);
				if (effectNum <= 0) {
					throw new ProductCategoryOperationException("店铺创建失败");
				} else {
					return new ProductCategoryExecution(ProductCategoryStateEnum.SUCCESS);
				}
			} catch (Exception e) {
				throw new ProductCategoryOperationException("batchInsertProductCategory error : " + e.getMessage());
			}
		} else {
			return new ProductCategoryExecution(ProductCategoryStateEnum.EMPTY_LIST);
		}
	}

	@Override
	@Transactional
	public ProductCategoryExecution deleteProductCategory(long productCategoryId, long shopId) throws ProductCategoryOperationException {
		//TODO 将此类别下的商品里的类别id置为空，再删除该商品类别
		try {
			int effectNum = productCategoryDao.deleteProductCategory(productCategoryId,shopId);
			if (effectNum <= 0){
				throw new ProductCategoryOperationException("商品类别删除失败");
			}else {
				return new ProductCategoryExecution(ProductCategoryStateEnum.SUCCESS);
			}
		}catch (Exception e){
			throw new ProductCategoryOperationException("deleteProductCategory error: " + e.getMessage());
		}
	}
}
