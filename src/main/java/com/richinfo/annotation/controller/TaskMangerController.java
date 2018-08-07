package com.richinfo.annotation.controller;

import com.alibaba.fastjson.JSONObject;
import com.richinfo.annotation.bo.*;
import com.richinfo.annotation.bo.Car;
import com.richinfo.annotation.bo.Frame;
import com.richinfo.annotation.po.*;
import com.richinfo.annotation.repository.*;
import com.richinfo.annotation.service.DaytaskService;
import com.richinfo.annotation.service.MonthtaskService;
import com.richinfo.annotation.service.TaskService;
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
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Controller
@RequestMapping("/admin")
@Configuration
@PropertySource(value={"classpath:config/config.properties"}, ignoreResourceNotFound = true)
public class TaskMangerController {

    @Autowired
    private TaskbatchRepository taskbatchRepository;
    @Autowired
    private SysuserRepository sysuerRepository;
    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private CarpicRepository carpicRepository;

    @Autowired
    private RecordRepository recordRepository;
    private Logger logger = LoggerFactory.getLogger(TaskMangerController.class);

    @Value("${file.folder}")
    private String filefolder;
    @Value("${file.resources}")
    private String resources;

    @Autowired
    private MonthtaskService monthtaskService;

    @Autowired
    private CarsdataRepository carsdataRepository;

    @Autowired
    private TaskbatchUserRepository taskbatchUserRepository;

    @Autowired
    private TextdataRepository textdataRepository;

    @Autowired
    private TaskqueueRepository taskqueueRepository;

    @Autowired
    private DaytaskService daytaskService;

    @Autowired
    private PersonsDataRepository personsDataRepository;

    @Autowired
    private SegmentRepository segmentRepository;

