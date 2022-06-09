package com.wanjuanshu.web.shopadmin;

import com.wanjuanshu.enums.ProductCategoryStateEnum;
import com.wanjuanshu.dto.ProductCategoryExecution;
import com.wanjuanshu.dto.Result;
import com.wanjuanshu.entity.ProductCategory;
import com.wanjuanshu.entity.Shop;
import com.wanjuanshu.exceptions.ProductCategoryOperationException;
import com.wanjuanshu.service.ProductCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/shopadmin")
public class ProductCategoryManagementController {
	@Autowired
	private ProductCategoryService productCategoryService;

	@RequestMapping(value = "/getproductcategorylist", method = RequestMethod.GET)
	@ResponseBody
	private Result<List<ProductCategory>> getProductCategoryList(HttpServletRequest request) {
		Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
		List<ProductCategory> list = null;
		if (currentShop != null && currentShop.getShopId() > 0) {
			list = productCategoryService.getProductCategoryList(currentShop.getShopId());
			return new Result<List<ProductCategory>>(true, list);
		} else {
			ProductCategoryStateEnum ps = ProductCategoryStateEnum.INNER_ERROR;
			return new Result<List<ProductCategory>>(false, ps.getState(), ps.getStateInfo());
		}
	}

	@RequestMapping(value = "/addproductcategorys", method = RequestMethod.POST)
	@ResponseBody
	private Map<String, Object> addProductCategorys(@RequestBody List<ProductCategory> productCategoryList, HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<>();
		Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
		for (ProductCategory pc : productCategoryList) {
			pc.setShopId(currentShop.getShopId());
		}
		if (productCategoryList != null && productCategoryList.size() > 0) {
			try {
				ProductCategoryExecution pe = productCategoryService.batchInsertProductCategory(productCategoryList);
				if (pe.getState() == ProductCategoryStateEnum.SUCCESS.getState()) {
					modelMap.put("success", true);
				} else {
					modelMap.put("success", false);
					modelMap.put("errMsg", pe.getStateInfo());
				}
			} catch (ProductCategoryOperationException e) {
				modelMap.put("success", false);
				modelMap.put("errMsg", e.toString());
				return modelMap;
			}
		}
		return modelMap;
	}

	@RequestMapping(value = "/removeproductcategory",method = RequestMethod.POST)
	@ResponseBody
	private Map<String, Object> removeProductCategory(Long productCategoryId,HttpServletRequest request){
		Map<String, Object> modelMap = new HashMap<>();
		if (productCategoryId != null && productCategoryId > 0 ){
			try{
				Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
				ProductCategoryExecution pe =productCategoryService.deleteProductCategory(productCategoryId,currentShop.getShopId());
				if(pe.getState() == ProductCategoryStateEnum.SUCCESS.getState()){
					modelMap.put("success",true);
				}else {
					modelMap.put("success",false);
					modelMap.put("errMsg",pe.getStateInfo());
				}
			}catch (ProductCategoryOperationException e){
				modelMap.put("success",false);
				modelMap.put("errMsg",e.toString());
			}
		}else {
			modelMap.put("success",false);
			modelMap.put("errMsg","请至少选择一个商品类别");
		}
		return modelMap;
	}
}
