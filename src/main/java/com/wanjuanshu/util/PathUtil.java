package com.wanjuanshu.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @author yangshucheng
 * @create 2021-04-06 17:02
 */
@Configuration
public class PathUtil {
    private static final String separator = "/";

    private static  String winPath;

    private static String linuxPath;

    private static  String shopPath;

    private static String headLinePath;

    private static  String shopCategoryPath;

    @Value("${win.base.path}")
    public void setWinPath(String winPath) {
        PathUtil.winPath = winPath;
    }

    @Value("${linux.base.path}")
    public void setLinuxPath(String linuxPath) {
        PathUtil.linuxPath = linuxPath;
    }

    @Value("${shop.relevant.path}")
    public void setShopPath(String shopPath) {
        PathUtil.shopPath = shopPath;
    }

    @Value("${headline.relevant.path}")
    public void setHeadLinePath(String headLinePath) {
        PathUtil.headLinePath = headLinePath;
    }

    @Value("${shopcategory.relevant.path}")
    public void setShopCategoryPath(String shopCategoryPath) {
        PathUtil.shopCategoryPath = shopCategoryPath;
    }

    public static String getImgBasePath(){
        String os = System.getProperty("os.name");
        String basePath = "";
        if (os.toLowerCase().startsWith("win")) {
            basePath = winPath;
        }else {
            basePath = linuxPath;
        }
        basePath = basePath.replace("/",separator);
        return basePath;
    }

    public static String getShopImagePath(long shopId){
        String imagePath = shopPath + shopId + separator;
        return imagePath.replace("/",separator);
    }
    public static String getHeadLineImagePath() {
        return headLinePath.replace("/", separator);
    }
    public static String getShopCategoryPath() {
        return shopCategoryPath.replace("/", separator);
    }
}