    @Autowired
    private TaskService taskService;
    /**
     * 跳转至新增/编辑任务批次
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value="/toaddtaskbatch")
    public String toaddtaskbatch(Model model,HttpServletRequest request){
        String batchno = request.getParameter("batchno");
        if(batchno!=null&&!batchno.isEmpty()) {//编辑
            Taskbatch taskbatch = taskbatchRepository.findByBatchno(batchno);
            if(taskbatch.getStatus()!=0){
                model.addAttribute("msg","修改失败，任务批次为不可修改状态：status="+taskbatch.getStatus());
            }else {
                model.addAttribute("header", "编辑任务批次");
                model.addAttribute("batchno", batchno);
                model.addAttribute("title", taskbatch.getTitle());
                model.addAttribute("filepath", taskbatch.getFilepath());
                model.addAttribute("descr", taskbatch.getDescr());
                model.addAttribute("extend", taskbatch.getExtend());
                model.addAttribute("type", taskbatch.getType());
                model.addAttribute("filecounts",taskbatch.getFilecounts());
            }
        }else{
            model.addAttribute("header","新增任务批次");
        }
        return "addtaskbatch";
    }

    /**
     * 跳转至更新任务批次页面
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value="/torepubtaskbatch")
    public String torepubtaskbatch(Model model,HttpServletRequest request){
        String batchno = request.getParameter("batchno");
        model.addAttribute("prebatchno",batchno);
        return "repubtaskbatch";
    }

    /**
     * 更新任务批次
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value="/repubtaskbatch",method = RequestMethod.POST)
    public String repubtaskbatch(Model model,HttpServletRequest request){
        String title = request.getParameter("title");
        String prebatchno = request.getParameter("prebatchno");
        String descr = request.getParameter("descr");
//        String counts = request.getParameter("count");
        String type = request.getParameter("type");
//        int count= Integer.valueOf(counts);
//        int m=0;
        if(prebatchno!=null&&!prebatchno.isEmpty()) {
            String id = UUID.randomUUID().toString();
            String batchno = "b" + DateTimeUtils.getDate("yyMMddHHmmssSSS") + RandomUtil.createRandomNum(4);
            Taskbatch taskbatch = new Taskbatch();
            Taskbatch prebatch = taskbatchRepository.findByBatchno(prebatchno);
//            List<CarsData> carsDatas = carsdataRepository.findByTaskbatchno(prebatchno);
            List<Task> taskList = taskRepository.findByBatchnoAndStatus(prebatchno,Variable.TASK_STATUS_FINISH);
            List<String> tasknolist = new ArrayList<>();
            for(Task task:taskList){
                tasknolist.add(task.getTaskno());
            }
            List<CarsData> carsDatas = carsdataRepository.findByTasknoIn(tasknolist);
            List<String[]> filelist = new ArrayList<>();
            if (carsDatas != null&&!carsDatas.isEmpty()) {
                logger.info("carsDatas.size==="+carsDatas.size());
                for(CarsData carsData:carsDatas) {
//                    List<Cardata> cars = carsData.getCars();
//                    if (cars != null && !cars.isEmpty()) {
                        String[] files = new String[2];
                        files[0] = prebatch.getFilepath();
                        files[1] = carsData.getFilename();
                        filelist.add(files);
                        //m++;
//                        if(m>=count){
//                            break;
//                        }
//                    }
                }
                logger.info("filelist.size()==="+filelist.size());
                taskbatch.setFilecounts(filelist.size());
                taskbatch.setFilenames(filelist);
            }

            taskbatch.setType(type);
            taskbatch.setFilepath(prebatch.getFilepath());
            taskbatch.setExtend(prebatch.getExtend());
            taskbatch.setId(id);
            taskbatch.setTitle(title);
            taskbatch.setBatchno(batchno);
            taskbatch.setCreatetime(DateTimeUtils.getDate());
            taskbatch.setDescr(descr);
            taskbatch.setStatus(Variable.TASKBATCH_STATUS_WAITPUB);
            taskbatch.setPrebatchno(prebatchno);
            taskbatchRepository.save(taskbatch);
        }
        return "redirect:../admin/taskbatchlist";
    }

    /**
     * 保存新增/编辑任务批次结果
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value="/addtaskbatch",method = RequestMethod.POST)

    public String addtaskbatch(Model model,HttpServletRequest request){
        String title = request.getParameter("title");
        String filepath = request.getParameter("filepath");
        String descr = request.getParameter("descr");
        String type = request.getParameter("type");
        String extend = request.getParameter("extend");
        String counts = request.getParameter("count");
        String prebatchno = request.getParameter("prebatchno");
//        String id = request.getParameter("id");
        String batchno = request.getParameter("batchno");
        if(batchno==null||batchno.isEmpty()) {//新增
            String id = UUID.randomUUID().toString();
            batchno = "b" + DateTimeUtils.getDate("yyMMddHHmmssSSS") + RandomUtil.createRandomNum(4);
            Taskbatch taskbatch = new Taskbatch();

            if (filepath.startsWith("http")) {
                int count = Integer.valueOf(counts);
                List<String[]> filelist = new ArrayList<>();
                for (int i = 1; i <= count; i++) {
                    String filefull = "";
                    filefull = extend.replace("{no}", RandomUtil.zeroStr(6, i));
                    if("7".equals(type)||"8".equals(type)){
                        filefull = extend.replace("{no}", RandomUtil.zeroStr(5, i-1));
                    }
                    String[] files = new String[2];
                    files[0] = filepath;
                    files[1] = filefull;
                    filelist.add(files);
                }
                taskbatch.setFilecounts(filelist.size());
                taskbatch.setFilenames(filelist);
            }else if(filepath.endsWith(".txt")){
                List<String[]> filelist = FileUtils.getPicUrlByFile(resources + filefolder +filepath);
                taskbatch.setFilecounts(filelist.size());
                taskbatch.setFilenames(filelist);
            }
            else {

                List<String[]> filelist = FileUtils.getFiles(resources + filefolder + filepath, extend);
                taskbatch.setFilecounts(filelist.size());
                taskbatch.setFilenames(filelist);
            }

            taskbatch.setFilepath(filepath);
            taskbatch.setExtend(extend);
            taskbatch.setId(id);
            taskbatch.setTitle(title);
            taskbatch.setBatchno(batchno);
            taskbatch.setCreatetime(DateTimeUtils.getDate());
            taskbatch.setDescr(descr);

            taskbatch.setStatus(Variable.TASKBATCH_STATUS_WAITPUB);
            taskbatch.setType(type);

            taskbatchRepository.save(taskbatch);
        }else{//修改
            //Optional<Taskbatch> optional = taskbatchRepository.findById(id);
            Taskbatch taskbatch = taskbatchRepository.findByBatchno(batchno);
            if(taskbatch!=null){
                if(taskbatch.getStatus()!=Variable.TASKBATCH_STATUS_WAITPUB){
                    model.addAttribute("msg","修改失败，任务批次为不可修改状态：status="+taskbatch.getStatus());
                }else {
                    taskbatch.setTitle(title);
                    taskbatch.setDescr(descr);
                    taskbatch.setExtend(extend);
                    List<String[]> filelist = FileUtils.getFiles(resources+filefolder+filepath,extend);
                    taskbatch.setFilecounts(filelist.size());
                    taskbatch.setFilepath(filepath);
                    taskbatch.setStatus(Variable.TASKBATCH_STATUS_WAITPUB);
                    taskbatch.setType(type);
                    taskbatch.setFilenames(filelist);
                    taskbatchRepository.save(taskbatch);
                }
            }else{
                model.addAttribute("msg","修改失败，未找到batchno："+batchno+"，的数据！");
            }
        }
        return "redirect:../admin/taskbatchlist";
    }

    /**
     * 任务批次列表
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value="/taskbatchlist")
    public String taskbatchlist(Model model,HttpServletRequest request){
        Iterable<Taskbatch> taskbatchList = taskbatchRepository.findAll();
        model.addAttribute("taskbatchList",taskbatchList);
        for(Taskbatch bt:taskbatchList) {
            Task task = new Task();
            task.setBatchno(bt.getBatchno());
            task.setStatus(Variable.TASK_STATUS_FINISH);
            Example<Task> example = Example.of(task);
            long finishcount = taskRepository.count(example);
            //logger.info("finishcount==="+finishcount);
            bt.setFinishcounts(finishcount);
        }
        return "taskbatchlist";
    }

    /**
     * 删除任务批次
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value="/deltaskbatch")
    public String deltaskbatch(Model model,HttpServletRequest request) {
        String batchno =request.getParameter("batchno");
        Taskbatch taskbatch = taskbatchRepository.findByBatchno(batchno);
        if(taskbatch!=null){
            taskbatchRepository.delete(taskbatch);
        }
        return "redirect:../admin/taskbatchlist";
    }

    /**
     * 跳转至发布任务
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value="/topubtaskbatch")
    public String topubtaskbatch(Model model,HttpServletRequest request){
        String batchno = request.getParameter("batchno");
        if(batchno!=null&&!batchno.isEmpty()) {
            Taskbatch taskbatch = taskbatchRepository.findByBatchno(batchno);
            model.addAttribute("taskbatch",taskbatch);
            List<String> rolelist = new ArrayList<>();
            rolelist.add(Variable.USER_ROLE_NORMAL);
            List<Sysuser> sysuserList = sysuerRepository.findByRoleInAndStatus(rolelist,Variable.USER_STATUS_NORMAL);
            model.addAttribute("sysuserList",sysuserList);
        }else{
            model.addAttribute("msg","任务批次为空");
        }
        return "pubtaskbatch";
    }

    /**
     * 发布任务
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value="/pubtaskbatch",method = RequestMethod.POST)
    public String pubtaskbatch(Model model,HttpServletRequest request){
        String batchno = request.getParameter("batchno");
        String sysusers = request.getParameter("sysusers");
        if(batchno!=null&&!batchno.isEmpty()) {
            if(sysusers!=null&&!sysusers.isEmpty()) {
                String[] sysuserids = sysusers.split(",");
                List<Sysuser> sysuserList = new ArrayList<>();
                for(String id:sysuserids){
                    Optional<Sysuser> optional =  sysuerRepository.findById(id);
                    Sysuser sysuser = optional.get();
                    if(sysuser!=null){
                        sysuserList.add(sysuser);
                    }
                }
                Taskbatch taskbatch = taskbatchRepository.findByBatchno(batchno);
                if (taskbatch != null) {
                    if(taskbatch.getType().equals("3")||taskbatch.getType().equals("4")||taskbatch.getType().equals("5")||taskbatch.getType().equals("6")||taskbatch.getType().equals("7")||taskbatch.getType().equals("8")){
                        int usercount=0;
                        for(Sysuser sysuser:sysuserList){
                           if(taskbatchUserRepository.findByUserAndTaskbatchno(sysuser,taskbatch.getBatchno())==null) {
                               TaskbatchUser taskbatchUser = new TaskbatchUser();
                               taskbatchUser.setTaskbatchno(taskbatch.getBatchno());
                               taskbatchUser.setCreatetime(DateTimeUtils.getDate());
                               taskbatchUser.setUser(sysuser);
                               taskbatchUser.setId(UUID.randomUUID().toString());
                               taskbatchUser.setDocount(0);
                               taskbatchUserRepository.save(taskbatchUser);
                               usercount++;
                           }
                        }
                        taskbatch.setStatus(Variable.TASKBATCH_STATUS_PUB);
                        taskbatch.setUsercounts(0);
                        taskbatch.setStarttime(DateTimeUtils.getDate());
                        taskbatchRepository.save(taskbatch);
                        Taskqueue taskqueue = new Taskqueue();
                        taskqueue.setBatchno(taskbatch.getBatchno());
                        List<String[]> filelist = taskbatch.getFilenames();
                        taskqueue.setFilelist(filelist);
                        taskqueue.setId(UUID.randomUUID().toString());
                        taskqueueRepository.save(taskqueue);
                    }else {
                        List<String[]> filelist = taskbatch.getFilenames();
                        int usersize = sysuserList.size();
                        int usercount = 0;
                        String starttime = DateTimeUtils.getDate();
                        for (int i = 0; i < filelist.size(); i++) {
                            int k = i;
                            usercount = i + 1;
                            if (i >= usersize) {
                                k = i % usersize;
                                usercount = usersize;
                            }
                            Sysuser sysuser = sysuserList.get(k);
                            Task task = new Task();
                            String taskid = UUID.randomUUID().toString();
                            task.setId(taskid);
                            task.setCreatetime(starttime);
                            String taskno = "t" + DateTimeUtils.getDate("yyMMddHHmmssSSS") + RandomUtil.createRandomNum(4);
                            task.setTaskno(taskno);
                            task.setBatchno(batchno);
                            task.setTitle(taskbatch.getTitle());
                            String filename = filelist.get(i)[1];
                            //filename = filename.replace("/Users/robinjie/IdeaProjects/annotation/src/main/resources/","");
                            //filename = filefloder+filename;
                            task.setFilename(filename);
                            task.setFolder(filelist.get(i)[0]);
                            task.setStatus(Variable.TASK_STATUS_DOING);
                            task.setSysuser(sysuser);
                            task.setType(taskbatch.getType());

                            taskRepository.save(task);
                            //月任务数加1
                            monthtaskService.addMonthtaskDonum(sysuser, 1l);
                        }
                        taskbatch.setStatus(Variable.TASKBATCH_STATUS_PUB);
                        taskbatch.setUsercounts(usercount);
                        taskbatch.setStarttime(starttime);
                        taskbatchRepository.save(taskbatch);
                    }
                }
            }
        }else{
            model.addAttribute("msg","任务批次为空");
        }
        return "redirect:../admin/taskbatchlist";
    }

    /**
     * 已完成任务列表
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value="/finishtasklist")
    public String finishtasklist(Model model,HttpServletRequest request){
        String batchno = request.getParameter("batchno");
        List<Task> taskList =null;
        if(batchno!=null&&!batchno.isEmpty()) {
            taskList = taskRepository.findByBatchnoAndStatus(batchno, Variable.TASK_STATUS_FINISH);
        }else{
            taskList = taskRepository.findByStatus(Variable.TASK_STATUS_FINISH);
//            List<Task> taskall =  taskRepository.findAll();
//            for(Task task:taskall){
//                String batchno1 = task.getBatchno();
//                Taskbatch taskbatch = taskbatchRepository.findByBatchno(batchno1);
//                task.setTitle(taskbatch.getTitle());
//                taskRepository.save(task);
//            }

        }
        model.addAttribute("taskList",taskList);
        return "finishtasklist";
    }

    /**
     * 车辆图片结果页
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = { "/carpicresult" }, produces="application/json;charset=UTF-8")
    @ResponseBody
    public String carpicresult(HttpServletRequest request,HttpServletResponse response) {
        response.addHeader("Access-Control-Allow-Origin", "*");

        List<Carpic> showlist = new ArrayList<>();
        String taskbatchno = request.getParameter("taskbatchno");
        List<Carpic> carpicList = carpicRepository.findByTaskbatchno(taskbatchno);
//       logger.info("carpicListsize==="+carpicList.size());
        for(Carpic carpic:carpicList){
            carpic.setId(null);
            carpic.setTaskno(null);
            carpic.setTaskbatchno(null);
            showlist.add(carpic);
        }
        String jsonstr = JSONObject.toJSONString(showlist);
        return jsonstr;
    }

    /**
     * 车辆信息结果页
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = { "/carinforesult" }, produces="application/json;charset=UTF-8")
    @ResponseBody
    public String carinforesult(HttpServletRequest request,HttpServletResponse response) {
        response.addHeader("Access-Control-Allow-Origin", "*");

        List<CarsData> showlist = new ArrayList<>();
        String taskbatchno = request.getParameter("taskbatchno");
//        List<Task> taskList = taskRepository.findByBatchnoAndStatus(taskbatchno,Variable.TASK_STATUS_FINISH);
        List<Task> taskList = taskRepository.findByBatchno(taskbatchno);
        List<String> tasknolist = new ArrayList<>();
        for(Task task:taskList){
            tasknolist.add(task.getTaskno());
        }
        List<CarsData> carsDataList = carsdataRepository.findByTasknoIn(tasknolist);
       //logger.info("carsDataList==="+carsDataList.size());
        for(CarsData carsData:carsDataList){
            carsData.setId(null);
            carsData.setTaskno(null);
            carsData.setTaskbatchno(null);
            showlist.add(carsData);
        }
        String jsonstr = JSONObject.toJSONString(showlist);
        return jsonstr;
    }

    /**
     * 笔录类型任务结果页
     * @param response
     * @param request
     * @return
     */
    @RequestMapping(value = { "/recordresult" }, produces="application/json;charset=UTF-8")
    @ResponseBody
    public String recordresult(HttpServletResponse response,HttpServletRequest request) {

        String taskno = request.getParameter("taskno");
        response.addHeader("Access-Control-Allow-Origin", "*");
        Task task = taskRepository.findByTaskno(taskno);
        Record record = recordRepository.findByFilenameAndCasename(task.getFilename(),task.getFolder());
        String jsonstr =JSONObject.toJSONString(record);
        return jsonstr;
    }

