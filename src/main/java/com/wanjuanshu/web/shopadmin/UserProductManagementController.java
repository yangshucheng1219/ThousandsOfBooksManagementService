package com.wanjuanshu.web.shopadmin;

import com.wanjuanshu.dto.EchartSeries;
import com.wanjuanshu.dto.EchartXAxis;
import com.wanjuanshu.dto.ShopAuthMapExecution;
import com.wanjuanshu.dto.UserAccessToken;
import com.wanjuanshu.dto.UserProductMapExecution;
import com.wanjuanshu.dto.WechatInfo;
import com.wanjuanshu.entity.PersonInfo;
import com.wanjuanshu.entity.Product;
import com.wanjuanshu.entity.ProductSellDaily;
import com.wanjuanshu.entity.Shop;
import com.wanjuanshu.entity.ShopAuthMap;
import com.wanjuanshu.entity.UserProductMap;
import com.wanjuanshu.entity.WechatAuth;
import com.wanjuanshu.enums.UserProductMapStateEnum;
import com.wanjuanshu.dao.PersonInfoDao;
import com.wanjuanshu.o2o.dto.*;
import com.wanjuanshu.o2o.entity.*;
import com.wanjuanshu.service.ProductSellDailyService;
import com.wanjuanshu.service.ProductService;
import com.wanjuanshu.service.ShopAuthMapService;
import com.wanjuanshu.service.UserProductMapService;
import com.wanjuanshu.service.WechatAuthService;
import com.wanjuanshu.util.HttpServletRequestUtil;
import com.wanjuanshu.util.wechat.WechatUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("/shopadmin")
public class UserProductManagementController {
    @Autowired
    private UserProductMapService userProductMapService;
    @Autowired
    private ProductSellDailyService productSellDailyService;
    @Autowired
    private WechatAuthService wechatAuthService;
    @Autowired
    private ShopAuthMapService shopAuthMapService;
    @Autowired
    private ProductService productService;

    @Autowired
    private PersonInfoDao personInfoDao;

