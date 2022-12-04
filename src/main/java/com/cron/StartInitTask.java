package com.cron;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.cron.config.MyScheduledTask;
import com.cron.dto.TaskDto;

@Component
public class StartInitTask implements CommandLineRunner {

    @Autowired
    private MyScheduledTask myScheduledTask;

    @Override
    public void run(String... args) throws Exception {
        List<TaskDto> list = Arrays.asList(
                new TaskDto(1, "test1", "0/20 * *  * * ?")
                //new TaskDto(2, "test2", "0/30 * *  * * ?")
        );
        myScheduledTask.refresh(list);
    }
}