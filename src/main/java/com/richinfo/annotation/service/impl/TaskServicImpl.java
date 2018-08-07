package com.richinfo.annotation.service.impl;

import com.richinfo.annotation.po.*;
import com.richinfo.annotation.repository.*;
import com.richinfo.annotation.service.MonthtaskService;
import com.richinfo.annotation.service.TaskService;
import com.richinfo.util.DateTimeUtils;
import com.richinfo.util.RandomUtil;
import com.richinfo.util.Variable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@Service
public class TaskServicImpl implements TaskService {
    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    private SysuserRepository sysuserRepository;
    @Autowired
    private MonthtaskService monthtaskService;

    @Autowired
    private TaskbatchRepository taskbatchRepository;

    @Autowired
    private TaskqueueRepository taskqueueRepository;

    @Autowired
    private CarsdataRepository carsdataRepository;

    private Logger logger = LoggerFactory.getLogger(TaskServicImpl.class);

    /**
     * 提交审核
     * @param task
     */
    @Override
    public void submitTask(Task task) {
        task.setStatus(Variable.TASK_STATUS_SUBMIT);
        task.setSubmittime(DateTimeUtils.getDate());

        task.setSubmittime(DateTimeUtils.getDate());
        List<String> rolelist = new ArrayList<>();
        rolelist.add(Variable.USER_ROLE_FIRSTCHECKER);
        List<Sysuser> firstcheckerlist = sysuserRepository.findByRoleInAndStatus(rolelist,Variable.USER_STATUS_NORMAL);
        if(firstcheckerlist!=null&&!firstcheckerlist.isEmpty()){
            Random random = new Random();
            int i = random.nextInt(firstcheckerlist.size());
            Sysuser firstchecker = firstcheckerlist.get(i);
            task.setFirstchecker(firstchecker);
            //monthtaskService.addMonthtaskTasknum(firstchecker,1l);
        }
        taskRepository.save(task);
    }

    /**
     * 初审任务
     * @param task
     * @param firstresult
     */
    @Override
    public void firstcheckTask(Task task, String firstresult) {
        task.setStatus(Variable.TASK_STATUS_FIRSTCHECK);
        task.setFirstresult(Integer.valueOf(firstresult));
        task.setFirstchecktime(DateTimeUtils.getDate());
        if (!"1".equals(firstresult)) {//初检不通过
            monthtaskService.addMonthtaskFailnum(task.getSysuser(), 1l);
        } else {
            monthtaskService.addMonthtaskSuccessnum(task.getSysuser(), 1l);
        }
        if("-1".equals(firstresult)){
            task.setStatus(Variable.TASK_STATUS_DOING);
            taskRepository.save(task);
            Taskbatch taskbatch = taskbatchRepository.findByBatchno(task.getBatchno());
            if(taskbatch.getStatus()==Variable.TASKBATCH_STATUS_FINISH) {
                taskbatch.setStatus(Variable.TASKBATCH_STATUS_PUB);
                taskbatchRepository.save(taskbatch);
            }
            overTask(task.getTaskno());
            task.setStatus(Variable.TASK_STATUS_FIRSTCHECK);
        }else {
            List<String> rolelist = new ArrayList<>();
            rolelist.add(Variable.USER_ROLE_SECONDCHECKER);
            List<Sysuser> secondcheckerlist = sysuserRepository.findByRoleInAndStatus(rolelist, Variable.USER_STATUS_NORMAL);
            if (secondcheckerlist != null && !secondcheckerlist.isEmpty()) {
                Random random = new Random();
                int i = random.nextInt(secondcheckerlist.size());
                Sysuser secondchecker = secondcheckerlist.get(i);
                task.setSecondchecker(secondchecker);
                monthtaskService.addMonthtaskTasknum(secondchecker, 1l);
            }
        }
        taskRepository.save(task);
    }

