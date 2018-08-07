package com.richinfo.annotation.controller;

import com.alibaba.fastjson.JSONArray;
import com.richinfo.annotation.po.Carbrand;
import com.richinfo.annotation.po.Message;
import com.richinfo.annotation.po.Sysuser;
import com.richinfo.annotation.repository.CarbrandRepository;
import com.richinfo.annotation.repository.MessageRepository;
import com.richinfo.annotation.repository.SysuserRepository;
import com.richinfo.util.DateTimeUtils;
import com.richinfo.util.PingyinUtils;
import com.richinfo.util.Variable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Controller
@RequestMapping("/admin")
@Configuration
@PropertySource(value={"classpath:config/config.properties"}, ignoreResourceNotFound = true)
public class CarbrandController {

    private Logger logger = LoggerFactory.getLogger(CarbrandController.class);

    @Autowired
    private SysuserRepository sysuserRepository;

    @Autowired
    private CarbrandRepository carbrandRepository;


    /**
     * 跳转到添加车辆品牌页面
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value="/toaddbrand")
    public String toaddbrand(Model model,HttpServletRequest request){
        String id= request.getParameter("id");
        String title = "新增品牌";
        if(id!=null&&!id.isEmpty()){
            Carbrand carbrand = carbrandRepository.findById(id).get();
            if(carbrand!=null){
                model.addAttribute("id",id);
                model.addAttribute("first",carbrand.getFirst());
                model.addAttribute("brand",carbrand.getBrand());
                model.addAttribute("picurl",carbrand.getPicurl());
            }
            title="修改品牌";
        }
        return "addbrand";
    }


    /**
     * 保存车辆品牌
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value="/savebrand",method = RequestMethod.POST)
    public String savebrand(Model model, HttpServletRequest request){
        String id = request.getParameter("id");
        String first = request.getParameter("first");
        String brand = request.getParameter("brand");
        String picurl = request.getParameter("picurl");
        if(id!=null&&!id.isEmpty()){//修改
            Carbrand carbrand = carbrandRepository.findById(id).get();
            if(carbrand!=null) {
                if(!brand.equals(carbrand.getBrand())){
                    Carbrand carbrand1 = carbrandRepository.findByBrand(brand);
                    if(carbrand1!=null){
                        model.addAttribute("id",id);
                        model.addAttribute("first",first);
                        model.addAttribute("brand",brand);
                        model.addAttribute("picurl",picurl);
                        model.addAttribute("msg","品牌名重复");
                        return "addbrand";
                    }
                    carbrand.setBrand(brand);
                }
                carbrand.setFirst(first);
                carbrand.setPicurl(picurl);
                carbrandRepository.save(carbrand);
            }
        }else{
            Carbrand carbrand1 = carbrandRepository.findByBrand(brand);
            if(carbrand1!=null){
                model.addAttribute("first",first);
                model.addAttribute("brand",brand);
                model.addAttribute("picurl",picurl);
                model.addAttribute("msg","品牌名重复");
                return "addbrand";
            }
            id = UUID.randomUUID().toString();
            Carbrand carbrand = new Carbrand();
            carbrand.setId(id);
            carbrand.setBrand(brand);
            carbrand.setPicurl(picurl);
            carbrand.setFirst(first);
            carbrandRepository.save(carbrand);
        }
        return "redirect:../admin/brandlist";
    }

    /**
     * 品牌列表
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value="/brandlist")
    public String brandlist(Model model,HttpServletRequest request){
        Sort sort = Sort.by("first");
        List<Carbrand> carbrandList = carbrandRepository.findAll(sort);
        String html = "";
        for(Carbrand carbrand:carbrandList){
            //<input type="radio"  name="brand" value=""> <img th:src="" width="48" height="48">
            html+="<div><label><input type=\"radio\"  name=\"brand\" value=\""+carbrand.getBrand()+"\">"+carbrand.getBrand()+" <img src=\""+carbrand.getPicurl()+"\" width=\"48\" height=\"48\"></label></div>";
        }
        logger.info(html);
//            if(carbrand.getFirst()==null||carbrand.getFirst().isEmpty()){
//                String firstl = carbrand.getBrand().substring(0,1);
//                String first = PingyinUtils.getFirstLetter(firstl).toUpperCase();
//                logger.info("first===="+first);
//                carbrand.setFirst(first);
//                carbrandRepository.save(carbrand);
//            }
//        }
        model.addAttribute("carbrandList",carbrandList);
        return "brandlist";
    }


    /**
     * 删除品牌
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value="/delbrand")
    public String delbrand(Model model,HttpServletRequest request) {
        String id =request.getParameter("id");
        Carbrand carbrand = carbrandRepository.findById(id).get();
        if(carbrand!=null){
            carbrandRepository.delete(carbrand);
        }
        return "redirect:../admin/brandlist";
    }
}
