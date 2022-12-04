package com.cron.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskDto {

    /**
     * 任務id
     */
    private int taskId;
    /**
     * 任務說明
     */
    private String desc;
    /**
     * cron 表示式
     */
    private String expression;
}
