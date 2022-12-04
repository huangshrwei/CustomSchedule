package com.cron.controller;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cron.config.MyScheduledTask;
import com.cron.dto.TaskDto;

import io.swagger.annotations.Api;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Api(tags = "StartScheduleController")
@Tag(name = "StartScheduleController", description = "排程控制")
@RequestMapping("/task")
public class StartController {

    @Autowired
    private MyScheduledTask scheduledTask;

    @PostMapping(value = "/start")
    public String changeCron(){
    	    List<TaskDto> list;
            // 這裡模擬存在資料庫的資料
            list = Arrays.asList(
                    new TaskDto(1, "test1","*/30 * * * * ?")
                    //,new TaskDto(2, "test2","*/20 * * * * ?")
            );
        scheduledTask.refresh(list);
        return "task:" + list.toString() + "already be executed";
    }

    @PostMapping(value = "/stopCron")
    public String stopCron(@RequestBody List<TaskDto> list){
        if (CollectionUtils.isEmpty(list)) {
            // 這裡模擬將要停止的cron可通過前端傳來
            list = Arrays.asList(
                    new TaskDto(1, "test1","*/6 * * * * ?") ,
                    new TaskDto(2, "test2","*/20 * * * * ?")
            );
        }
        scheduledTask.stop(list);
        List<Integer> collect = list.stream().map(TaskDto::getTaskId).collect(Collectors.toList());
        return "task:" + collect.toString() + "alread be stopped";
    }

}