    /**
     * 跳转至添加做任务的人
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value="/totaskbatchadduser")
    public String totaskbatchadduser(Model model,HttpServletRequest request){
        String batchno = request.getParameter("batchno");
        if(batchno!=null&&!batchno.isEmpty()) {
            Taskbatch taskbatch = taskbatchRepository.findByBatchno(batchno);
            model.addAttribute("taskbatch",taskbatch);
            List<String> rolelist = new ArrayList<>();
            rolelist.add(Variable.USER_ROLE_NORMAL);
            List<Sysuser> sysuserListt = sysuerRepository.findByRoleInAndStatus(rolelist,Variable.USER_STATUS_NORMAL);
            List<Sysuser> sysuserList = new ArrayList<>();
            for(Sysuser sysuser:sysuserListt){
                if(taskbatchUserRepository.findByUserAndTaskbatchno(sysuser,taskbatch.getBatchno())==null){
                    sysuserList.add(sysuser);
                }
            }
            model.addAttribute("sysuserList",sysuserList);
        }else{
            model.addAttribute("msg","任务批次为空");
        }
        return "taskbatchadduser";
    }

    /**
     * 增加做任务的人
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value="/taskbatchadduser",method = RequestMethod.POST)
    public String taskbatchadduser(Model model,HttpServletRequest request) {
        String batchno = request.getParameter("batchno");
        String sysusers = request.getParameter("sysusers");
        if (batchno != null && !batchno.isEmpty()) {
            if (sysusers != null && !sysusers.isEmpty()) {
                String[] sysuserids = sysusers.split(",");
                List<Sysuser> sysuserList = new ArrayList<>();
                for (String id : sysuserids) {
                    Optional<Sysuser> optional = sysuerRepository.findById(id);
                    Sysuser sysuser = optional.get();
                    if (sysuser != null) {
                        sysuserList.add(sysuser);
                    }
                }
                Taskbatch taskbatch = taskbatchRepository.findByBatchno(batchno);
                if (taskbatch != null) {
                    int usercount = 0;
                    for (Sysuser sysuser : sysuserList) {
                        if (taskbatchUserRepository.findByUserAndTaskbatchno(sysuser, taskbatch.getBatchno()) == null) {
                            TaskbatchUser taskbatchUser = new TaskbatchUser();
                            taskbatchUser.setTaskbatchno(taskbatch.getBatchno());
                            taskbatchUser.setCreatetime(DateTimeUtils.getDate());
                            taskbatchUser.setUser(sysuser);
                            taskbatchUser.setId(UUID.randomUUID().toString());
                            taskbatchUser.setDocount(0);
                            taskbatchUserRepository.save(taskbatchUser);
                            usercount++;
                        }
                    }
                    if(usercount>0){
                        taskbatch.setUsercounts(taskbatch.getUsercounts()+usercount);
                        taskbatchRepository.save(taskbatch);
                    }
                }
            }
        }
        return "redirect:../admin/taskbatchlist";
    }

    /**
     * 用户月任务统计
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value="/monthtasklist")
    public String monthtasklist(Model model,HttpServletRequest request){
        String month = request.getParameter("month");
        if(month==null||month.isEmpty()){
            month = DateTimeUtils.getDate("yyyy-MM");
        }
        model.addAttribute("month",month);
        HttpSession session = request.getSession();
        Sysuser sysuser = (Sysuser)session.getAttribute(Variable.SESSION_SYSUSER);
        if(sysuser.getRole().equals(Variable.USER_ROLE_MANAGER)) {
            List<Monthtask> monthtaskList = monthtaskService.findMonthtaskList(month);
            if(monthtaskList!=null&&!monthtaskList.isEmpty()){
                for(Monthtask monthtask:monthtaskList){
                    if(monthtask.getFailrate()==null){
                        long failnum = monthtask.getFailnum()==null?0: monthtask.getFailnum();
                        long tasknum = monthtask.getDonum()==null?0: monthtask.getDonum();
                        if(tasknum==0){
                            monthtask.setFailrate(0.00);
                        }else {
                            BigDecimal b   =   new   BigDecimal(failnum * 100.00 / tasknum);
                            double   failrate   =   b.setScale(2,   RoundingMode.HALF_UP).doubleValue();
                            monthtask.setFailrate(failrate);
                        }

                    }
                }
            }
            model.addAttribute("monthtaskList", monthtaskList);
            return "monthtasklist";
        }else{
            Monthtask monthtask = monthtaskService.findByMonthAndSysuser(month,sysuser);
            if(monthtask!=null){
                if(monthtask.getFailrate()==null){
                    long failnum = monthtask.getFailnum()==null?0:monthtask.getFailnum();
                    long tasknum = monthtask.getDonum()==null?0:monthtask.getDonum();
                    if(tasknum==0){
                        monthtask.setFailrate(0.00);
                    }else {
                        BigDecimal b   =   new   BigDecimal(failnum * 100.00 / tasknum);
                        double   failrate   =   b.setScale(2,   RoundingMode.HALF_UP).doubleValue();
                        monthtask.setFailrate(failrate);
                    }
                }
            }
            model.addAttribute("monthtask", monthtask);
            return "monthtask";
        }
    }

    /**
     * 用户日任务统计
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value="/daytasklist")
    public String daytasklist(Model model,HttpServletRequest request){
        String day = request.getParameter("day");
        if(day==null||day.isEmpty()){
            day=DateTimeUtils.getDate("yyyy-MM-dd");
        }
        model.addAttribute("day",day);

        HttpSession session = request.getSession();
        Sysuser sysuser = (Sysuser)session.getAttribute(Variable.SESSION_SYSUSER);
        if(sysuser.getRole().equals(Variable.USER_ROLE_MANAGER)) {
            List<Daytask> daytaskList = new ArrayList<>();
            daytaskList = daytaskService.findDaytaskList(day);
            if(daytaskList!=null&&!daytaskList.isEmpty()){
                for(Daytask daytask:daytaskList){
                    if(daytask.getFailrate()==null){
                        long failnum = daytask.getFailnum()==null?0:daytask.getFailnum();
                        long tasknum = daytask.getDonum()==null?0:daytask.getDonum();
                        if(tasknum==0){
                            daytask.setFailrate(0.00);
                        }else {
                            BigDecimal b   =   new   BigDecimal(failnum * 100.00 / tasknum);
                            double   failrate   =   b.setScale(2,   RoundingMode.HALF_UP).doubleValue();
                            daytask.setFailrate(failrate);
                        }


                    }
                }
            }
            model.addAttribute("daytaskList",daytaskList);
            return "daytasklist";
        }else{
            Daytask daytask = daytaskService.findByDayAndSysuser(day,sysuser);
            if(daytask!=null){
                if(daytask.getFailrate()==null){
                    long failnum = daytask.getFailnum()==null?0:daytask.getFailnum();
                    long tasknum = daytask.getDonum()==null?0:daytask.getDonum();
                    if(tasknum==0){
                        daytask.setFailrate(0.00);
                    }else {
                        BigDecimal b   =   new   BigDecimal(failnum * 100.00 / tasknum);
                        double   failrate   =   b.setScale(2,   RoundingMode.HALF_UP).doubleValue();
                        daytask.setFailrate(failrate);
                    }
                }
            }
            model.addAttribute("daytask",daytask);
            return "daytask";
        }



    }

    /**
     * 文本录入结果页
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = { "/textdataresult" }, produces="application/json;charset=UTF-8")
    @ResponseBody
    public String textdataresult(HttpServletRequest request,HttpServletResponse response) {
        response.addHeader("Access-Control-Allow-Origin", "*");

        List<Textdata> showlist = new ArrayList<>();
        String taskbatchno = request.getParameter("taskbatchno");
        List<Task> taskList = taskRepository.findByBatchnoAndStatus(taskbatchno,Variable.TASK_STATUS_FINISH);
        List<String> tasknolist = new ArrayList<>();
        for(Task task:taskList){
            tasknolist.add(task.getTaskno());
        }
        List<Textdata> textdataList = textdataRepository.findByTasknoIn(tasknolist);

        for(Textdata textdata:textdataList){
            textdata.setId(null);
            textdata.setTaskno(null);
            textdata.setTaskbatchno(null);
            showlist.add(textdata);
        }
        String jsonstr = JSONObject.toJSONString(showlist);
        return jsonstr;
    }

    /**
     * 行人信息结果页
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = { "/personinforesult" }, produces="application/json;charset=UTF-8")
    @ResponseBody
    public String personinforesult(HttpServletRequest request,HttpServletResponse response) {
        response.addHeader("Access-Control-Allow-Origin", "*");

        String taskbatchno = request.getParameter("taskbatchno");
        List<Task> taskList = taskRepository.findByBatchnoAndStatus(taskbatchno,Variable.TASK_STATUS_FINISH);
        List<String> tasknolist = new ArrayList<>();
        for(Task task:taskList){
            tasknolist.add(task.getTaskno());
        }
        List<PersonsData> personsDataList = personsDataRepository.findByTasknoIn(tasknolist);
        //logger.info("carsDataList==="+carsDataList.size());
//        List<CarPersonData> showlist = new ArrayList<>();
        FrameDatas datas = new FrameDatas();
        List<Frame> frameList = new ArrayList<>();
        for(PersonsData personsData:personsDataList){
            Frame frame = new Frame();
//            CarPersonData carPersonData = new CarPersonData();
            Task task = taskRepository.findByTaskno(personsData.getTaskno());
            if(task.getData()!=null){
                CarsData carsData =(CarsData)task.getData();
                carsData.setTaskbatchno(null);
                carsData.setTaskno(null);
                carsData.setId(null);
                carsData.setFilename(null);
//                carPersonData.setCarsData(carsData);
                List<Cardata> cardatas = carsData.getCars();
                List<Car> carlist = new ArrayList<>();
                for(Cardata cardata:cardatas){
                    if(cardata.getCarlefttop()!=null&&!cardata.getCarlefttop().isEmpty()) {
                        Car car = new Car();
                        car.setType(cardata.getType());
                        car.setCar_color(cardata.getColor());
                        car.setCar_leftbottom(cardata.getCarleftbottom());
                        car.setCar_lefttop(cardata.getCarlefttop());
                        car.setCar_rightbottom(cardata.getCarrightbottom());
                        car.setCar_righttop(cardata.getCarrighttop());
                        car.setLicense_plate_leftbottom(cardata.getLeftbottom());
                        car.setLicense_plate_lefttop(cardata.getLefttop());
                        car.setLicense_plate_rightbottom(cardata.getRightbottom());
                        car.setLicense_plate_righttop(cardata.getRighttop());
                        car.setLicense_plate_no(cardata.getCarno());
                        car.setMake(cardata.getBrand());
                        carlist.add(car);
                    }
                }
                frame.setCar_list(carlist);
            }
            personsData.setId(null);
            personsData.setTaskno(null);
            personsData.setTaskbatchno(null);
//            carPersonData.setFilename (personsData.getFilename().substring(personsData.getFilename().length()-16));
            //personsData.setFilename(null);
//            carPersonData.setPersonsData(personsData);

//            showlist.add(carPersonData);
            List<People> peopleList = new ArrayList<>();
            for(PersonInfo person:personsData.getPersons()){
                //if(person.getLefttop()!=null&&!person.getLefttop().isEmpty()) {
                    People people = new People();
                    people.setAge(person.getAge());
                    people.setBody_type(person.getShape());
                    people.setCoat_color(person.getClothcolor());
                    people.setGender(person.getSex());
                    people.setMovement_state(person.getMovement_state());
                    people.setPants_color(person.getPantscolor());
                    people.setPerson_leftbottom(person.getLeftbottom());
                    people.setPerson_lefttop(person.getLefttop());
                    people.setPerson_rightbottom(person.getRightbottom());
                    people.setPerson_righttop(person.getRighttop());
                    peopleList.add(people);
                //}
            }
            frame.setFilename(personsData.getFilename());
            frame.setPeople_list(peopleList);
            frameList.add(frame);

        }
        datas.setFrames_list(frameList);
        String jsonstr = JSONObject.toJSONString(datas);
        return jsonstr;
    }

    /**
     * 任务用户完成数统计
     * @param model
     * @param request
     * @return
     */
    @RequestMapping(value="/taskstatlist")
    public String taskstatlist(Model model,HttpServletRequest request) {
        String batchno = request.getParameter("batchno");
        HttpSession session = request.getSession();
        Sysuser sysuser = (Sysuser) session.getAttribute(Variable.SESSION_SYSUSER);
        Taskbatch taskbatch = taskbatchRepository.findByBatchno(batchno);
        model.addAttribute("taskbatch", taskbatch);
        if (sysuser.getRole().equals(Variable.USER_ROLE_MANAGER)) {
            List<Daytask> daytaskList = new ArrayList<>();
            List<Sysuser> sysuserList = sysuerRepository.findByRole(Variable.USER_ROLE_NORMAL);
            for (Sysuser sysuser1 : sysuserList) {
                List<Task> successtasklist = taskRepository.findByBatchnoAndFirstresultAndSysuser(batchno, 1, sysuser1);
                List<Task> failetasklist = taskRepository.findByBatchnoAndFirstresultLessThanAndSysuser(batchno, 1, sysuser1);

                Daytask daytask = new Daytask();
                daytask.setSysuser(sysuser1);
                daytask.setSuccessnum(Long.valueOf(successtasklist.size()));
                daytask.setFailnum(Long.valueOf(failetasklist.size()));

                long failnum = daytask.getFailnum() == null ? 0 : daytask.getFailnum();
                long tasksuccessnum = daytask.getSuccessnum() == null ? 0 : daytask.getSuccessnum();
                long tasknum = failnum + tasksuccessnum;
                daytask.setDonum(tasknum);
                daytask.setTasknum(tasknum);
                if(tasknum>0){
                    BigDecimal b = new BigDecimal(failnum * 100.00 / tasknum);
                    double failrate = b.setScale(2, RoundingMode.HALF_UP).doubleValue();
                    daytask.setFailrate(failrate);
                    daytaskList.add(daytask);
                }
            }
            model.addAttribute("daytaskList", daytaskList);
            return "taskbatchstatlist";
        } else {
            List<Task> successtasklist = taskRepository.findByBatchnoAndFirstresultAndSysuser(batchno, 1, sysuser);
            List<Task> failetasklist = taskRepository.findByBatchnoAndFirstresultLessThanAndSysuser(batchno, 1, sysuser);
            Daytask daytask = new Daytask();
            daytask.setSysuser(sysuser);
            daytask.setSuccessnum(Long.valueOf(successtasklist.size()));
            daytask.setFailnum(Long.valueOf(failetasklist.size()));

            long failnum = daytask.getFailnum() == null ? 0 : daytask.getFailnum();
            long tasksuccessnum = daytask.getSuccessnum() == null ? 0 : daytask.getSuccessnum();
            long tasknum = failnum + tasksuccessnum;
            daytask.setTasknum(tasknum);
            daytask.setDonum(tasknum);
            BigDecimal b = new BigDecimal(failnum * 100.00 / tasknum);
            double failrate = b.setScale(2, RoundingMode.HALF_UP).doubleValue();
            daytask.setFailrate(failrate);
            model.addAttribute("daytask", daytask);
            return "taskbatchstat";
        }
    }

