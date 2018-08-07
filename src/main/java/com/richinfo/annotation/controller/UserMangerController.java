package com.richinfo.annotation.controller;

import com.richinfo.annotation.po.Sysuser;
import com.richinfo.annotation.po.Task;
import com.richinfo.annotation.repository.SysuserRepository;
import com.richinfo.annotation.repository.TaskRepository;
import com.richinfo.util.DateTimeUtils;
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
import java.util.Optional;
import java.util.UUID;

@Controller
@RequestMapping("/admin")
public class UserMangerController{
    @Autowired
    private SysuserRepository sysuserRepository;

    @Autowired
    private TaskRepository taskRepository;

    private Logger logger = LoggerFactory.getLogger(UserMangerController.class);


    /**
     * 跳转至新增/修改用户
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value="/adduser")
    public String adduser(Model model,HttpServletRequest request){
        String id =request.getParameter("id");
        if(id!=null&&!id.isEmpty()){
            Optional<Sysuser> sysuserOption = sysuserRepository.findById(id);
            Sysuser sysuser = sysuserOption.get();
            if(sysuser!=null) {
                model.addAttribute("id", id);
                model.addAttribute("username", sysuser.getUsername());
                model.addAttribute("name", sysuser.getName());
                model.addAttribute("phone", sysuser.getPhone());
                model.addAttribute("role", sysuser.getRole());
            }
            model.addAttribute("title","更新用户");
        }else{
            model.addAttribute("title","新增用户");
        }
        return "adduser";
    }

    /**
     * 保存新增/修改用户数据
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value="/saveuser",method = RequestMethod.POST)
    public String saveuser(Model model,HttpServletRequest request) {

        String name =request.getParameter("name").trim();
        String phone =request.getParameter("phone").trim();
        String role =request.getParameter("role");
        String id =request.getParameter("id");
        Sysuser sysuser =null;
        if(id==null||id.isEmpty()){
            String username =request.getParameter("username").trim();
            String password = MD5Helper.MD5(request.getParameter("password").trim());
            Sysuser user = sysuserRepository.findByUsername(username);

            if (user != null) {
                model.addAttribute("msg", "添加失败，用户名重复！");
                model.addAttribute("username", username);
                model.addAttribute("password", request.getParameter("password"));
                model.addAttribute("name", name);
                model.addAttribute("phone", phone);
                model.addAttribute("role", role);
                return "adduser";
            }
            id = UUID.randomUUID().toString();
            sysuser = new Sysuser(id,username,password,name);
            sysuser.setCreatetime(DateTimeUtils.getDate());
            sysuser.setRole(role);
            sysuser.setPhone(phone);
            sysuser.setStatus(1);
        }else{
           Optional<Sysuser> sysuserOption = sysuserRepository.findById(id);
           sysuser = sysuserOption.get();
           if(sysuser==null){
               model.addAttribute("msg", "更新失败，未找到用户！");
               return "adduser";
           }else{
               sysuser.setName(name);
               sysuser.setRole(role);
               sysuser.setPhone(phone);
           }
        }
        sysuserRepository.save(sysuser);
        return "redirect:../admin/userlist";
    }

    /**
     * 修改用户状态（暂停/恢复）
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value="/changeuserstatus")
    public String changeuserstatus(Model model,HttpServletRequest request) {
        String id =request.getParameter("id");
        String status =request.getParameter("status");
        Optional<Sysuser> sysuserOption = sysuserRepository.findById(id);
        Sysuser sysuser = sysuserOption.get();
        if(sysuser!=null){
            if(status.equals("1")){
                sysuser.setStatus(1);
            }else{
                sysuser.setStatus(0);
            }
            sysuserRepository.save(sysuser);
        }
        return "redirect:../admin/userlist";
    }

    /**
     * 删除用户
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value="/deluser")
    public String deluser(Model model,HttpServletRequest request) {
        String id =request.getParameter("id");
        Optional<Sysuser> sysuserOption = sysuserRepository.findById(id);
        Sysuser sysuser = sysuserOption.get();
        if(sysuser!=null){
            sysuserRepository.delete(sysuser);
        }
        return "redirect:../admin/userlist";
    }

    /**
     * 重置用户密码
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value="/repwd")
    public String repwd(Model model,HttpServletRequest request) {
        String id =request.getParameter("id");
        Optional<Sysuser> sysuserOption = sysuserRepository.findById(id);
        Sysuser sysuser = sysuserOption.get();
        if(sysuser!=null){
            sysuser.setPassword(MD5Helper.MD5("888888"));
            sysuserRepository.save(sysuser);
        }
        return "redirect:../admin/userlist";
    }

    /**
     * 用户列表
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value="/userlist")
    public String userlist(Model model,HttpServletRequest request){
//        String pno = request.getParameter("pageNo");
//        String psize = request.getParameter("pageSize");
//
//        if(pno!=null&&!pno.isEmpty()){
//            pageNo = Integer.valueOf(pno);
//        }
//        if(pno!=null&&!pno.isEmpty()){
//            pageSize = Integer.valueOf(psize);
//        }
//        PageRequest page = buildPageRequest(pageNo,pageSize,"createtime",Sort.Direction.DESC);
//        Page<Sysuser> sysuserPage = sysuserRepository.findAll(page);
//        model.addAttribute("sysuserPage",sysuserPage);
        Iterable<Sysuser> userlist = sysuserRepository.findAll();
        for(Sysuser user:userlist){
            Task taskdoing = new Task();
            taskdoing.setSysuser(user);
            taskdoing.setStatus(Variable.TASK_STATUS_DOING);
            Example<Task> exampledoing = Example.of(taskdoing);
            Long docount = taskRepository.count(exampledoing);

            user.setDonum(docount.intValue());
            Task taskfinish = new Task();
            taskfinish.setSysuser(user);
            taskfinish.setStatus(Variable.TASK_STATUS_FINISH);
            Example<Task> examplefinish = Example.of(taskfinish);
            Long finishcount = taskRepository.count(examplefinish);
            user.setFinishnum(finishcount.intValue());
        }
        model.addAttribute("userlist",userlist);
        return "userlist";
    }

    /**
     * 跳转至修改密码
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value="/touppwd")
    public String touppwd(Model model,HttpServletRequest request){
        return "uppwd";
    }

    /**
     * 修改密码
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value="/uppwd")
    public String uppwd(Model model,HttpServletRequest request){
        HttpSession session = request.getSession();
        Sysuser sysuser = (Sysuser)session.getAttribute(Variable.SESSION_SYSUSER);
        if(sysuser!=null){
            String id =sysuser.getId();
            Optional<Sysuser> sysuserOption = sysuserRepository.findById(id);
            sysuser = sysuserOption.get();
            if(sysuser!=null){
                if(sysuser.getPassword().equals(MD5Helper.MD5(request.getParameter("oldpassword").trim()))){
                    sysuser.setPassword(MD5Helper.MD5(request.getParameter("password").trim()));
                    sysuserRepository.save(sysuser);
                    model.addAttribute("msg", "修改成功！");
                }else{
                    model.addAttribute("msg", "旧密码错误,修改失败！");
                }
            }else{
                model.addAttribute("msg","修改失败！");
            }

        }else{
            model.addAttribute("msg","修改失败！");
        }

        return "uppwd";
    }
}
