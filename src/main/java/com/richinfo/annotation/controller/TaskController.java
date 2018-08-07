package com.richinfo.annotation.controller;

import com.alibaba.fastjson.JSONObject;
import com.richinfo.annotation.po.*;
import com.richinfo.annotation.repository.*;
import com.richinfo.annotation.service.MonthtaskService;
import com.richinfo.annotation.service.TaskService;
import com.richinfo.util.DateTimeUtils;
import com.richinfo.util.Variable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/admin")
@Configuration
@PropertySource(value={"classpath:config/config.properties"}, ignoreResourceNotFound = true)
public class TaskController {

    private Logger logger = LoggerFactory.getLogger(TaskController.class);
    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private TaskbatchRepository taskbatchRepository;
    @Autowired
    private RecordRepository recordRepository;

    @Autowired
    private CarpicRepository carpicRepository;

    @Autowired
    private SysuserRepository sysuserRepository;

    @Autowired
    private MonthtaskService monthtaskService;

    @Autowired
    private CarsdataRepository carsdataRepository;

    @Autowired
    private PersonsDataRepository personsDataRepository;

    @Autowired
    private CarbrandRepository carbrandRepository;

    @Autowired
    private TaskbatchUserRepository taskbatchUserRepository;

    @Autowired
    private TaskService taskService;

    @Autowired
    private TextdataRepository textdataRepository;

    @Autowired
    private  SegmentRepository segmentRepository;

    @Value("${file.folder}")
    private String filefolder;
    @Value("${file.resources}")
    private String resources;