    /**
     * 复审任务
     * @param task
     * @param secondresult
     */
    @Override
    public void secondcheckTask(Task task, String secondresult) {
        task.setStatus(Variable.TASK_STATUS_FINISH);
        task.setSecondresult(Integer.valueOf(secondresult));
        task.setFinishtime(DateTimeUtils.getDate());
        if ("0".equals(secondresult)) {//复检不通过
            monthtaskService.addMonthtaskFailnum(task.getFirstchecker(), 1l);
        } else {
            monthtaskService.addMonthtaskSuccessnum(task.getFirstchecker(), 1l);
        }
        taskRepository.save(task);
        //---如果复审数等于任务数结束任务 标记为已审核----
        Task extask = new Task();
        extask.setBatchno(task.getBatchno());
        extask.setStatus(Variable.TASK_STATUS_FINISH);
        Example<Task> example = Example.of(extask);
        long finishcount = taskRepository.count(example);
        Taskbatch taskbatch = taskbatchRepository.findByBatchno(task.getBatchno());
        if(finishcount>=taskbatch.getFilecounts()){
            taskbatch.setStatus(Variable.TASKBATCH_STATUS_CHECKED);
            taskbatchRepository.save(taskbatch);
        }
    }

    /**
     * 分配任务/争夺任务
      * @param sysuser
     * @param batchno
     * @return
     */
    @Override
    public synchronized String robTask(Sysuser sysuser, String batchno) {
        Taskbatch taskbatch =  taskbatchRepository.findByBatchno(batchno);
        if(sysuser.getStatus()!=Variable.USER_STATUS_NORMAL){
            return "erro";
        }
        if(taskbatch.getStatus()!=Variable.TASKBATCH_STATUS_PUB){
            return "erro";
        }
        int usercount = taskbatch.getUsercounts();//已获取量
//        List<String[]> filelist = taskbatch.getFilenames();
//        if(usercount>=filelist.size()){
//            taskbatch.setStatus(Variable.TASKBATCH_STATUS_FINISH);
//            taskbatchRepository.save(taskbatch);
//            return "erro";
//        }
        Taskqueue taskqueue =  taskqueueRepository.findByBatchno(batchno);
        if(taskqueue==null){
            return "erro";
        }
        List<String[]> filelist = taskqueue.getFilelist();
        String filename ="";
        String folder = "";
        if(filelist==null||filelist.isEmpty()){
            List<Task> taskList = taskRepository.findByBatchnoAndStatus(batchno,Variable.TASK_STATUS_DOING);
            if(taskList ==null ||taskList.isEmpty()){
                taskbatch.setStatus(Variable.TASKBATCH_STATUS_FINISH);
                taskbatchRepository.save(taskbatch);
            }
            return "erro";
        }else{
            filename =filelist.get(0)[1];
            folder =filelist.get(0)[0];
            filelist.remove(0);
            taskqueue.setFilelist(filelist);
            taskqueueRepository.save(taskqueue);
        }

        Task task = new Task();
        String taskid = UUID.randomUUID().toString();
        task.setId(taskid);
        task.setCreatetime(DateTimeUtils.getDate());
        String taskno = "t" + DateTimeUtils.getDate("yyMMddHHmmssSSS") + RandomUtil.createRandomNum(4);
        task.setTaskno(taskno);
        task.setBatchno(batchno);
        task.setTitle(taskbatch.getTitle());
        task.setFilename(filename);
        task.setFolder(folder);
        task.setStatus(Variable.TASK_STATUS_DOING);
        task.setSysuser(sysuser);
        task.setType(taskbatch.getType());

        if(taskbatch.getPrebatchno()!=null&&!taskbatch.getPrebatchno().isEmpty()){
            if(taskbatch.getType().equals("3")||taskbatch.getType().equals("5")){
                CarsData carsData =carsdataRepository.findByTaskbatchnoAndAndFilename(taskbatch.getPrebatchno(),filename);
                if(carsData!=null) {
                    task.setData(carsData);
                }
            }
        }

        taskRepository.save(task);

        taskbatch.setUsercounts(usercount+1);
        taskbatchRepository.save(taskbatch);
        return taskno;
    }

