package com.snail.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.snail.chinaybop.entity.BaseEntity;

@TableName(value = "proxy_command", autoResultMap = true)
public class Command extends BaseEntity {
    private String ip;
    private String command;
    private Integer flag;
    private String result;

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

    public enum Field {
        IP("ip"),
        FLAG("flag");
        private String name;

        Field(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }
}