    /**
     * 任务列表页
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value="/tasklist")
    public String taskbatchlist(Model model,HttpServletRequest request){
        HttpSession session = request.getSession();
        Sysuser sysuser = (Sysuser)session.getAttribute(Variable.SESSION_SYSUSER);
        if(sysuser!=null) {
            String status = request.getParameter("status");
            List<Task> taskList;
            if(status==null||status.isEmpty()) {
                taskList = taskRepository.findBySysuser(sysuser);

            }else{
                taskList = taskRepository.findBySysuserAndStatus(sysuser,Integer.valueOf(status));
            }
            model.addAttribute("taskList", taskList);
        }
        return "tasklist";
    }

    @RequestMapping(value="/usertaskbatchlist")
    public String usertaskbatchlist(Model model,HttpServletRequest request){
        HttpSession session = request.getSession();
        Sysuser sysuser = (Sysuser)session.getAttribute(Variable.SESSION_SYSUSER);
        if(sysuser!=null) {
            List<TaskbatchUser> taskbatchUserList = taskbatchUserRepository.findByUser(sysuser);
            List<Taskbatch> taskbatchList = new ArrayList<>();
            for(TaskbatchUser taskbatchUser:taskbatchUserList){
                Taskbatch taskbatch = taskbatchRepository.findByBatchno(taskbatchUser.getTaskbatchno());
                if(taskbatch.getStatus()==Variable.TASKBATCH_STATUS_PUB){
                    taskbatchList.add(taskbatch);
                }
            }
            model.addAttribute("taskbatchList", taskbatchList);
        }
        return "usertaskbatchlist";
    }
    @RequestMapping(value="/userfinishtaskbatchlist")
    public String userfinishtaskbatchlist(Model model,HttpServletRequest request){
        HttpSession session = request.getSession();
        Sysuser sysuser = (Sysuser)session.getAttribute(Variable.SESSION_SYSUSER);
        if(sysuser!=null) {
            List<TaskbatchUser> taskbatchUserList = taskbatchUserRepository.findByUser(sysuser);
            List<Taskbatch> taskbatchList = new ArrayList<>();
            for(TaskbatchUser taskbatchUser:taskbatchUserList){
                Taskbatch taskbatch = taskbatchRepository.findByBatchno(taskbatchUser.getTaskbatchno());
                if(taskbatch.getStatus()==Variable.TASKBATCH_STATUS_CHECKED){
                    taskbatchList.add(taskbatch);
                }
            }
            model.addAttribute("taskbatchList", taskbatchList);
        }
        return "userfinishtaskbatchlist";
    }

    @RequestMapping(value="/dotask")
    public String dotask(Model model,HttpServletRequest request){
        String batchno = request.getParameter("batchno");
        HttpSession session = request.getSession();
        Sysuser sysuser = (Sysuser)session.getAttribute(Variable.SESSION_SYSUSER);
        TaskbatchUser taskbatchUser = taskbatchUserRepository.findByUserAndTaskbatchno(sysuser,batchno);
        if(taskbatchUser!=null){
            List<Task> taskList = taskRepository.findByBatchnoAndSysuserAndStatus(batchno,sysuser,Variable.TASK_STATUS_DOING);
            if(taskList!=null&&!taskList.isEmpty()){
                Task task = taskList.get(0);
                return "redirect:../admin/viewtask?taskno="+task.getTaskno();
            }
            String taskno =taskService.robTask(sysuser,batchno);
            if(!taskno.equals("erro")){
                return "redirect:../admin/viewtask?taskno="+taskno;
            }
        }
        return "redirect:../admin/usertaskbatchlist";
    }

    /**
     * 查看/做任务页面
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value="/viewtask")
    public String viewtask(Model model,HttpServletRequest request){
        String taskno = request.getParameter("taskno");
        if(taskno==null||taskno.isEmpty()){
            model.addAttribute("msg","任务编号为空！");
        }
        model.addAttribute("now",DateTimeUtils.getDate());
        HttpSession session = request.getSession();
        Sysuser sysuser = (Sysuser)session.getAttribute(Variable.SESSION_SYSUSER);
        if(sysuser!=null) {
            Task task =taskRepository.findByTaskno(taskno);
            if(task!=null) {
                if(sysuser.getRole().equals(Variable.USER_ROLE_MANAGER)||sysuser.getRole().equals(Variable.USER_ROLE_FIRSTCHECKER)||sysuser.getRole().equals(Variable.USER_ROLE_SECONDCHECKER)||task.getSysuser().getId().equals(sysuser.getId())) {
                    Taskbatch taskbatch = taskbatchRepository.findByBatchno(task.getBatchno());
                    model.addAttribute("task", task);
                    model.addAttribute("taskbatch",taskbatch);

                    if(taskbatch.getType().equals("2")){
                        Carpic carpic = carpicRepository.findByTaskno(taskno);
                        if(carpic!=null){
                            model.addAttribute("person",carpic.getPerson());
                            model.addAttribute("carno",carpic.getCarno());
                        }
                        String rfilefolder = filefolder+"/"+task.getFolder();
                        model.addAttribute("filefolder",rfilefolder);
                        return "viewcartask";
                    }else if(taskbatch.getType().equals("3")){
                        CarsData carsData =  carsdataRepository.findByTaskno(taskno);
                        if(carsData!=null){
                            String carsdatajson = JSONObject.toJSONString(carsData);
                            model.addAttribute("carsdatajson", carsdatajson);
                        }else{
                            if(task.getData()==null) {
                                carsData = new CarsData();
                                String carsdatajson = JSONObject.toJSONString(carsData);
                                model.addAttribute("carsdatajson",carsdatajson);
                            }else{
                                carsData =(CarsData)task.getData();
                                carsData.setId(null);
                                carsData.setTaskno(null);
                                carsData.setTaskbatchno(null);
                                String carsdatajson = JSONObject.toJSONString(carsData);
                                model.addAttribute("carsdatajson", carsdatajson);
                            }
                        }

                        return "viewcarinfotask_n";
                    }else if(taskbatch.getType().equals("4")){
                        Textdata textdata =  textdataRepository.findByTaskno(taskno);
                        if(textdata!=null){
                           String text = textdata.getText();
                            model.addAttribute("text",text);
                        }
//                        String rfilefolder = filefolder+"/"+task.getFolder();
//                        model.addAttribute("filefolder",rfilefolder);
                        return "viewtexttask";
                    }else if(taskbatch.getType().equals("5")){
                        PersonsData personsData =  personsDataRepository.findByTaskno(taskno);
                        if(personsData!=null){
                            String personsDatajson = JSONObject.toJSONString(personsData);
                            model.addAttribute("personsdatajson", personsDatajson);
                        }else{
                            personsData = new PersonsData();
                            String personsDatajson = JSONObject.toJSONString(personsData);
                            model.addAttribute("personsdatajson", personsDatajson);
                        }

                        return "viewpersoninfotask_n";
                    }else if(taskbatch.getType().equals("6")){//人包标注
                        Segment segment = segmentRepository.findByTaskno(taskno);
                        if(segment==null){
                            segment = new Segment();
                        }
                        String segmentJson = JSONObject.toJSONString(segment);
                        logger.info("segmentJson==="+segmentJson);
                        model.addAttribute("segmentJson", segmentJson);
                        return "viewpersonbagtask";
                    }else if(taskbatch.getType().equals("7")){//车牌信息
                        CarsData carsData =  carsdataRepository.findByTaskno(taskno);
                        if(carsData!=null){
                            String carsdatajson = JSONObject.toJSONString(carsData);
                            model.addAttribute("carsdatajson", carsdatajson);
                        }else{
                            if(task.getData()==null) {
                                carsData = new CarsData();
                                String carsdatajson = JSONObject.toJSONString(carsData);
                                model.addAttribute("carsdatajson",carsdatajson);
                            }else{
                                carsData =(CarsData)task.getData();
                                carsData.setId(null);
                                carsData.setTaskno(null);
                                carsData.setTaskbatchno(null);
                                String carsdatajson = JSONObject.toJSONString(carsData);
                                model.addAttribute("carsdatajson", carsdatajson);
                            }
                        }
                        return "viewcarnotask.html";
                    }else if(taskbatch.getType().equals("8")){
                        PersonsData personsData =  personsDataRepository.findByTaskno(taskno);
                        if(personsData!=null){
                            String personsDatajson = JSONObject.toJSONString(personsData);
                            model.addAttribute("personsdatajson", personsDatajson);
                        }else{
                            personsData = new PersonsData();
                            String personsDatajson = JSONObject.toJSONString(personsData);
                            model.addAttribute("personsdatajson", personsDatajson);
                        }

                        return "viewpersontask";
                    }
                    else{
                        String rfilefolder = filefolder+taskbatch.getFilepath()+"/"+task.getFolder();
                        model.addAttribute("filefolder",rfilefolder);
                    }

                    Record record = recordRepository.findByFilenameAndCasename(task.getFilename(),task.getFolder());
                    String recordjson="";
                    if(record==null){
                        record=new Record();
                        List<Person> personList = new ArrayList<>();
                        record.setPersonlist(personList);
                        record.setStarttime("");
                        record.setEndtime("");
                        record.setRecordname("");
                        record.setType("");
                        List<Event> eventList = new ArrayList<>();
                        record.setEvents(eventList);
                        record.setTag("");

                        Person person = new Person();
                        List<Family> familyList = new ArrayList<>();
                        person.setFamilys(familyList);
                        List<Relation> relationList = new ArrayList<>();
                        person.setRelations(relationList);
                        List<Contact> contactList = new ArrayList<>();
                        person.setContacts(contactList);
                        person.setNation("");
                        person.setSex("");
                        person.setPlace("");
                        person.setOrigin("");
                        Identify identify = new Identify();
                        identify.setType("");
                        identify.setNo("");
                        person.setIdentify(identify);

                        person.setEducation("");

                        person.setDomicile("");
                        person.setBirthday("");
                        person.setAlias("");
                        person.setWork("");
                        person.setName("");
                        person.setAge("");

                        record.setPerson(person);
                    }

                    recordjson = JSONObject.toJSONString(record);
                    //JSONObject jsonObject = JSONObject.parseObject(recordjson);

                    model.addAttribute("record",recordjson);
                }
            }
        }

        return "viewtask_s";
}

    /**
     * 保存笔录任务结果
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value="/saverecord",method = RequestMethod.POST)
    public String saverecord(Model model, HttpServletRequest request){
        String jsonstr = request.getParameter("record");
        String taskno = request.getParameter("taskno");
        Task task =taskRepository.findByTaskno(taskno);
        if(task==null){
            return "redirect:../admin/tasklist";
        }else if(task.getStatus()!=Variable.TASK_STATUS_DOING){
            return "redirect:../admin/dotask?batchno=" + task.getBatchno();
        }
        String filename = task.getFilename();
        String folder = task.getFolder();

        Record recordnew =JSONObject.parseObject(jsonstr, Record.class);

        Record record = recordRepository.findByFilenameAndCasename(filename,folder);
        String recordjson ="";
        if(record!=null){
            BeanUtils.copyProperties(recordnew,record,"id","filename","casename");

            recordRepository.save(record);
            recordjson = JSONObject.toJSONString(record);
        }else{
            recordnew.setId(UUID.randomUUID().toString());
            recordnew.setFilename(filename);
            recordnew.setCasename(folder);
            recordRepository.save(recordnew);
            recordjson = JSONObject.toJSONString(recordnew);
        }
        JSONObject jsonObject = JSONObject.parseObject(recordjson);

        return "redirect:../admin/viewtask?taskno="+taskno;
    }

    /**
     * 保存车牌信息任务结果
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value="/savecarrecord",method = RequestMethod.POST)
    public String savecarrecord(Model model,HttpServletRequest request) {
        String person = request.getParameter("person");
        String taskno = request.getParameter("taskno");
        String filename = request.getParameter("filename");
        String carno = request.getParameter("carno");
        Task task =taskRepository.findByTaskno(taskno);
        HttpSession session = request.getSession();
        Sysuser sysuser = (Sysuser)session.getAttribute(Variable.SESSION_SYSUSER);
        if(task==null){
            return "redirect:../admin/tasklist";
        }else if(sysuser.getRole().equals(Variable.USER_ROLE_NORMAL)&&task.getStatus()!=Variable.TASK_STATUS_DOING){
            return "redirect:../admin/dotask?batchno=" + task.getBatchno();
        }

        Carpic carpic = carpicRepository.findByTaskno(taskno);
        if(carpic==null){
            carpic = new Carpic();
            carpic.setId(UUID.randomUUID().toString());
            carpic.setTaskno(taskno);
        }
        if(carno!=null&&!carno.isEmpty()) {
            carpic.setCarno(carno);
        }else{
            carpic.setCarno(null);
        }
        //filename.substring(filename.lastIndexOf("/")+1,filename.length());
        carpic.setFilename(filename);
        carpic.setPerson(person);


        if(sysuser.getRole().equals(Variable.USER_ROLE_NORMAL)) {
            carpicRepository.save(carpic);
            if(task.getStatus()==Variable.TASK_STATUS_DOING) {
                taskService.submitTask(task);
                //完成任务数+1
                monthtaskService.addMonthtaskDonum(sysuser, 1l);
            }
            List<Task> taskList = taskRepository.findByBatchnoAndSysuserAndStatus(task.getBatchno(),sysuser,Variable.TASK_STATUS_DOING);
            String nextno="";
            if(taskList!=null&&!taskList.isEmpty()){
                nextno = taskList.get(0).getTaskno();
            }else{
                return "redirect:../admin/tasklist";
            }
            return "redirect:../admin/viewtask?taskno="+nextno;
        }else if(sysuser.getRole().equals(Variable.USER_ROLE_FIRSTCHECKER)){
            String firstresult = request.getParameter("firstresult");
            if(task.getStatus()==Variable.TASK_STATUS_SUBMIT) {
                if ("0".equals(firstresult)) {//初检不通过，修改数据
                    carpicRepository.save(carpic);
                }
                taskService.firstcheckTask(task, firstresult);

                monthtaskService.addMonthtaskDonum(sysuser, 1l);
            }
            List<Task> taskList = taskRepository.findByBatchnoAndSysuserAndStatus(task.getBatchno(),sysuser,Variable.TASK_STATUS_SUBMIT);
            String nextno="";
            if(taskList!=null&&!taskList.isEmpty()){
                nextno = taskList.get(0).getTaskno();
            }else{
                return "redirect:../admin/firstchecklist?status=1";
            }
            return "redirect:../admin/viewtask?taskno="+nextno;

        }else if(sysuser.getRole().equals(Variable.USER_ROLE_SECONDCHECKER)){
            String secondresult = request.getParameter("secondresult");
            if(task.getStatus()==Variable.TASK_STATUS_FIRSTCHECK) {
                if ("0".equals(secondresult)) {//复检不通过，修改数据
                    carpicRepository.save(carpic);
                }
                taskService.secondcheckTask(task, secondresult);

                monthtaskService.addMonthtaskDonum(sysuser, 1l);
            }
            List<Task> taskList = taskRepository.findByBatchnoAndSysuserAndStatus(task.getBatchno(),sysuser,Variable.TASK_STATUS_FIRSTCHECK);
            String nextno="";
            if(taskList!=null&&!taskList.isEmpty()){
                nextno = taskList.get(0).getTaskno();
            }else{
                return "redirect:../admin/secondchecklist?status=2";
            }
            return "redirect:../admin/viewtask?taskno="+nextno;
        }
        return "redirect:../admin/tasklist";
    }

    /**
     * 提交任务
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value="/submittask")
    public String submittask(Model model,HttpServletRequest request){
        String taskno = request.getParameter("taskno");
        Task task =taskRepository.findByTaskno(taskno);
        if(task!=null){
            if(task.getStatus()==Variable.TASK_STATUS_DOING){
                task.setStatus(Variable.TASK_STATUS_FINISH);
                task.setSubmittime(DateTimeUtils.getDate());
                task.setFinishtime(DateTimeUtils.getDate());
                taskRepository.save(task);
            }
        }
        return "redirect:../admin/tasklist";
    }

    /**
     * 初检列表
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value="/firstchecklist")
    public String firstchecklist(Model model,HttpServletRequest request){
        HttpSession session = request.getSession();
        Sysuser sysuser = (Sysuser)session.getAttribute(Variable.SESSION_SYSUSER);
        if(sysuser!=null) {
            String status = request.getParameter("status");
//            List<Task> taskList = taskRepository.findByFirstcheckerAndStatus(sysuser,Integer.valueOf(status));
//
//            for(Task task:taskList){
//                task.setStatus(Variable.TASK_STATUS_FIRSTCHECK);
//                task.setFirstresult(1);
//                task.setFirstchecktime(DateTimeUtils.getDate());
//                monthtaskService.addMonthtaskSuccessnum(task.getSysuser(),1l);
//                List<Sysuser> secondcheckerlist = sysuserRepository.findByRoleEqualsAndStatusEquals(Variable.USER_ROLE_SECONDCHECKER,Variable.USER_STATUS_NORMAL);
//                if(secondcheckerlist!=null&&!secondcheckerlist.isEmpty()){
//                    Random random = new Random();
//                    int i = random.nextInt(secondcheckerlist.size()-1);
//                    Sysuser secondchecker = secondcheckerlist.get(i);
//                    task.setSecondchecker(secondchecker);
//                    monthtaskService.addMonthtaskTasknum(secondchecker,1l);
//                 }
//                taskRepository.save(task);
//
//                monthtaskService.addMonthtaskDonum(task.getFirstchecker(),1l);
//
//            }

            PageRequest pageRequest = PageRequest.of(0,100);
            Page<Task> taskPage =  taskRepository.findByFirstcheckerAndStatus(sysuser,Integer.valueOf(status),pageRequest);
            model.addAttribute("taskList", taskPage.getContent());
            if(status.equals("1")) {
                model.addAttribute("title", "待检列表");
            }else{
                model.addAttribute("title", "已检列表");
            }
        }
        return "firstchecklist";
    }

    /**
     * 复检列表
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value="/secondchecklist")
    public String secondchecklist(Model model,HttpServletRequest request){
        HttpSession session = request.getSession();
        Sysuser sysuser = (Sysuser)session.getAttribute(Variable.SESSION_SYSUSER);
        if(sysuser!=null) {
            String status = request.getParameter("status");
//            List<Task> taskList = taskRepository.findBySecondcheckerAndStatus(sysuser,Integer.valueOf(status));
//            for(Task task:taskList){
//                task.setStatus(Variable.TASK_STATUS_FINISH);
//                task.setSecondresult(1);
//                task.setFinishtime(DateTimeUtils.getDate());
//                monthtaskService.addMonthtaskSuccessnum(task.getFirstchecker(),1l);
//                taskRepository.save(task);
//                monthtaskService.addMonthtaskDonum(task.getSecondchecker(),1l);
//            }
            PageRequest pageRequest = PageRequest.of(0,100);
            Page<Task> taskPage = taskRepository.findBySecondcheckerAndStatus(sysuser,Integer.valueOf(status),pageRequest);
            model.addAttribute("taskList", taskPage.getContent());
            if(status.equals("2")) {
                model.addAttribute("title", "待检列表");
            }else{
                model.addAttribute("title", "已检列表");
            }
        }
        return "secondchecklist";
    }
    //批量审核
    @RequestMapping(value="/batchok")
    public String batchok(Model model,HttpServletRequest request){
        HttpSession session = request.getSession();
        Sysuser sysuser = (Sysuser)session.getAttribute(Variable.SESSION_SYSUSER);
        String tasknos = request.getParameter("tasknos");
        String[] tasknoarray = tasknos.split(",");
        if(tasknoarray.length>=1) {
            taskService.allok(tasknoarray);
            monthtaskService.addMonthtaskDonum(sysuser, (long)tasknoarray.length);
        }
        return "redirect:../admin/secondchecklist?status=2";
    }

    /**
     * 车辆信息录入界面
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value="/viewcarinfotask")
    public String viewcarinfotask(Model model,HttpServletRequest request){
        HttpSession session = request.getSession();
        Sysuser sysuser = (Sysuser)session.getAttribute(Variable.SESSION_SYSUSER);
        return "viewcarinfotask";
    }

    /**
     * 保存车辆信息任务结果
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value="/savecarinfo",method = RequestMethod.POST)
    public String savecarinfo(Model model, HttpServletRequest request) {
        String jsonstr = request.getParameter("carsdata");
        String taskno = request.getParameter("taskno");
        Task task = taskRepository.findByTaskno(taskno);
        HttpSession session = request.getSession();
        Sysuser sysuser = (Sysuser) session.getAttribute(Variable.SESSION_SYSUSER);
        if(task==null){
            return "redirect:../admin/tasklist";
        }else if(sysuser.getRole().equals(Variable.USER_ROLE_NORMAL)&&task.getStatus()!=Variable.TASK_STATUS_DOING){
            return "redirect:../admin/dotask?batchno=" + task.getBatchno();
        }
        String filename = task.getFilename();
        String folder = task.getFolder();

        logger.info(jsonstr);
        CarsData carsDatanew = JSONObject.parseObject(jsonstr, CarsData.class);

        CarsData carsData = carsdataRepository.findByTaskno(taskno);
        String recordjson = "";
        if (carsData != null) {
            BeanUtils.copyProperties(carsDatanew, carsData, "id", "filename", "taskno");
            //recordjson = JSONObject.toJSONString(record);
        } else {
            carsDatanew.setId(UUID.randomUUID().toString());
            carsDatanew.setFilename(filename);
            carsDatanew.setTaskno(taskno);
            carsDatanew.setTaskbatchno(task.getBatchno());
            //recordjson = JSONObject.toJSONString(carsDatanew);
        }
        // JSONObject jsonObject = JSONObject.parseObject(recordjson);
        if (sysuser.getRole().equals(Variable.USER_ROLE_NORMAL)) {
            if (carsData != null) {
                carsdataRepository.save(carsData);
            }else{
                carsdataRepository.save(carsDatanew);
            }
            if(task.getStatus()==Variable.TASK_STATUS_DOING){
                taskService.submitTask(task);
                //完成任务数+1
                monthtaskService.addMonthtaskDonum(sysuser, 1l);
            }
            return "redirect:../admin/dotask?batchno=" + task.getBatchno();

        } else if (sysuser.getRole().equals(Variable.USER_ROLE_FIRSTCHECKER)) {
            String firstresult = request.getParameter("firstresult");
            if(task.getStatus()==Variable.TASK_STATUS_SUBMIT) {
                if("-1".equals(firstresult)){
                    carsdataRepository.delete(carsData);
                } else {//修改数据
                    carsdataRepository.save(carsData);
                }
                taskService.firstcheckTask(task,firstresult);
                if(!"-1".equals(firstresult)) {
                    monthtaskService.addMonthtaskDonum(sysuser, 1l);
                }
            }

            List<Task> taskList = taskRepository.findByBatchnoAndSysuserAndStatus(task.getBatchno(), sysuser, Variable.TASK_STATUS_SUBMIT);
            String nextno = "";
            if (taskList != null && !taskList.isEmpty()) {
                nextno = taskList.get(0).getTaskno();
            } else {
                return "redirect:../admin/firstchecklist?status=1";
            }
            return "redirect:../admin/viewtask?taskno=" + nextno;

        } else if (sysuser.getRole().equals(Variable.USER_ROLE_SECONDCHECKER)) {
            String secondresult = request.getParameter("secondresult");
            if(task.getStatus()==Variable.TASK_STATUS_FIRSTCHECK) {
                if ("0".equals(secondresult)) {//复检不通过，修改数据
                    carsdataRepository.save(carsData);
                }
                taskService.secondcheckTask(task, secondresult);
                monthtaskService.addMonthtaskDonum(sysuser, 1l);
            }

            List<Task> taskList = taskRepository.findByBatchnoAndSysuserAndStatus(task.getBatchno(), sysuser, Variable.TASK_STATUS_FIRSTCHECK);
            String nextno = "";
            if (taskList != null && !taskList.isEmpty()) {
                nextno = taskList.get(0).getTaskno();
            } else {
                return "redirect:../admin/secondchecklist?status=2";
            }
            return "redirect:../admin/viewtask?taskno=" + nextno;
        }
        return "redirect:../admin/tasklist";
    }

    /**
     * 文字任务录入界面
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value="/viewtexttask")
    public String viewtexttask(Model model,HttpServletRequest request){
        HttpSession session = request.getSession();
        Sysuser sysuser = (Sysuser)session.getAttribute(Variable.SESSION_SYSUSER);
        return "viewtexttask";
    }

    /**
     * 保存文字任务结果
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value="/savetext",method = RequestMethod.POST)
    public String savetext(Model model, HttpServletRequest request) {
        String taskno = request.getParameter("taskno");
        String text = request.getParameter("text");
        HttpSession session = request.getSession();
        Sysuser sysuser = (Sysuser) session.getAttribute(Variable.SESSION_SYSUSER);
        Task task = taskRepository.findByTaskno(taskno);
        if(task==null){
            return "redirect:../admin/tasklist";
        }else if(sysuser.getRole().equals(Variable.USER_ROLE_NORMAL)&&task.getStatus()!=Variable.TASK_STATUS_DOING){
            return "redirect:../admin/dotask?batchno=" + task.getBatchno();
        }
        String filename = task.getFilename();

        Textdata textdata = textdataRepository.findByTaskno(taskno);
        if(textdata==null){
            textdata = new Textdata();
            textdata.setTaskno(taskno);
            textdata.setTaskbatchno(task.getBatchno());
            textdata.setFilename(filename);
        }
        textdata.setText(text);
        if(sysuser.getRole().equals(Variable.USER_ROLE_NORMAL)){
            textdataRepository.save(textdata);
            if(task.getStatus()==Variable.TASK_STATUS_DOING) {
                taskService.submitTask(task);
                monthtaskService.addMonthtaskDonum(sysuser, 1l);
            }
            return "redirect:../admin/dotask?batchno=" + task.getBatchno();
        }else if(sysuser.getRole().equals(Variable.USER_ROLE_FIRSTCHECKER)){
            String firstresult = request.getParameter("firstresult");
            if(task.getStatus()==Variable.TASK_STATUS_SUBMIT) {
                if ("0".equals(firstresult)) {//初检不通过，修改数据
                    textdataRepository.save(textdata);
                }
                taskService.firstcheckTask(task,firstresult);
                monthtaskService.addMonthtaskDonum(sysuser, 1l);
                List<Task> taskList = taskRepository.findByBatchnoAndSysuserAndStatus(task.getBatchno(), sysuser, Variable.TASK_STATUS_SUBMIT);
                String nextno = "";
                if (taskList != null && !taskList.isEmpty()) {
                    nextno = taskList.get(0).getTaskno();
                } else {
                    return "redirect:../admin/firstchecklist?status=1";
                }
                return "redirect:../admin/viewtask?taskno=" + nextno;
            }
        }else if (sysuser.getRole().equals(Variable.USER_ROLE_SECONDCHECKER)) {
            String secondresult = request.getParameter("secondresult");
            if(task.getStatus()==Variable.TASK_STATUS_FIRSTCHECK) {
                if ("0".equals(secondresult)) {//复检不通过，修改数据
                    textdataRepository.save(textdata);
                }
                taskService.secondcheckTask(task, secondresult);
                monthtaskService.addMonthtaskDonum(sysuser, 1l);
            }

            List<Task> taskList = taskRepository.findByBatchnoAndSysuserAndStatus(task.getBatchno(), sysuser, Variable.TASK_STATUS_FIRSTCHECK);
            String nextno = "";
            if (taskList != null && !taskList.isEmpty()) {
                nextno = taskList.get(0).getTaskno();
            } else {
                return "redirect:../admin/secondchecklist?status=2";
            }
            return "redirect:../admin/viewtask?taskno=" + nextno;

        }
        return "redirect:../admin/tasklist";

    }

    /**
     * 文本录入结果页
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = { "/overtask" }, produces="application/json;charset=UTF-8")
    @ResponseBody
    public String overtask(HttpServletRequest request,HttpServletResponse response) {
        response.addHeader("Access-Control-Allow-Origin", "*");
        String taskno = request.getParameter("taskno");
        taskService.overTask(taskno);
        return "{\"result\":\"ok\"}";
    }


    /**
     * 保存行人信息任务结果
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value="/savepersoninfo",method = RequestMethod.POST)
    public String savepersoninfo(Model model, HttpServletRequest request) {
        String jsonstr = request.getParameter("personsdata");
        String taskno = request.getParameter("taskno");
        Task task = taskRepository.findByTaskno(taskno);
        HttpSession session = request.getSession();
        Sysuser sysuser = (Sysuser) session.getAttribute(Variable.SESSION_SYSUSER);
        if(task==null){
            return "redirect:../admin/tasklist";
        }else if(sysuser.getRole().equals(Variable.USER_ROLE_NORMAL)&&task.getStatus()!=Variable.TASK_STATUS_DOING){
            return "redirect:../admin/dotask?batchno=" + task.getBatchno();
        }
        String filename = task.getFilename();
        String folder = task.getFolder();

        logger.info(jsonstr);
        PersonsData personsDatanew = JSONObject.parseObject(jsonstr, PersonsData.class);

        PersonsData personsData = personsDataRepository.findByTaskno(taskno);
        String recordjson = "";
        if (personsData != null) {
            BeanUtils.copyProperties(personsDatanew, personsData, "id", "filename", "taskno");
            //recordjson = JSONObject.toJSONString(record);
        } else {
            personsDatanew.setId(UUID.randomUUID().toString());
            personsDatanew.setFilename(filename);
            personsDatanew.setTaskno(taskno);
            personsDatanew.setTaskbatchno(task.getBatchno());
            //recordjson = JSONObject.toJSONString(carsDatanew);
        }
        // JSONObject jsonObject = JSONObject.parseObject(recordjson);
        if (sysuser.getRole().equals(Variable.USER_ROLE_NORMAL)) {
            if (personsData != null) {
                personsDataRepository.save(personsData);
            }else{
                personsDataRepository.save(personsDatanew);
            }
            if(task.getStatus()==Variable.TASK_STATUS_DOING){
                taskService.submitTask(task);
                //完成任务数+1
                monthtaskService.addMonthtaskDonum(sysuser, 1l);
            }
            return "redirect:../admin/dotask?batchno=" + task.getBatchno();

        } else if (sysuser.getRole().equals(Variable.USER_ROLE_FIRSTCHECKER)) {
            String firstresult = request.getParameter("firstresult");
            if(task.getStatus()==Variable.TASK_STATUS_SUBMIT) {

                if ("-1".equals(firstresult)) {//初检不通过，修改数据
                    personsDataRepository.delete(personsData);
                }else{//重做
                    personsDataRepository.save(personsData);
                }
                taskService.firstcheckTask(task,firstresult);
                if(!"-1".equals(firstresult)) {
                    monthtaskService.addMonthtaskDonum(sysuser, 1l);
                }
            }

            List<Task> taskList = taskRepository.findByBatchnoAndSysuserAndStatus(task.getBatchno(), sysuser, Variable.TASK_STATUS_SUBMIT);
            String nextno = "";
            if (taskList != null && !taskList.isEmpty()) {
                nextno = taskList.get(0).getTaskno();
            } else {
                return "redirect:../admin/firstchecklist?status=1";
            }
            return "redirect:../admin/viewtask?taskno=" + nextno;

        } else if (sysuser.getRole().equals(Variable.USER_ROLE_SECONDCHECKER)) {
            String secondresult = request.getParameter("secondresult");
            if(task.getStatus()==Variable.TASK_STATUS_FIRSTCHECK) {
                if ("0".equals(secondresult)) {//复检不通过，修改数据
                    personsDataRepository.save(personsData);
                }
                taskService.secondcheckTask(task, secondresult);
                monthtaskService.addMonthtaskDonum(sysuser, 1l);
            }

            List<Task> taskList = taskRepository.findByBatchnoAndSysuserAndStatus(task.getBatchno(), sysuser, Variable.TASK_STATUS_FIRSTCHECK);
            String nextno = "";
            if (taskList != null && !taskList.isEmpty()) {
                nextno = taskList.get(0).getTaskno();
            } else {
                return "redirect:../admin/secondchecklist?status=2";
            }
            return "redirect:../admin/viewtask?taskno=" + nextno;
        }
        return "redirect:../admin/tasklist";
    }


    /**
     * 保存人包任务结果
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value="/savepersonbag",method = RequestMethod.POST)
    public String savepersonbag(Model model, HttpServletRequest request) {
        String jsonstr = request.getParameter("personbagdata");
        String taskno = request.getParameter("taskno");
        Task task = taskRepository.findByTaskno(taskno);
        HttpSession session = request.getSession();
        Sysuser sysuser = (Sysuser) session.getAttribute(Variable.SESSION_SYSUSER);
        if(task==null){
            return "redirect:../admin/tasklist";
        }else if(sysuser.getRole().equals(Variable.USER_ROLE_NORMAL)&&task.getStatus()!=Variable.TASK_STATUS_DOING){
            return "redirect:../admin/dotask?batchno=" + task.getBatchno();
        }
        String filename = task.getFilename();
        String folder = task.getFolder();

        logger.info(jsonstr);
        Segment segmentnew = JSONObject.parseObject(jsonstr, Segment.class);

        Segment segment = segmentRepository.findByTaskno(taskno);
        if (segment != null) {
            BeanUtils.copyProperties(segmentnew, segment, "id", "filename", "taskno","packagename","taskbatchno");
        } else {
            segmentnew.setId(UUID.randomUUID().toString());
            segmentnew.setFilename(filename);
            segmentnew.setTaskno(taskno);
            segmentnew.setPackagename(folder);
            segmentnew.setTaskbatchno(task.getBatchno());
        }
        if (sysuser.getRole().equals(Variable.USER_ROLE_NORMAL)) {
            if (segment != null) {
                segmentRepository.save(segment);
            }else{
                segmentRepository.save(segmentnew);
            }
            if(task.getStatus()==Variable.TASK_STATUS_DOING){
                taskService.submitTask(task);
                //完成任务数+1
                monthtaskService.addMonthtaskDonum(sysuser, 1l);
            }
            return "redirect:../admin/dotask?batchno=" + task.getBatchno();

        } else if (sysuser.getRole().equals(Variable.USER_ROLE_FIRSTCHECKER)) {
            String firstresult = request.getParameter("firstresult");
            if(task.getStatus()==Variable.TASK_STATUS_SUBMIT) {
                if("-1".equals(firstresult)){
                    segmentRepository.delete(segment);
                } else {//修改数据
                    segmentRepository.save(segment);
                }
                taskService.firstcheckTask(task,firstresult);
                if(!"-1".equals(firstresult)) {
                    monthtaskService.addMonthtaskDonum(sysuser, 1l);
                }
            }

            List<Task> taskList = taskRepository.findByBatchnoAndSysuserAndStatus(task.getBatchno(), sysuser, Variable.TASK_STATUS_SUBMIT);
            String nextno = "";
            if (taskList != null && !taskList.isEmpty()) {
                nextno = taskList.get(0).getTaskno();
            } else {
                return "redirect:../admin/firstchecklist?status=1";
            }
            return "redirect:../admin/viewtask?taskno=" + nextno;

        } else if (sysuser.getRole().equals(Variable.USER_ROLE_SECONDCHECKER)) {
            String secondresult = request.getParameter("secondresult");
            if(task.getStatus()==Variable.TASK_STATUS_FIRSTCHECK) {
                if ("0".equals(secondresult)) {//复检不通过，修改数据
                    segmentRepository.save(segment);
                }
                taskService.secondcheckTask(task, secondresult);
                monthtaskService.addMonthtaskDonum(sysuser, 1l);
            }

            List<Task> taskList = taskRepository.findByBatchnoAndSysuserAndStatus(task.getBatchno(), sysuser, Variable.TASK_STATUS_FIRSTCHECK);
            String nextno = "";
            if (taskList != null && !taskList.isEmpty()) {
                nextno = taskList.get(0).getTaskno();
            } else {
                return "redirect:../admin/secondchecklist?status=2";
            }
            return "redirect:../admin/viewtask?taskno=" + nextno;
        }
        return "redirect:../admin/tasklist";
    }


    /**
     * 保存车牌信息任务结果
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value="/savecarnoinfo",method = RequestMethod.POST)
    public String savecarnoinfo(Model model, HttpServletRequest request) {
        String jsonstr = request.getParameter("carsdata");
        String taskno = request.getParameter("taskno");
        Task task = taskRepository.findByTaskno(taskno);
        HttpSession session = request.getSession();
        Sysuser sysuser = (Sysuser) session.getAttribute(Variable.SESSION_SYSUSER);
        if(task==null){
            return "redirect:../admin/tasklist";
        }else if(sysuser.getRole().equals(Variable.USER_ROLE_NORMAL)&&task.getStatus()!=Variable.TASK_STATUS_DOING){
            return "redirect:../admin/dotask?batchno=" + task.getBatchno();
        }
        String filename = task.getFilename();
        String folder = task.getFolder();

        logger.info(jsonstr);
        CarsData carsDatanew = JSONObject.parseObject(jsonstr, CarsData.class);

        CarsData carsData = carsdataRepository.findByTaskno(taskno);
        String recordjson = "";
        if (carsData != null) {
            BeanUtils.copyProperties(carsDatanew, carsData, "id", "filename", "taskno");
            //recordjson = JSONObject.toJSONString(record);
        } else {
            carsDatanew.setId(UUID.randomUUID().toString());
            carsDatanew.setFilename(filename);
            carsDatanew.setTaskno(taskno);
            carsDatanew.setTaskbatchno(task.getBatchno());
            //recordjson = JSONObject.toJSONString(carsDatanew);
        }
        // JSONObject jsonObject = JSONObject.parseObject(recordjson);
        if (sysuser.getRole().equals(Variable.USER_ROLE_NORMAL)) {
            if (carsData != null) {
                carsdataRepository.save(carsData);
            }else{
                carsdataRepository.save(carsDatanew);
            }
            if(task.getStatus()==Variable.TASK_STATUS_DOING){
                taskService.submitTask(task);
                //完成任务数+1
                monthtaskService.addMonthtaskDonum(sysuser, 1l);
            }
            return "redirect:../admin/dotask?batchno=" + task.getBatchno();

        } else if (sysuser.getRole().equals(Variable.USER_ROLE_FIRSTCHECKER)) {
            String firstresult = request.getParameter("firstresult");
            if(task.getStatus()==Variable.TASK_STATUS_SUBMIT) {
                if("-1".equals(firstresult)){
                    carsdataRepository.delete(carsData);
                } else {//修改数据
                    carsdataRepository.save(carsData);
                }
                taskService.firstcheckTask(task,firstresult);
                if(!"-1".equals(firstresult)) {
                    monthtaskService.addMonthtaskDonum(sysuser, 1l);
                }
            }

            List<Task> taskList = taskRepository.findByBatchnoAndSysuserAndStatus(task.getBatchno(), sysuser, Variable.TASK_STATUS_SUBMIT);
            String nextno = "";
            if (taskList != null && !taskList.isEmpty()) {
                nextno = taskList.get(0).getTaskno();
            } else {
                return "redirect:../admin/firstchecklist?status=1";
            }
            return "redirect:../admin/viewtask?taskno=" + nextno;

        } else if (sysuser.getRole().equals(Variable.USER_ROLE_SECONDCHECKER)) {
            String secondresult = request.getParameter("secondresult");
            if(task.getStatus()==Variable.TASK_STATUS_FIRSTCHECK) {
                if ("0".equals(secondresult)) {//复检不通过，修改数据
                    carsdataRepository.save(carsData);
                }
                taskService.secondcheckTask(task, secondresult);
                monthtaskService.addMonthtaskDonum(sysuser, 1l);
            }

            List<Task> taskList = taskRepository.findByBatchnoAndSysuserAndStatus(task.getBatchno(), sysuser, Variable.TASK_STATUS_FIRSTCHECK);
            String nextno = "";
            if (taskList != null && !taskList.isEmpty()) {
                nextno = taskList.get(0).getTaskno();
            } else {
                return "redirect:../admin/secondchecklist?status=2";
            }
            return "redirect:../admin/viewtask?taskno=" + nextno;
        }
        return "redirect:../admin/tasklist";
    }

}
