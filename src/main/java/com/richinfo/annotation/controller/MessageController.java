package com.richinfo.annotation.controller;

import com.alibaba.fastjson.JSONObject;
import com.richinfo.annotation.po.*;
import com.richinfo.annotation.repository.*;
import com.richinfo.util.DateTimeUtils;
import com.richinfo.util.FileUtils;
import com.richinfo.util.RandomUtil;
import com.richinfo.util.Variable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Controller
@RequestMapping("/admin")
@Configuration
@PropertySource(value={"classpath:config/config.properties"}, ignoreResourceNotFound = true)
public class MessageController {

    private Logger logger = LoggerFactory.getLogger(MessageController.class);

    @Autowired
    private SysuserRepository sysuserRepository;

    @Autowired
    private MessageRepository messageRepository;


    @RequestMapping(value="/tosendmsg")
    public String tosendmsg(Model model,HttpServletRequest request){
        List<String> rolelist = new ArrayList<>();
        rolelist.add(Variable.USER_ROLE_NORMAL);
        rolelist.add(Variable.USER_ROLE_FIRSTCHECKER);
        rolelist.add(Variable.USER_ROLE_SECONDCHECKER);
        List<Sysuser> sysuserList = sysuserRepository.findByRoleInAndStatus(rolelist,1);
        model.addAttribute("sysuserList",sysuserList);
        return "sendmsg";
    }


    @RequestMapping(value="/sendmsg",method = RequestMethod.POST)
    public String sendmsg(Model model, HttpServletRequest request){
        HttpSession session = request.getSession();
        Sysuser sysuser = (Sysuser)session.getAttribute(Variable.SESSION_SYSUSER);
        String sysusers = request.getParameter("sysusers");
        String msg =request.getParameter("msg");
        String title = request.getParameter("title");
        if(sysusers!=null&&!sysusers.isEmpty()) {
            String[] sysuserids = sysusers.split(",");
            for(String id:sysuserids){
                Optional<Sysuser> optional =  sysuserRepository.findById(id);
                Sysuser receiver = optional.get();
                if(receiver!=null){
                    Message message = new Message();
                    message.setId(UUID.randomUUID().toString());
                    message.setMsg(msg);
                    message.setTitle(title);
                    message.setReceiver(receiver);
                    message.setSender(sysuser);
                    message.setStatus(Variable.MSG_STATUS_NEW);
                    message.setSendtime(DateTimeUtils.getDate());
                    messageRepository.save(message);
                }
            }
        }

        return "redirect:../admin/msglist";
    }


    @RequestMapping(value="/msglist")
    public String msglist(Model model,HttpServletRequest request){
        HttpSession session = request.getSession();
        Sysuser sysuser = (Sysuser)session.getAttribute(Variable.SESSION_SYSUSER);
        if(sysuser.getRole().equals(Variable.USER_ROLE_MANAGER)) {
            List<Message> msglist = messageRepository.findBySender(sysuser);
            model.addAttribute("msglist", msglist);
        }else{
            String status = request.getParameter("status");
            List<Message> msglist;
            if(status!=null&&!status.isEmpty()){
                msglist = messageRepository.findByReceiverAndStatus(sysuser,Integer.valueOf(status));
            }else {
                msglist = messageRepository.findByReceiver(sysuser);
            }
            model.addAttribute("msglist", msglist);
        }
        return "msglist";
    }

    @RequestMapping(value="/viewmsg")
    public String viewmsg(Model model,HttpServletRequest request){
        String id = request.getParameter("id");
        Optional<Message> optional = messageRepository.findById(id);
        Message msg = optional.get();
        HttpSession session = request.getSession();
        Sysuser sysuser = (Sysuser)session.getAttribute(Variable.SESSION_SYSUSER);
        if(msg!=null && msg.getStatus()==Variable.MSG_STATUS_NEW&&msg.getReceiver().getId().equals(sysuser.getId())){
            msg.setReadtime(DateTimeUtils.getDate());
            msg.setStatus(Variable.MSG_STATUS_READ);
            messageRepository.save(msg);
        }
        model.addAttribute("msg",msg);
        return "viewmsg";
    }
}
