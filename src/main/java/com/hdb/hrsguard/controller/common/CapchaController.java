package com.hdb.hrsguard.controller.common;


import com.hdb.hrsguard.util.CpachaUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/*
*Description:系统验证码公用控制器
*/
@Controller
@RequestMapping("/cpacha")
public class CapchaController {
    /*
    *Description:通用验证码生成器
    */
    private Logger log= LoggerFactory.getLogger(CapchaController.class);
    @RequestMapping(value = "/generateCpacha",method = RequestMethod.GET)
    public void generateCpacha(
            @RequestParam(name="vl",defaultValue = "4") Integer vcodeLength,
            @RequestParam(name="fs",defaultValue = "21") Integer fontSize,
            @RequestParam(name="w",defaultValue = "98") Integer width,
            @RequestParam(name="h",defaultValue = "33") Integer height,
            @RequestParam(name="method",defaultValue = "admin_login") String method,
            HttpServletRequest request,
            HttpServletResponse response){
        CpachaUtil cpachaUtil=new CpachaUtil(vcodeLength,fontSize,width,height);
        String generatorVCode= cpachaUtil.generatorVCode();
        //将生成的验证码放入session，以供放后面程序的验证使用
        request.getSession().setAttribute(method,generatorVCode);
        log.info("验证码成功生成,method="+method+",value="+generatorVCode);

        try {
            ImageIO.write(cpachaUtil.generatorRotateVCodeImage(generatorVCode,true),"gif",response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
