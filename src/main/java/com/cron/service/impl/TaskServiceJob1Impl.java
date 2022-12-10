package com.cron.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import com.cron.service.TaskService;

@Service
@Primary
public class TaskServiceJob1Impl implements TaskService {
    @Override
    public void HandlerJob() {
        System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + " " + Thread.currentThread().getName() + "  task1 start");
        /*
        try {
            Thread.sleep(20000);//任務耗時10秒
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        */
        System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + " " + Thread.currentThread().getName() + "  task1 end");

    }

    @Override
    public Integer jobId() {
        return 1;
    }
}