package com.hdb.hrsguard.controller.admin;

import com.hdb.hrsguard.bean.CodeMsg;
import com.hdb.hrsguard.bean.Result;
import com.hdb.hrsguard.config.SiteConfig;
import com.hdb.hrsguard.entity.admin.Department;
import com.hdb.hrsguard.entity.admin.Doctor;
import com.hdb.hrsguard.service.admin.DepartmentService;
import com.hdb.hrsguard.service.admin.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Description
 * @Author hollis
 */
@RequestMapping("/hospital")
@Controller
public class DepartmentController {
    @Autowired
    DepartmentService departmentService;
    @Autowired
    SiteConfig siteConfig;
    @Autowired
    DoctorService doctorService;

    /**
     * 显示所有的部门信息
     * @param model
     * @return
     */
    @RequestMapping(value="/allDeps")
    public String showAll(Model model){
        model.addAttribute("siteName",siteConfig.getSiteName());
        List<Department> departments = departmentService.findAll();
        model.addAttribute("departments", departments);
        return "admin/department/list";
    }

    /**
     * 挑选某个部门的id
     * @param request
     * @param id
     * @return
     */
    @RequestMapping(value = "/selectDep",method = RequestMethod.POST)
    @ResponseBody
    public Result<Boolean> showOneDepartment(HttpServletRequest request, Long id){
        request.getSession().setAttribute("selectedDepId",id);
        if(id==null){
            return Result.error(CodeMsg.ADMIN_PATIENT_SELECT_DEPID_ERROR);
        }
        return  Result.success(true);
    }
    @RequestMapping(value = "/oneDepartment")
    public String showOneDepartment(Model model, HttpServletRequest request){
        model.addAttribute("siteName",siteConfig.getSiteName());
        Long id=(Long)request.getSession().getAttribute("selectedDepId");
        List<Doctor> doctors=doctorService.findByDocDepId(id);
        model.addAttribute("doctors",doctors);
        List<Department> departments = departmentService.findAll();
        model.addAttribute("departments", departments);
        return "admin/department/oneDepdocs";
    }



}
