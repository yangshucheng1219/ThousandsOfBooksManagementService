package com.wanjuanshu.util;

import javax.servlet.http.HttpServletRequest;

/**
 * @author yangshucheng
 * @create 2021-04-10 13:51
 */
public class HttpServletRequestUtil {
    public static int getInt(HttpServletRequest request,String key){
        try {
            return Integer.decode(request.getParameter(key));
        } catch (Exception e) {
            return -1;
        }
    }

    public static long getLong(HttpServletRequest request, String key){
        try {
            return Long.valueOf(request.getParameter(key));
        } catch (Exception e) {
            return -1;
        }
    }

    public static double getDouble(HttpServletRequest request, String key){
        try {
            return Double.valueOf(request.getParameter(key));
        } catch (Exception e) {
            return -1d;
        }
    }

    public static boolean getBoolean(HttpServletRequest request, String key){
        try {
            return Boolean.valueOf(request.getParameter(key));
        } catch (Exception e) {
            return false;
        }
    }

    public static String getString(HttpServletRequest request,String key){
        try {
            String result = request.getParameter(key);
            if (result != null) {
                return result;
            }
            if ("".equals(result)){
                result = null;
            }
            return result;
        } catch (Exception e) {
            return null;
        }
    }


}
