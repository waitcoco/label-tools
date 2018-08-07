package com.richinfo.annotation.controller;

import com.richinfo.annotation.po.*;
import com.richinfo.annotation.repository.*;
import com.richinfo.util.MD5Helper;
import com.richinfo.util.Variable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class LoginController {
    @Autowired
    private TaskbatchRepository taskbatchRepository;
    @Autowired
    private SysuserRepository sysuerRepository;
    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private TaskbatchUserRepository taskbatchUserRepository;
    
    private Logger logger = LoggerFactory.getLogger(LoginController.class);

    /**
     * 跳转至登录
     * @return
     */
    @RequestMapping(value="/login")
    public String gologn(){
        return "loginpage";

    }


    /**
     * 登录
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value="/loginto",method = RequestMethod.POST)
    public String login(Model model,HttpServletRequest request) {
        //获取用户和密码
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String md5pwd = MD5Helper.MD5(password);
        Sysuser sysuser = sysuerRepository.findByUsername(username);
        if(sysuser!=null){
            if(sysuser.getStatus()!=1){
                model.addAttribute("msg","用户已被暂停，无权登录！");
                return "loginpage";
            }
            if(md5pwd.equals(sysuser.getPassword())){
               // model.addAttribute("sysuser",sysuser);
                HttpSession session = request.getSession();
                session.setAttribute(Variable.SESSION_SYSUSER,sysuser);
                return "redirect:../admin/index";
            }else{
                model.addAttribute("msg","密码错误，请重新输入！");
                return "loginpage";
            }
        }else{
            model.addAttribute("msg","未找到用户名为:"+username+"的用户！");
            return "loginpage";
        }

    }

    /**
     * 首页
     * @return
     */
    @RequestMapping(value="/index")
    public String index(Model model,HttpServletRequest request){
        HttpSession session = request.getSession();
        Sysuser sysuser = (Sysuser)session.getAttribute(Variable.SESSION_SYSUSER);
        //logger.info("sysuser.getRole()==="+sysuser.getRole());
        if(sysuser.getRole().equals(Variable.USER_ROLE_MANAGER)){//管理员
            Sysuser sysuser1 = new Sysuser();
            sysuser1.setRole(Variable.USER_ROLE_NORMAL);
            Example<Sysuser> exampleu = Example.of(sysuser1);
            long usercount = sysuerRepository.count(exampleu);
            //logger.info("usercount==="+usercount);
            Taskbatch taskbatch = new Taskbatch();
            Example<Taskbatch> exampletb = Example.of(taskbatch);
            long taskbatchcount = taskbatchRepository.count(exampletb);

            Task task = new Task();
            task.setStatus(Variable.TASK_STATUS_FINISH);
            Example<Task> examplet = Example.of(task);
            long taskfinishicount = taskRepository.count(examplet);

            model.addAttribute("usercount",usercount);
            model.addAttribute("taskbatchcount",taskbatchcount);
            model.addAttribute("taskfinishicount",taskfinishicount);
        }else{
            Message message = new Message();
            message.setStatus(Variable.MSG_STATUS_NEW);
            message.setReceiver(sysuser);
            Example<Message> examplemsg = Example.of(message);
            long msgcount= messageRepository.count(examplemsg);
            model.addAttribute("msgcount",msgcount);
            if(sysuser.getRole().equals(Variable.USER_ROLE_NORMAL)){
                Task task = new Task();
                task.setSysuser(sysuser);
                task.setStatus(Variable.TASK_STATUS_FINISH);
                Example<Task> examplet = Example.of(task);
                long taskfinishicount = taskRepository.count(examplet);
                model.addAttribute("taskfinishicount",taskfinishicount);
                List<TaskbatchUser> taskbatchUserList = taskbatchUserRepository.findByUser(sysuser);
                int taskbdocount=0;
                for(TaskbatchUser taskbatchUser:taskbatchUserList){
                    Taskbatch taskbatch = taskbatchRepository.findByBatchno(taskbatchUser.getTaskbatchno());
                    if(taskbatch.getStatus()==Variable.TASKBATCH_STATUS_PUB){
                        taskbdocount++;
                    }
                }
                model.addAttribute("taskbdocount",taskbdocount);
            }else if(sysuser.getRole().equals(Variable.USER_ROLE_FIRSTCHECKER)){
                Task task = new Task();
                task.setFirstchecker(sysuser);
                task.setStatus(Variable.TASK_STATUS_SUBMIT);
                Example<Task> examplet = Example.of(task);
                long taskbdocount = taskRepository.count(examplet);
                model.addAttribute("taskbdocount",taskbdocount);

                Task task1 = new Task();
                task.setFirstchecker(sysuser);
                task.setStatus(Variable.TASK_STATUS_FINISH);
                Example<Task> examplet1 = Example.of(task1);
                long taskfinishicount = taskRepository.count(examplet1);
                model.addAttribute("taskfinishicount",taskfinishicount);

            }else if(sysuser.getRole().equals(Variable.USER_ROLE_SECONDCHECKER)){
                Task task = new Task();
                task.setSecondchecker(sysuser);
                task.setStatus(Variable.TASK_STATUS_FIRSTCHECK);
                Example<Task> examplet = Example.of(task);
                long taskbdocount = taskRepository.count(examplet);
                model.addAttribute("taskbdocount",taskbdocount);

                Task task1 = new Task();
                task.setSecondchecker(sysuser);
                task.setStatus(Variable.TASK_STATUS_FINISH);
                Example<Task> examplet1 = Example.of(task1);
                long taskfinishicount = taskRepository.count(examplet1);
                model.addAttribute("taskfinishicount",taskfinishicount);
            }

        }
        return "main";

    }

    /**
     * 登出
     * @param request
     * @return
     */
    @RequestMapping(value="/logout")
    public String logout(HttpServletRequest request){
        HttpSession session = request.getSession();
        session.removeAttribute(Variable.SESSION_SYSUSER);
        return "loginpage";

    }


/**
 * 登出
 * @param request
 * @return
 */
    @RequestMapping(value="/firstuser")
    public String firstuser(HttpServletRequest request){
        return "firstuser";
    }
}
