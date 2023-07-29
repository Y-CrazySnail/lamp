package com.yeem.proxy.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.yeem.entity.BaseEntity;

@TableName(value = "proxy_command_config", autoResultMap = true)
public class CommandConfig extends BaseEntity {
    private String name;
    private String command;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public enum Field {
        NAME("name"),
        COMMAND("command");
        private String name;

        Field(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }
}
