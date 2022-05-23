package com.hdb.hrsguard.controller.admin;

import com.hdb.hrsguard.bean.CodeMsg;
import com.hdb.hrsguard.bean.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * @Description
 * @Author hollis
 */
@RequestMapping("/file")
@Controller
public class FileController {
    @Value("${barter.upload.file.sufix}")
    private String uploadFileSufix;

    @Value("${barter.upload.file.path}")
    private String uploadFilePath;//文件保存位置
    private Logger log = LoggerFactory.getLogger(FileController.class);
    public Result<String> uploadFile(@RequestParam(name="textfile",required=true) MultipartFile textfile){
        //判断文件类型是否是图片
        String filename = textfile.getOriginalFilename();
        //获取文件后缀
        String suffix = filename.substring(filename.lastIndexOf("."),filename.length());
        if(!uploadFileSufix.contains(suffix.toLowerCase())){
            return Result.error(CodeMsg.ADMIN_FILE_FORMAT_ERROR);
        }
        //准备保存文件
        File filePath = new File(uploadFilePath);
        if(!filePath.exists()){
            //若不存在文件夹，则创建一个文件夹
            filePath.mkdir();
        }
        try {
            textfile.transferTo(new File(uploadFilePath+"/"+filename));
        } catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        log.info("文件上传成功，保存位置：" + uploadFilePath + "/"+filename);
        return Result.success(filename);
    }


}
