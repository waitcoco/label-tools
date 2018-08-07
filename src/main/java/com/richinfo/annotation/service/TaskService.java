package com.richinfo.annotation.service;

import com.richinfo.annotation.po.Sysuser;
import com.richinfo.annotation.po.Task;

public interface TaskService {
    void submitTask(Task task);
    void firstcheckTask(Task task,String firstresult);
    void secondcheckTask(Task task,String secondresult);
    String robTask(Sysuser sysuser,String batchno);

    void endOvertimeTask();

    void overTask(String taskno);

    void allok(String[] tasknos);

    void firstallok(String[] tasknos);
}
