package com.wanjuanshu.o2o.service;


import com.wanjuanshu.o2o.entity.Area;
import com.wanjuanshu.o2o.entity.ShopCategory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ShopCategoryServiceTest {
    @Autowired
    private ShopCategoryService shopCategoryService;

    @Test
    public void testGetAreaList() {
        List<ShopCategory> shopCategoryList = shopCategoryService.getShopCategoryList(null);

        for (ShopCategory shopCategory : shopCategoryList) {
            System.out.println(shopCategory);
        }
    }
}