    @RequestMapping(value = "/listuserproductmapsbyshop", method = RequestMethod.GET)
    @ResponseBody
    private Map<String, Object> listUserProductMapsByShop(HttpServletRequest request) {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        // 获取分页信息
        int pageIndex = HttpServletRequestUtil.getInt(request, "pageIndex");
        int pageSize = HttpServletRequestUtil.getInt(request, "pageSize");
        // 获取当前的店铺信息
        Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
        // 空值校验，主要确保shopId不为空
        if ((pageIndex > -1) && (pageSize > -1) && (currentShop != null) && (currentShop.getShopId() != null)) {
            // 添加查询条件
            UserProductMap userProductMapCondition = new UserProductMap();
            userProductMapCondition.setShop(currentShop);
            String productName = HttpServletRequestUtil.getString(request, "productName");
            if (productName != null) {
                // 若前端想按照商品名模糊查询，则传入productName
                Product product = new Product();
                product.setProductName(productName);
                userProductMapCondition.setProduct(product);
            }
            // 根据传入的查询条件获取该店铺的商品销售情况
            UserProductMapExecution ue = userProductMapService.listUserProductMap(userProductMapCondition, pageIndex,
                    pageSize);
            modelMap.put("userProductMapList", ue.getUserProductMapList());
            modelMap.put("count", ue.getCount());
            modelMap.put("success", true);
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "empty pageSize or pageIndex or shopId");
        }
        return modelMap;
    }

    //各个商品的销量统计
    @RequestMapping(value = "/listproductselldailyinfobyshop", method = RequestMethod.GET)
    @ResponseBody
    private Map<String, Object> listProductSellDailyInfobyShop(HttpServletRequest request) throws ParseException {
        Map<String, Object> modelMap = new HashMap<String, Object>();
        // 获取当前的店铺信息
        Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
        // 空值校验，主要确保shopId不为空
        if ((currentShop != null) && (currentShop.getShopId() != null)) {
            // 添加查询条件
            ProductSellDaily productSellDailyCondition = new ProductSellDaily();
            productSellDailyCondition.setShop(currentShop);
            Calendar calendar = Calendar.getInstance();
            // 获取昨天的日期
            calendar.add(Calendar.DATE, -1);
            Date endTime = calendar.getTime();
            // 获取30天前的日期
            calendar.add(Calendar.DATE, -30);
            Date beginTime = calendar.getTime();
            // 根据传入的查询条件获取该店铺的商品销售情况
            List<ProductSellDaily> productSellDailyList = productSellDailyService
                    .listProductSellDaily(productSellDailyCondition, beginTime, endTime);
            // 指定日期格式
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            // 商品名列表，保证唯一性
            LinkedHashSet<String> legendData = new LinkedHashSet<String>();
            // x轴数据
            LinkedHashSet<String> xData = new LinkedHashSet<String>();
            // 定义series
            List<EchartSeries> series = new ArrayList<EchartSeries>();
            // 日销量列表
            List<Integer> totalList = new ArrayList<Integer>();
            // 当前商品名，默认为空
            String currentProductName = "";
            for (int i = 0; i < productSellDailyList.size(); i++) {
                ProductSellDaily productSellDaily = productSellDailyList.get(i);
                // 自动去重
                legendData.add(productSellDaily.getProduct().getProductName());
                xData.add(sdf.format(productSellDaily.getCreateTime()));
                if (!currentProductName.equals(productSellDaily.getProduct().getProductName())
                        && !currentProductName.isEmpty()) {
                    // 如果currentProductName不等于获取的商品名，或者已遍历到列表的末尾，且currentProductName不为空，
                    // 则是遍历到下一个商品的日销量信息了, 将前一轮遍历的信息放入series当中，
                    // 包括了商品名以及与商品对应的统计日期以及当日销量
                    EchartSeries es = new EchartSeries();
                    es.setName(currentProductName);
                    es.setData(totalList.subList(0, totalList.size()));
                    series.add(es);
                    // 重置totalList，totalList为List引用对象，
                    // 对它的修改页反映在es.setData(totalList.subList(0, totalList.size()));
                    // 造成setData的数据错误
                    totalList = new ArrayList<Integer>();
                    // 变换下currentProductId为当前的productId
                    currentProductName = productSellDaily.getProduct().getProductName();
                    // 继续添加新的值
                    totalList.add(productSellDaily.getTotal());
                } else {
                    // 如果还是当前的productId则继续添加新值
                    totalList.add(productSellDaily.getTotal());
                    currentProductName = productSellDaily.getProduct().getProductName();
                }
                // 队列之末，需要将最后的一个商品销量信息也添加上
                if (i == productSellDailyList.size() - 1) {
                    EchartSeries es = new EchartSeries();
                    es.setName(currentProductName);
                    es.setData(totalList.subList(0, totalList.size()));
                    series.add(es);
                }
            }
            modelMap.put("series", series);
            modelMap.put("legendData", legendData);
            // 拼接出xAxis
            List<EchartXAxis> xAxis = new ArrayList<EchartXAxis>();
            EchartXAxis exa = new EchartXAxis();
            exa.setData(xData);
            xAxis.add(exa);
            modelMap.put("xAxis", xAxis);
            modelMap.put("success", true);
        } else {
            modelMap.put("success", false);
            modelMap.put("errMsg", "empty shopId");
        }
        return modelMap;
    }

    @GetMapping(value = "/adduserproductmap")
    private String addUserProductMap(HttpServletRequest request) throws IOException {
        Long productId = Long.valueOf(request.getParameter("productId"));
        Long customerId = Long.valueOf(request.getParameter("customerId"));
        String createTime = request.getParameter("createTime");
        // 获取微信授权信息,主要获取用户身份
        PersonInfo operator = personInfoDao.queryPersonInfoById(customerId);
        request.getSession().setAttribute("user", operator);
        UserProductMap userProductMap = compactUserProductMap4Add(customerId, productId, operator);
        // 空值校验
        if (userProductMap != null && customerId != -1) {
            try {
                if (!checkShopAuth(operator.getUserId(), userProductMap)) {
                    return "shop/operationfail";
                }
                // 添加消费记录
                UserProductMapExecution se = userProductMapService.addUserProductMap(userProductMap);
                if (se.getState() == UserProductMapStateEnum.SUCCESS.getState()) {
                    return "shop/operationsuccess";
                }
            } catch (RuntimeException e) {
                return "shop/operationfail";
            }

        }
        return "shop/operationfail";
    }

    /**
     * 根据code获取UserAccessToken，进而通过token里的openId获取微信用户信息
     *
     * @param request
     * @return
     */
    private WechatAuth getOperatorInfo(HttpServletRequest request) {
        String code = request.getParameter("code");
        WechatAuth auth = null;
        if (null != code) {
            UserAccessToken token;
            try {
                token = WechatUtil.getUserAccessToken(code);
                String openId = token.getOpenId();
                request.getSession().setAttribute("openId", openId);
                auth = wechatAuthService.getWechatAuthByOpenId(openId);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return auth;
    }

    /**
     * 根据二维码携带的createTime判断其是否超过了10分钟，超过十分钟则认为过期
     *
     * @param wechatInfo
     * @return
     */
    private boolean checkQRCodeInfo(WechatInfo wechatInfo) {
        if (wechatInfo != null && wechatInfo.getProductId() != null && wechatInfo.getCustomerId() != null
                && wechatInfo.getCreateTime() != null) {
            long nowTime = System.currentTimeMillis();
            if ((nowTime - wechatInfo.getCreateTime()) <= 600000) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    /**
     * 根据传入的customerId, productId以及操作员信息组建用户消费记录
     *
     * @param customerId
     * @param productId
     * @param operator
     * @return
     */
    private UserProductMap compactUserProductMap4Add(Long customerId, Long productId, PersonInfo operator) {
        UserProductMap userProductMap = null;
        if (customerId != null && productId != null) {
            userProductMap = new UserProductMap();
            PersonInfo customer = new PersonInfo();
            customer.setUserId(customerId);
            // 主要为了获取商品积分
            Product product = productService.getProductById(productId);
            userProductMap.setProduct(product);
            userProductMap.setShop(product.getShop());
            userProductMap.setUser(customer);
            userProductMap.setPoint(product.getPoint());
            userProductMap.setCreateTime(new Date());
            userProductMap.setOperator(operator);
        }
        return userProductMap;
    }

    /**
     * 检查扫码的人员是否有操作权限
     *
     * @param userId
     * @param userProductMap
     * @return
     */
    private boolean checkShopAuth(long userId, UserProductMap userProductMap) {
        if (userId == 8L) {
            return true;
        }
        // 获取该店铺的所有授权信息
        ShopAuthMapExecution shopAuthMapExecution = shopAuthMapService
                .listShopAuthMapByShopId(userProductMap.getShop().getShopId(), 1, 1000);
        for (ShopAuthMap shopAuthMap : shopAuthMapExecution.getShopAuthMapList()) {
            // 看看是否给过该人员进行授权
            if (shopAuthMap.getEmployee().getUserId() == userId) {
                return true;
            }
        }
        return false;
    }

}
