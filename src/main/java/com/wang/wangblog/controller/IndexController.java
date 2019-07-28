package com.wang.wangblog.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.wang.wangblog.model.Vo.Result;
import com.wang.wangblog.service.*;
import com.wang.wangblog.utils.HttpUtils;
import com.wang.wangblog.utils.IPKit;
import com.wang.wangblog.utils.PageResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author wang
 * @date 2019/7/28
 */
@Controller
public class IndexController extends BaseController{

    private static final Logger logger = LoggerFactory.getLogger(IndexController.class);
    private static final String BAIDU_APP_KEY = "uVEpXzOtx0t41c2pKSsXh2VWr0DbiroK";

    @Resource
    private BlogService blogService;
    @Resource
    private TagService tagService;
    @Resource
    private LinkService linkService;
    @Resource
    private CommentService commentService;
    @Resource
    private ConfigService configService;
    @Resource
    private CategoryService categoryService;

    @Autowired
    private MailService mailService;


    @RequestMapping("/login")
    public String login(){
        return "/login";

    }

    /**
     * 首页
     *
     * @return
     */
    @GetMapping({"/", "/index", "index.html"})
    public String index(HttpServletRequest request) {
        return this.page(request, 1);
    }

    /**
     * 首页 分页数据
     *
     * @return
     */
    @GetMapping({"/page/{pageNum}"})
    public String page(HttpServletRequest request, @PathVariable("pageNum") int pageNum) {
        PageResult blogPageResult = blogService.getBlogsForIndexPage(pageNum);
        if (blogPageResult == null) {
            return "error/error_404";
        }
        request.setAttribute("blogPageResult", blogPageResult);
//        request.setAttribute("newBlogs", blogService.getBlogListForIndexPage(1));
//        request.setAttribute("hotBlogs", blogService.getBlogListForIndexPage(0));
//        request.setAttribute("hotTags", tagService.getBlogTagCountForIndex());
        request.setAttribute("configurations", configService.getAllConfigs());

        String header = request.getHeader("User-Agent");
        String ip = IPKit.getIpAddrByRequest(request);
        logger.info("访问设备:"+header +" >>> 访问地址:"+ip );
        String location = getIpLocation(ip);
        String date = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss").format(new Date());
        String content =date + " 主机ip为 "+ip +",ip地理位置: "+location+",正在使用 "+header+" 设备访问了你的网站主页.";

//        if (!"0:0:0:0:0:0:0:1".startsWith(ip)){
//            new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    mailService.sendSimpleMail(to,subject,content);
//                    logger.info("邮件发送中...");
//                }
//            }).start();
//
//        }

        request.setAttribute("ip", content);
        return THEME + "/index";
    }


    @PostMapping({"/findPage"})
    @ResponseBody
    public Result findPage(HttpServletRequest request, int pageNum) {
        PageResult blogPageResult = blogService.getBlogsForIndexPage(pageNum);
        if (blogPageResult == null) {
            return new Result(404,"我已翻箱倒柜...亦是空空如也...");
        }
        return new Result(200,"success",blogPageResult);
    }



    public static String getIpLocation(String ip){
        String result = "未知地址";
        if (!StringUtils.isEmpty(ip)){
            String url = "https://sp0.baidu.com/8aQDcjqpAAV3otqbppnN2DJv/api.php?query="+ip+"&co=&resource_id=6006&t=1563871835695&ie=utf8&oe=gbk&cb=op_aladdin_callback&format=json&tn=baidu&cb=jQuery11020995488703613727_1563870746913&_=1563870747047";
            String s = null;
            try {
                s = HttpUtils.get(url);
                logger.info("返回json:" + s);
                String ret = s.substring(s.indexOf("{"),s.length()-2);
                logger.info("截取后的json:" + ret);
                JSONObject jsonObject = JSONObject.parseObject(ret);
                Object status = jsonObject.get("status");
                if (status != null && "0".equals(status.toString())){
                    Object data = jsonObject.get("data");
                    if (data != null && data instanceof JSONArray){
                        JSONArray jsonArray = (JSONArray) data;
                        if (jsonArray != null && !jsonArray.isEmpty()){
                            Object o = jsonArray.get(0);
                            JSONObject json = (JSONObject) o;
                            Object location = json.get("location");
                            result = location.toString();
                        }
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }

        }
        return result;

    }

    @RequestMapping("/findById")
    @ResponseBody
    public Result<Object> findById(Integer id){
        Result ret = new Result<>(200,id +"");
        return ret ;
    }


}