    /**
     * 超时结束
     */
    @Override
    public void endOvertimeTask() {
        List<Taskbatch> taskbatchList= taskbatchRepository.findByStatus(Variable.TASKBATCH_STATUS_PUB);
        for(Taskbatch taskbatch:taskbatchList){
            String batchno = taskbatch.getBatchno();
            List<Task> taskList = taskRepository.findByBatchnoAndStatus(batchno,Variable.TASK_STATUS_DOING);
            Taskqueue taskqueue =  taskqueueRepository.findByBatchno(batchno);
            if(taskList ==null ||taskList.isEmpty()){
                if(taskqueue==null || taskqueue.getFilelist()==null||taskqueue.getFilelist().isEmpty()){
                    taskbatch.setStatus(Variable.TASKBATCH_STATUS_FINISH);
                    taskbatchRepository.save(taskbatch);
                }
            }else {
                for (Task task : taskList) {
                    if (DateTimeUtils.betweensecond(task.getCreatetime(), DateTimeUtils.getDate(), "yyyy-MM-dd HH:mm:ss") / 60 > 12) {
                        logger.info("overtime task:" + task.getTitle() + ",user:" + task.getSysuser().getName() + ",createtime:" + task.getCreatetime());
                        task.setStatus(Variable.TASK_STATUS_OVERTIME);
                        taskRepository.save(task);
                        if (taskqueue != null) {
                            List<String[]> filelist = taskqueue.getFilelist();
                            if (filelist == null) {
                                filelist = new ArrayList<>();
                            }
                            String[] files = new String[2];
                            files[0] = task.getFolder();
                            files[1] = task.getFilename();
                            filelist.add(files);
                            taskqueue.setFilelist(filelist);
                            taskqueueRepository.save(taskqueue);
                            int usercount = taskbatch.getUsercounts();
                            taskbatch.setUsercounts(usercount - 1);
                            taskbatchRepository.save(taskbatch);
                        }
                    }
                }
            }

        }
    }

    /**
     * 结束任务
     * @param taskno
     */
    @Override
    public void overTask(String taskno) {
        Task task = taskRepository.findByTaskno(taskno);
        if(task.getStatus()==Variable.TASK_STATUS_DOING) {
            Taskqueue taskqueue = taskqueueRepository.findByBatchno(task.getBatchno());
            Taskbatch taskbatch = taskbatchRepository.findByBatchno(task.getBatchno());
            task.setStatus(Variable.TASK_STATUS_OVERTIME);
            taskRepository.save(task);
            if (taskqueue != null) {
                List<String[]> filelist = taskqueue.getFilelist();
                if (filelist == null) {
                    filelist = new ArrayList<>();
                }
                String[] files = new String[2];
                files[0] = task.getFolder();
                files[1] = task.getFilename();
                filelist.add(files);
                taskqueue.setFilelist(filelist);
                taskqueueRepository.save(taskqueue);
                int usercount = taskbatch.getUsercounts();
                taskbatch.setUsercounts(usercount - 1);
                taskbatchRepository.save(taskbatch);
            }
        }
    }

    @Override
    public void allok(String[] tasknos) {
        for(String taskno:tasknos){
            Task task = taskRepository.findByTaskno(taskno);
            task.setStatus(Variable.TASK_STATUS_FINISH);
            task.setSecondresult(1);
            task.setFinishtime(DateTimeUtils.getDate());
            monthtaskService.addMonthtaskSuccessnum(task.getFirstchecker(), 1l);
            taskRepository.save(task);
            //---如果复审数等于任务数结束任务 标记为已审核----
            Task extask = new Task();
            extask.setBatchno(task.getBatchno());
            extask.setStatus(Variable.TASK_STATUS_FINISH);
            Example<Task> example = Example.of(extask);
            long finishcount = taskRepository.count(example);
            Taskbatch taskbatch = taskbatchRepository.findByBatchno(task.getBatchno());
            if(finishcount>=taskbatch.getFilecounts()){
                taskbatch.setStatus(Variable.TASKBATCH_STATUS_CHECKED);
                taskbatchRepository.save(taskbatch);
            }
        }

    }

    @Override
    public void firstallok(String[] tasknos) {
        for(String taskno:tasknos){
            Task task = taskRepository.findByTaskno(taskno);
            firstcheckTask(task,"1");
        }
    }
}
