package com.hdb.hrsguard.controller.admin;

import com.hdb.hrsguard.config.SiteConfig;
import com.hdb.hrsguard.constant.FilePathConstant;
import com.hdb.hrsguard.entity.admin.Department;
import com.hdb.hrsguard.searchEnc.SearchableEncryption;
import com.hdb.hrsguard.service.admin.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @Description
 * @Author hollis
 */
@RequestMapping("/depMatch")
@Controller
public class DepMatchController {
    @Autowired
    private DepartmentService departmentService;
    @Autowired
    SiteConfig siteConfig;
    private static final String check_str="#";

    /**
     * 多个部门的关键词对单个病人的病历进行搜索
     * @param model
     * @param request
     * @return
     * @throws IOException
     */
    @RequestMapping(value = "/mydepartment",method = RequestMethod.GET)
    public String getMyDepartment(Model model, HttpServletRequest request) throws IOException {
        List<String> depMajorKeys=departmentService.findAllDepMajorKey();
        List<Department> matchedDepartment=new ArrayList<Department>();
        String str;
        for(String majorkey:depMajorKeys) {
            String[] major_key = majorkey.split("\\s+");
            for (String mk : major_key) {
                str = SearchableEncryption.searchFile(mk, FilePathConstant.SEARCH_ENC_PATH);
                if (str.equals(check_str)) continue;
                else {
                    matchedDepartment.add(departmentService.findByDep_major_key(majorkey));
                    break;
                }
            }
        }
        model.addAttribute("matchedDeps",matchedDepartment);
        request.getSession().setAttribute("matchedDeps",matchedDepartment);
        return "admin/department/mydep";
    }

}
