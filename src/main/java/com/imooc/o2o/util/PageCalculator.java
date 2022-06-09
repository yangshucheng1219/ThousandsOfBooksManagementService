package com.wanjuanshu.o2o.util;

/**
 * @author yangshucheng
 * @create 2021-04-16 17:29
 */
public class PageCalculator {
    public static int calculateRowIndex(int pageIndex,int pageSize){
        return (pageIndex > 0) ? (pageIndex - 1) * pageSize : 0;
    }
}
