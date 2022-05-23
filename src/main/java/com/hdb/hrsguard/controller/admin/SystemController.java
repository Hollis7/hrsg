package com.hdb.hrsguard.controller.admin;


import com.hdb.hrsguard.bean.CodeMsg;
import com.hdb.hrsguard.bean.Result;
import com.hdb.hrsguard.config.SiteConfig;
import com.hdb.hrsguard.constant.SessionConstant;
import com.hdb.hrsguard.entity.admin.OperaterLog;
import com.hdb.hrsguard.entity.admin.User;
import com.hdb.hrsguard.service.admin.OperaterLogService;
import com.hdb.hrsguard.service.admin.UserService;
import com.hdb.hrsguard.util.StringUtil;
import com.hdb.hrsguard.util.ValidateEntityUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;


@RequestMapping("/system")
@Controller
public class SystemController {

    @Autowired
    private SiteConfig siteconfig;
    @Autowired
    private OperaterLogService operaterLogService;
    private Logger log= LoggerFactory.getLogger(SystemController.class);
    @Autowired
    private OperaterLogService operatorLogLogService;
    @Autowired
    private UserService userService;

    @RequestMapping(value="/login",method= RequestMethod.GET)
    public String login(Model model){
        model.addAttribute("siteName",siteconfig.getSiteName());
        model.addAttribute("siteUrl",siteconfig.getSiteUrl());
        return "admin/system/login";
    }
    @RequestMapping(value="/login",method= RequestMethod.POST)
    @ResponseBody
    public Result<Boolean> login(HttpServletRequest request, User user, String cpacha){
        if(user == null){
            return Result.error(CodeMsg.DATA_ERROR);
        }
        //用统一验证实体方法验证是否合法
        CodeMsg validate = ValidateEntityUtil.validate(user);
        if(validate.getCode() != CodeMsg.SUCCESS.getCode()){
            return Result.error(validate);
        }
        //表示实体信息合法，开始验证验证码是否为空
        if(StringUtils.isEmpty(cpacha)){
            return Result.error(CodeMsg.CPACHA_EMPTY);
        }
        //说明验证码不为空，从session里获取验证码
        Object attribute = request.getSession().getAttribute("admin_login");
        if(attribute == null){
            return Result.error(CodeMsg.SESSION_EXPIRED);
        }
        //表示session未失效，进一步判断用户填写的验证码是否正确
        if(!cpacha.equalsIgnoreCase(attribute.toString())){
            return Result.error(CodeMsg.CPACHA_ERROR);
        }
        //表示验证码正确，开始查询数据库，检验密码是否正确
        User findByUsername = userService.findByUsername(user.getUsername());
        //判断是否为空
        if(findByUsername == null){
            return Result.error(CodeMsg.ADMIN_USERNAME_NO_EXIST);
        }
        //表示用户存在，进一步对比密码是否正确
        if(!findByUsername.getPassword().equals(user.getPassword())){
            return Result.error(CodeMsg.ADMIN_PASSWORD_ERROR);
        }
        //表示密码正确，接下来判断用户状态是否可用
        if(findByUsername.getStatus() == User.ADMIN_USER_STATUS_UNABLE){
            return Result.error(CodeMsg.ADMIN_USER_UNABLE);
        }
        //检查一切符合，可以登录，将用户信息存放至session
        request.getSession().setAttribute("user", findByUsername);
        request.getSession().setAttribute(SessionConstant.SESSION_USER_LOGIN_KEY,findByUsername);
        request.getSession().setAttribute("admin_login", null);
        //将登陆记录写入日志库
        OperaterLog operaterLog=new OperaterLog();
        operaterLog.setOperater(user.getUsername());
        operaterLog.setContent("用户【"+user.getUsername()+"】于【" + StringUtil.getFormatterDate(new Date(), "yyyy-MM-dd HH:mm:ss") + "】登录系统！");
        operaterLogService.save(operaterLog);

        log.info("用户成功登录，user = " + findByUsername);
        return Result.success(true);
    }
    /**
     * 登录成功后的系统主页
     * @param model
     * @return
     */
    @RequestMapping(value="/welcome")
    public String index(Model model){
        model.addAttribute("siteName",siteconfig.getSiteName());
        return "admin/system/welcome";
    }
}
