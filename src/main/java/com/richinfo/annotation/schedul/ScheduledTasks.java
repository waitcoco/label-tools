package com.richinfo.annotation.schedul;

import com.richinfo.annotation.service.TaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ScheduledTasks {
    private Logger logger = LoggerFactory.getLogger(ScheduledTasks.class);
    @Autowired
    private TaskService taskService;
    @Scheduled(fixedDelay = 1000*60*3)//每3分钟执行一次
    public void endOvertimeTask() {
        logger.info("===endOvertimeTask===");
        taskService.endOvertimeTask();
    }
}