    /**
     * 人包结果页
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = { "/personbagresult" }, produces="application/json;charset=UTF-8")
    @ResponseBody
    public String personbagresult(HttpServletRequest request,HttpServletResponse response) {
        response.addHeader("Access-Control-Allow-Origin", "*");

        String taskbatchno = request.getParameter("taskbatchno");
//        List<Task> taskList = taskRepository.findByBatchnoAndStatus(taskbatchno, Variable.TASK_STATUS_FINISH);
        List<Task> taskList = taskRepository.findByBatchno(taskbatchno);
        List<String> tasknolist = new ArrayList<>();
        for (Task task : taskList) {
            tasknolist.add(task.getTaskno());
        }
        List<Segment> segmentList = segmentRepository.findByTasknoIn(tasknolist);
        for(Segment segment:segmentList){
            segment.setPackagename(null);
            segment.setTaskbatchno(null);
            segment.setTaskno(null);
            segment.setId(null);
        }
        VidoeBo vidoeBo = new VidoeBo();
        vidoeBo.setSegments_info(segmentList);
        String jsonstr = JSONObject.toJSONString(vidoeBo);
        return jsonstr;
    }
    //初审全过
    @RequestMapping(value="/firstallokbatch")
    public String firstallokbatch(Model model,HttpServletRequest request){
        String batchno = request.getParameter("batchno");
        List<Task> tasklist =  taskRepository.findByBatchnoAndStatus(batchno,Variable.TASK_STATUS_SUBMIT);
        String [] tasknoarray = new String [tasklist.size()];
        for(int i=0;i<tasklist.size();i++){
            tasknoarray[i] = tasklist.get(i).getTaskno();
        }
        if(tasknoarray.length>=1) {
            logger.info("begain firstallok ,数量："+tasknoarray.length);
            taskService.firstallok(tasknoarray);
        }
        return "redirect:../admin/taskbatchlist";
    }

    //复审全过
    @RequestMapping(value="/allokbatch")
    public String allokbatch(Model model,HttpServletRequest request){
        String batchno = request.getParameter("batchno");
        List<Task> tasklist =  taskRepository.findByBatchnoAndStatus(batchno,Variable.TASK_STATUS_FIRSTCHECK);
        String [] tasknoarray = new String [tasklist.size()];
        for(int i=0;i<tasklist.size();i++){
            tasknoarray[i] = tasklist.get(i).getTaskno();
        }
        if(tasknoarray.length>=1) {
            logger.info("begain secondallok ,数量："+tasknoarray.length);
            taskService.allok(tasknoarray);
//            monthtaskService.addMonthtaskDonum(sysuser, (long)tasknoarray.length);
        }
        return "redirect:../admin/taskbatchlist";
    }


}
