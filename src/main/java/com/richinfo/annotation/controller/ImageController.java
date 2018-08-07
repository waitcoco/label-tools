package com.richinfo.annotation.controller;

import com.richinfo.annotation.po.Message;
import com.richinfo.annotation.po.Sysuser;
import com.richinfo.annotation.repository.MessageRepository;
import com.richinfo.annotation.repository.SysuserRepository;
import com.richinfo.util.BaiduUtil;
import com.richinfo.util.DateTimeUtils;
import com.richinfo.util.Variable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Controller
@RequestMapping("/admin")
@Configuration
@PropertySource(value={"classpath:config/config.properties"}, ignoreResourceNotFound = true)
public class ImageController {

    private Logger logger = LoggerFactory.getLogger(ImageController.class);



    @RequestMapping(value="/recognizeimage",method = { RequestMethod.POST }, produces="application/json;charset=UTF-8")
    @ResponseBody
    public String recognizeimage(Model model,HttpServletRequest request,HttpServletResponse response,MultipartFile file){
        response.addHeader("Access-Control-Allow-Origin", "*");
        if(file.isEmpty()){
            return "{\"msg\":\"上传文件失败1\"}";
        }
        HttpSession session = request.getSession();
        Sysuser sysuser = (Sysuser)session.getAttribute(Variable.SESSION_SYSUSER);
        String fileName = file.getOriginalFilename();

        String suffix = fileName.substring(fileName.lastIndexOf("."));
        String filenamen = sysuser.getUsername()+suffix;
        String path = System.getProperty("user.dir") + "/uploadFile" ;
        File dest = new File(path + "/" + filenamen);
        if(!dest.getParentFile().exists()){ //判断文件父目录是否存在
            dest.getParentFile().mkdir();
        }
        try {
            file.transferTo(dest); //保存文件
            String result = BaiduUtil.carDetect(dest.getAbsolutePath());
            return result;
            //return "true";
        } catch (IllegalStateException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return "{\"msg\":\"上次文件失败2\"}";
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return "{\"msg\":\"上次文件失败3\"}";
        }

    }



}
