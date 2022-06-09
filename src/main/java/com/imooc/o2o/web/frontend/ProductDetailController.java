package com.wanjuanshu.o2o.web.frontend;

import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.wanjuanshu.o2o.entity.PersonInfo;
import com.wanjuanshu.o2o.entity.Product;
import com.wanjuanshu.o2o.service.ProductService;
import com.wanjuanshu.o2o.util.CodeUtil;
import com.wanjuanshu.o2o.util.HttpServletRequestUtil;
import com.wanjuanshu.o2o.util.ShortNetAddressUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * @author yangshucheng
 * @create 2021-05-16 15:46
 */
@Controller
@RequestMapping(value = "/frontend")
public class ProductDetailController {
    @Autowired
    private ProductService productService;


    /**
     * 根据商品Id获取商品详情
     * @param request
     * @return
     */
    @RequestMapping(value = "/listproductdetailpageinfo",method = RequestMethod.GET)
    @ResponseBody
    private Map<String, Object> listProductDetailPageInfo(HttpServletRequest request){
        Map<String, Object> modelMap = new HashMap<>();
        // 获取前台传递进来的productId
        long productId = HttpServletRequestUtil.getLong(request,"productId");
        Product product = null;
        // 空值判断
        if(productId != -1){
            // 根据productId获取商品信息，包含商品详情图列表
            // 二维码新增
            PersonInfo user = (PersonInfo) request.getSession().getAttribute("user");
            if (user == null){
                modelMap.put("needQRCode",false);
            }else{
                modelMap.put("needQRCode",true);
            }
            product = productService.getProductById(productId);
            modelMap.put("product", product);
            modelMap.put("success", true);
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "empty productId");
        }
        return modelMap;
    }
    
    // 微信获取用户信息的api前缀
    private static String urlPrefix;
    // 微信获取用户信息的api中间部分
    private static String urlMiddle;
    // 微信获取用户信息的api后缀
    private static String urlSuffix;
    // 微信回传给的响应添加顾客商品映射信息的url
    private static String productmapUrl;
    
    @Value("${wechat.prefix}")
    public void setUrlPrefix(String urlPrefix){
        ProductDetailController.urlPrefix = urlPrefix;
    }

    @Value("${wechat.middle}")
    public void setUrlMiddle(String urlMiddle) {
        ProductDetailController.urlMiddle = urlMiddle;
    }

    @Value("${wechat.suffix}")
    public void setUrlSuffix(String urlSuffix) {
        ProductDetailController.urlSuffix = urlSuffix;
    }

    @Value("${wechat.productmap.url}")
    public void setProductmapUrl(String productmapUrl) {
        ProductDetailController.productmapUrl = productmapUrl;
    }


    @RequestMapping(value = "/generateqrcode4product",method = RequestMethod.GET)
    @ResponseBody
    private void generateQRCode4Product(HttpServletRequest request, HttpServletResponse response){
        //获取前端传递过来的商品Id
        long productId = HttpServletRequestUtil.getLong(request,"productId");
        //从session里获取当前顾客的信息
        PersonInfo user = (PersonInfo) request.getSession().getAttribute("user");
        //空值判断
        if (productId != -1 && user != null && user.getUserId() != null){
            //获取当前时间戳，以保证二维码的时间有效性，精确到毫秒
            long timeStamp = System.currentTimeMillis();
            //将商品id，顾客Id和timestamp传入content，赋值到state中，
            // 这样微信获取到这些信息后会回传到用户商品映射信息的添加方法里
            //加上aaa是为了一会的在添加信息的方法里替换这些信息使用
            String content = "?productId=" + productId + "&&customerId=" + user.getUserId()
                    + "&&createTime=" + timeStamp;
            try{
                //将content的信息先进行base64编码以避免特殊字符造成的干扰，之后拼接URL
                String longUrl = productmapUrl
                        + content;
                //将目标URL转换成短的URL
                String shortUrl = longUrl;
                // 调用二维码生成的工具类方法，传入短的URL，生成二维码
                BitMatrix qRcodeImg = CodeUtil.generateQRCodeStream(shortUrl,response);
                // 将二维码以图片流的形式输出到前端
                MatrixToImageWriter.writeToStream(qRcodeImg,"png",response.getOutputStream());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}























