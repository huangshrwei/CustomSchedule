package com.cron.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.stereotype.Service;

import com.cron.service.TaskService;

@Service
public class TaskServiceJob2Impl implements TaskService {
    @Override
    public void HandlerJob() {
        System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + " " + Thread.currentThread().getName() + "  task2 start");
        try {
            Thread.sleep(1000);//任務耗時10秒
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + " " + Thread.currentThread().getName() + "  task2 end");

    }

    @Override
    public Integer jobId() {
        return 2;
    }
}