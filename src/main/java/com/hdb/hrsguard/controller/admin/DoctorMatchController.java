package com.hdb.hrsguard.controller.admin;

import com.hdb.hrsguard.abse.filepConstant.SearchEncPath;
import com.hdb.hrsguard.abse.filepConstant.SufixConstant;
import com.hdb.hrsguard.abse.scheme.*;
import com.hdb.hrsguard.bean.CodeMsg;
import com.hdb.hrsguard.bean.Result;
import com.hdb.hrsguard.config.SiteConfig;
import com.hdb.hrsguard.constant.FilePathConstant;
import com.hdb.hrsguard.entity.admin.Department;
import com.hdb.hrsguard.entity.admin.Doctor;
import com.hdb.hrsguard.entity.admin.WFIndex;
import com.hdb.hrsguard.searchEnc.FileUtil;
import com.hdb.hrsguard.service.admin.DoctorService;
import com.hdb.hrsguard.service.admin.WFIndexService;
import it.unisa.dia.gas.jpbc.Pairing;
import it.unisa.dia.gas.plaf.jpbc.pairing.PairingFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Description
 * @Author hollis
 */
@RequestMapping("/docMatch")
@Controller
public class DoctorMatchController {
    //universal attribute set, any attribute is in u.
    final static String []u={"UESTC","patient", "doctor","peter","director","deputy","male","female"};
    //attributes of the user
    final static String []attrs =  {"UESTC","patient","doctor","peter","deputy","male"};
    //路径以及文件后缀
    private static final String doctor_plain_path= FilePathConstant.DOCTOR_PLAINTEXT_PATH;
    private static final String doctor_cipher_path=FilePathConstant.DOCTOR_CIPHERTEXT_PATH;
    private static final String sufix= SufixConstant.TEXT;
    @Autowired
    private WFIndexService wfIndexService;
    @Autowired
    private SiteConfig siteconfig;
    @Autowired
    DoctorService doctorService;
    private Logger log= LoggerFactory.getLogger(DoctorMatchController.class);
    @RequestMapping(value = "/matdoctor",method = RequestMethod.POST)
    @ResponseBody
    public Result<Boolean> inputMyCareWord(HttpServletRequest request, String word) throws IOException {
        if(word==null){
            return Result.error(CodeMsg.ADMIN_WORD_ERROR);
        }
        log.info("病人输入了:"+word);
        List<Department> department=(List<Department>)request.getSession().getAttribute("matchedDeps");
        if(department==null){
            return Result.error(CodeMsg.ADMIN_PATIENT_MATCH_DEP_ERROR);
        }
        Pairing pairing= PairingFactory.getPairing(SearchEncPath.TypeAPath);
        int length=u.length*2;
        BswabePub pub = new BswabePub();
        BswabeMsk msk = new BswabeMsk();
        BswabePrv prv;
        BswabeToken token;
        BswabeCph cph=new BswabeCph();
        boolean result=false;
        List<Doctor> doctors=new ArrayList<>();
        //获取匹配的科室的所有医生
        List<WFIndex> wfIndexLists=wfIndexService.findByDoc_dep_id(department.get(0).getId());
        //获取公钥和主密钥，以便产生私钥
        log.info("department id is:"+department.get(0).getId());
        Bswabe.getFilePubKey(pub,length);
        Bswabe.getFileMskKey(msk,length);
        prv = Bswabe.keygen(u,pub, msk, attrs);
        token = Bswabe.tokgen(prv,pub,word);
        //令牌与关键词匹配进行
        for(WFIndex wf:wfIndexLists){
            Bswabe.getCphFromWFIndexDB(cph,wf,pairing);
            result = Bswabe.search(pub, token, cph);
            if(result){//匹配成功，得到对应关键词的对应的医生列表
                log.info("匹配成功");
                String doctorlist=wf.getFile_list();
                List<String> files=FileUtil.getWords(doctorlist);
                for(String f:files){
                    String content=FileUtil.read(FilePathConstant.DOCTOR_PLAINTEXT_PATH+f,"utf-8");
                    List<String> con=FileUtil.getWords(content);
                    int id=Integer.parseInt(con.get(0));
                    doctors.add(doctorService.findByDoctorId((long) id));
                }
                break;//唯一匹配的加密关键词找到了
            }
        }
        request.getSession().setAttribute("mydoctor",doctors);
        return Result.success(true);
    }
    @RequestMapping(value = "/inputDemand",method = RequestMethod.GET)
    public String inputMyCareWord(HttpServletRequest request, Model model){
        model.addAttribute("siteName",siteconfig.getSiteName());
        model.addAttribute("title","demands");
        List<String> myAttrs=new ArrayList<>(Arrays.asList(attrs));
        model.addAttribute("myAttrs",myAttrs);
        return "admin/doctor/expectedDoctor";
    }
    @RequestMapping(value = "/matchedDoctors",method = RequestMethod.GET)
    public String showDoctors(Model model,HttpServletRequest request){
        List<Doctor> doctors=(List<Doctor>) request.getSession().getAttribute("mydoctor");
        model.addAttribute("mydoctor",doctors);
        return "admin/doctor/mydoctor";
    }
    @RequestMapping(value = "/selectDoc",method = RequestMethod.POST)
    @ResponseBody
    public Result<Boolean> mySeletcedDoc(HttpServletRequest request,String doc_id) throws Exception{
       if(doc_id==null) return Result.error(CodeMsg.ADMIN_SELECT_DOC_ERROR);
       String[] selectdocs=doc_id.split("#");
       if(selectdocs.length !=1)return Result.error(CodeMsg.ADMIN_SELECT_DOC_ERROR);
       log.info("用户选择的医生为:"+selectdocs[0]);
       Doctor doctor=doctorService.findByDoctorId(Long.valueOf(selectdocs[0]));
       if(doctor==null)return Result.error(CodeMsg.ADMIN_SELECT_DOC_EMPTY);
       request.getSession().setAttribute("mySelectDoc",doctor);
       return Result.success(true);
    }
    @RequestMapping(value = "/myAppointment")
    public String myAppointment(Model model,HttpServletRequest request){
        Doctor doctor=(Doctor) request.getSession().getAttribute("mySelectDoc");
        model.addAttribute("mydoctor",doctor);
        return "admin/patient/myAppoint";
    }


}
