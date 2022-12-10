package com.cron.config;

import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.CronTask;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.util.StringUtils;

import com.cron.dto.TaskDto;

import jakarta.annotation.PreDestroy;

@Configuration
@EnableScheduling
public class MyScheduledTask implements SchedulingConfigurer {

    private volatile ScheduledTaskRegistrar registrar;

    private final ConcurrentHashMap<Integer, ScheduledFuture<?>> scheduledFutures = new ConcurrentHashMap<>();
    private final ConcurrentHashMap<Integer, CronTask> cronTasks = new ConcurrentHashMap<>();

    @Autowired
    private TaskSolverChooser taskSolverChooser;
   

    @Override
    public void configureTasks(ScheduledTaskRegistrar registrar) {

        //設定20個執行緒,預設單執行緒,如果不設定的話，不能同時並行執行任務
        registrar.setScheduler(Executors.newScheduledThreadPool(10));
        this.registrar = registrar;
    }

    /**
     * 修改 cron 需要 呼叫該方法
     */
    public void refresh(List<TaskDto> tasks){
        //取消已經刪除的策略任務
        Set<Integer> sids = scheduledFutures.keySet();
        for (Integer sid : sids) {
            if(!exists(tasks, sid)){
                scheduledFutures.get(sid).cancel(false);
            }
        }
        for (TaskDto TaskEntity : tasks) {
        	if (taskSolverChooser.getTask(TaskEntity.getTaskId())!=null) {
	            String expression = TaskEntity.getExpression();
	            //計劃任務表示式為空則跳過
	            if(!StringUtils.hasLength(expression)){
	                continue;
	            }
	            //計劃任務已存在並且表示式未發生變化則跳過
	            if (scheduledFutures.containsKey(TaskEntity.getTaskId())
	                    && cronTasks.get(TaskEntity.getTaskId()).getExpression().equals(expression)) {
	                continue;
	            }
	            //如果策略執行時間發生了變化，則取消當前策略的任務
	            if(scheduledFutures.containsKey(TaskEntity.getTaskId())){
	                scheduledFutures.get(TaskEntity.getTaskId()).cancel(false);
	                scheduledFutures.remove(TaskEntity.getTaskId());
	                cronTasks.remove(TaskEntity.getTaskId());
	            }
	            //業務邏輯處理
	            CronTask task = cronTask(TaskEntity, expression);
	
	
	            //執行業務
	            ScheduledFuture<?> future = registrar.getScheduler().schedule(task.getRunnable(), task.getTrigger());
	            cronTasks.put(TaskEntity.getTaskId(), task);
	            scheduledFutures.put(TaskEntity.getTaskId(), future);
        	}
        }
    }
    
    /**
     * 直接執行
     */
    @SuppressWarnings("deprecation")
	public void run(int id){
    	if (taskSolverChooser.getTask(id)!=null) {
            //業務邏輯處理
            CronTask task = onceTask(id);
            //執行業務
            ScheduledFuture<?> future = registrar.getScheduler().schedule(task.getRunnable(), new Date());
            cronTasks.put(id, task);
            scheduledFutures.put(id, future);
    	}
    }    

    /**
     * 停止 cron 執行
     */
    public void stop(List<TaskDto> tasks){
        tasks.forEach(item->{
            if (scheduledFutures.containsKey(item.getTaskId())) {
                // mayInterruptIfRunning設成false話，不允許線上程執行時中斷，設成true的話就允許。
                scheduledFutures.get(item.getTaskId()).cancel(false);
                scheduledFutures.remove(item.getTaskId());
            }
        });
    }

    /**
     * 業務邏輯處理
     */
    public CronTask cronTask(TaskDto TaskEntity, String expression)  {
        return new CronTask(() -> {
                    //每個計劃任務實際需要執行的具體業務邏輯
                    //採用策略，模式 ，執行我們的job
                   taskSolverChooser.getTask(TaskEntity.getTaskId()).HandlerJob();
                }, expression);
    }
    

    /**
     * 業務邏輯處理
     */
    public CronTask onceTask(int id)  {
        return new CronTask(() -> {
                    //每個計劃任務實際需要執行的具體業務邏輯
                    //採用策略，模式 ，執行我們的job
                   taskSolverChooser.getTask(id).HandlerJob();
                }, "* * * * * *");
    }
    

    private boolean exists(List<TaskDto> tasks, Integer tid){
        for(TaskDto TaskEntity:tasks){
            if(TaskEntity.getTaskId() == tid){
                return true;
            }
        }
        return false;
    }

    @PreDestroy
    public void destroy() {
        this.registrar.destroy();
    }

}