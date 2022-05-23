package com.hdb.hrsguard.controller.admin;

import com.hdb.hrsguard.abse.filepConstant.SearchEncPath;
import com.hdb.hrsguard.abse.filepConstant.SufixConstant;
import com.hdb.hrsguard.abse.scheme.Bswabe;
import com.hdb.hrsguard.abse.scheme.BswabeCph;
import com.hdb.hrsguard.abse.scheme.BswabeMsk;
import com.hdb.hrsguard.abse.scheme.BswabePub;
import com.hdb.hrsguard.constant.FilePathConstant;
import com.hdb.hrsguard.entity.admin.Doctor;
import com.hdb.hrsguard.entity.admin.WFIndex;
import com.hdb.hrsguard.searchEnc.FileUtil;
import com.hdb.hrsguard.searchEnc.SearchableEncryption;
import com.hdb.hrsguard.service.admin.DoctorService;
import com.hdb.hrsguard.service.admin.WFIndexService;
import it.unisa.dia.gas.jpbc.Pairing;
import it.unisa.dia.gas.plaf.jpbc.pairing.PairingFactory;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

/**
 * @Description 对于基于属性的可搜索加密
 * @Author hollis
 */
@RequestMapping("/hospital/AttributeBaseSE")
@Controller
public class AttributeBaseSE {
    @Autowired
    private DoctorService doctorService;
    @Autowired
    private WFIndexService wfIndexService;
    private Logger log= LoggerFactory.getLogger(AttributeBaseSE.class);
    //universal attribute set, any attribute is in u.
    final static String []u={"UESTC","patient", "doctor","peter","director","deputy","male","female"};
    //attributes of the policy
    final static String []policy = {"UESTC","patient","doctor","peter","deputy","male"};
    //路径以及文件后缀
    private static final String doctor_plain_path=FilePathConstant.DOCTOR_PLAINTEXT_PATH;
    private static final String doctor_cipher_path=FilePathConstant.DOCTOR_CIPHERTEXT_PATH;
    private static final String sufix= SufixConstant.TEXT;
    @RequestMapping(value="/generateKeys")
    public String generateKeys() throws IOException {
        BswabePub pub = new BswabePub();
        BswabeMsk msk = new BswabeMsk();
        Bswabe.setup(u,pub, msk);
        /*start to generate public keys and msk keys,msk keys are a must
        * to generate further private keys */
        return "admin/system/index";
    }

    /**
     * 生成加密关键词和文件列表的索引
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/create_wfindex")
    public String create_wfindex(HttpServletRequest request) throws Exception {
        wfIndexService.deleteAll();
        Pairing pairing= PairingFactory.getPairing(SearchEncPath.TypeAPath);
        BswabePub pub = new BswabePub();
        BswabeMsk msk = new BswabeMsk();
        BswabeCph cph;
        int length=u.length*2;
        FileUtil.deleteFiles(doctor_plain_path);
        FileUtil.deleteFiles(doctor_cipher_path);
        List<Doctor> doctors=doctorService.findAllDoctors();
        //生成医生信息（id+introduction），并用可搜索加密尽心加密，文件无规则命名
        for(Doctor doctor:doctors){
            String doctor_message=doctor.getDoc_introduction();
            Long id=doctor.getId();
            String doctor_major=doctor.getDoc_major();
            String filename= RandomStringUtils.randomAlphanumeric(5);
            FileUtil.write(id.toString()+"\t",doctor_plain_path+filename,"UTF-8");
            FileUtil.write(doctor_message+"\t",doctor_plain_path+filename,"UTF-8");
            FileUtil.write(doctor_major,doctor_plain_path+filename,"UTF-8");
        }
        SearchableEncryption.encryptFile(doctor_plain_path,doctor_cipher_path);
        //进行加密关键词和文件列表创建
        Bswabe.getFilePubKey(pub,length);
        Bswabe.getFileMskKey(msk,length);
        for(Doctor doctor:doctors){
            List<String> words=FileUtil.getWords(doctor.getDoc_major());
            Long dep_id=doctor.getDepartment().getId();
            for(String word:words){
                String filelist=SearchableEncryption.searchFile(word,doctor_cipher_path);
                cph=Bswabe.enc(u,pub,policy,word);
                WFIndex wfIndex=Bswabe.createOneWFIndex(cph,filelist,dep_id,pairing);
                wfIndexService.save(wfIndex);
            }
        }
        log.info("索引表建立完成");
        return "admin/system/index";
    }
}
