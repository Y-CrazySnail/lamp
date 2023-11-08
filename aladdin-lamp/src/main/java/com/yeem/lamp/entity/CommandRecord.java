package com.yeem.lamp.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.yeem.common.entity.BaseEntity;

import java.time.LocalDateTime;

@TableName(value = "proxy_command_record", autoResultMap = true)
public class CommandRecord extends BaseEntity {
    private String type;
    private String ip;
    private String command;
    private Integer flag;
    private String result;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    private LocalDateTime executeStartTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.SSS")
    private LocalDateTime executeEndTime;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public Integer getFlag() {
        return flag;
    }

    public void setFlag(Integer flag) {
        this.flag = flag;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public LocalDateTime getExecuteStartTime() {
        return executeStartTime;
    }

    public void setExecuteStartTime(LocalDateTime executeStartTime) {
        this.executeStartTime = executeStartTime;
    }

    public LocalDateTime getExecuteEndTime() {
        return executeEndTime;
    }

    public void setExecuteEndTime(LocalDateTime executeEndTime) {
        this.executeEndTime = executeEndTime;
    }

    public enum Field {
        IP("ip"),
        FLAG("flag"),
        EXECUTE_START_TIME("execute_start_time"),
        EXECUTE_END_TIME("execute_end_time");
        private String name;

        Field(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }
}
