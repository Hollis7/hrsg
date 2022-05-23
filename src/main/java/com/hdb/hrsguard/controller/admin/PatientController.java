package com.hdb.hrsguard.controller.admin;

import com.hdb.hrsguard.bean.CodeMsg;
import com.hdb.hrsguard.bean.Result;
import com.hdb.hrsguard.config.SiteConfig;
import com.hdb.hrsguard.constant.FilePathConstant;
import com.hdb.hrsguard.constant.SessionConstant;
import com.hdb.hrsguard.entity.admin.User;
import com.hdb.hrsguard.searchEnc.FileUtil;
import com.hdb.hrsguard.searchEnc.SearchableEncryption;
import com.hdb.hrsguard.service.admin.DepartmentService;
import com.hdb.hrsguard.service.admin.DoctorService;
import com.hdb.hrsguard.service.admin.OperaterLogService;
import com.hdb.hrsguard.service.admin.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @Description
 * @Author hollis
 */
@RequestMapping("/hospital")
@Controller
public class PatientController {
    @Autowired
    DepartmentService departmentService;
    @Autowired
    SiteConfig siteConfig;
    @Autowired
    DoctorService doctorService;
    @Autowired
    UserService userService;
    @Autowired
    OperaterLogService operaterLogService;

    /**
     * 添加病人的信息
     * @param model
     * @return
     */
    @RequestMapping(value = "/addPatient",method = RequestMethod.GET)
    public String addMedicalRecord(HttpServletRequest request,Model model){
        User current_user=(User) request.getSession().getAttribute(SessionConstant.SESSION_USER_LOGIN_KEY);
        String myname= current_user.getUsername();
        model.addAttribute("myname",myname);
        model.addAttribute("siteName",siteConfig.getSiteName());
        model.addAttribute("title",siteConfig.getAddPatient());
        return "admin/patient/add";
    }

    /**
     * 添加病人得病历，或者修改
     * @param user
     * @return
     */
    @RequestMapping(value = "/addPatient",method = RequestMethod.POST)
    @ResponseBody
    public Result<Boolean> addMedicalRecord( User user,HttpServletRequest request) throws IOException {
        if(user == null){
            return Result.error(CodeMsg.DATA_ERROR);
        }
        User current_user=userService.findByUsername(user.getUsername());
        if(current_user==null){
            return Result.error(CodeMsg.ADMIN_PASSWORD_EMPTY);
        }
        if(user.getAge()<0||user.getAge()>110){
            return Result.error(CodeMsg.ADMIN_PATIENT_AGE_ERROR);
        }
        current_user.setAge(user.getAge());
        current_user.setMedical_record(user.getMedical_record());
        current_user.setSex(user.getSex());
        if(userService.save(current_user)==null){
           return Result.error(CodeMsg.ADMIN_PATIENT_UPDATE_ERROR);
        }
        operaterLogService.add(user.getUsername(),"修改病人信息【" + user + "】");
        String recordSuffix="_record.txt";
        String filesavepath=FilePathConstant.SEARCH_RAW_PATH+user.getUsername()+recordSuffix;
        FileUtil.deleteFiles(FilePathConstant.SEARCH_RAW_PATH);
        try {
            FileUtil.write("Patient Name:"+user.getUsername()+"\n", filesavepath,"UTF-8");
            FileUtil.write("Patient sex:"+user.getSex()+"\n",filesavepath,"UTF-8");
            FileUtil.write("Patient age:"+user.getAge()+"\n",filesavepath,"UTF-8");
            FileUtil.write("Patient mecdical_records:"+"\n"+user.getMedical_record()+"\n",filesavepath,"UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        SearchableEncryption.encryptFile(FilePathConstant.SEARCH_RAW_PATH,FilePathConstant.SEARCH_ENC_PATH);
        return Result.success(true);
    }
    @RequestMapping(value = "/person",method = RequestMethod.GET)
    public String showPerson(Model model,HttpServletRequest request){
        User old_user=(User)request.getSession().getAttribute(SessionConstant.SESSION_USER_LOGIN_KEY);
        User current_user=userService.findByUsername(old_user.getUsername());
        model.addAttribute("person",current_user);
        return "admin/patient/person";
    }


}
