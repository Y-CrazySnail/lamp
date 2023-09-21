package com.yeem.log;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
@TableName("sys_log")
public class SysLog implements Serializable {

    private static final long serialVersionUID = 115492983341579610L;

    /**
     * id
     **/
    @TableId(value = "id", type = IdType.ASSIGN_UUID)
    private String id;

    /**
     * 线程id
     **/
    private Integer thread;

    /**
     * ip
     **/
    private String ip;

    /**
     * 请求连接
     **/
    private String uri;

    /**
     * 功能模块
     **/
    private String operateModule;

    /**
     * 操作类型
     **/
    private String operateType;

    /**
     * 操作方法
     **/
    private String operateMethod;

    /**
     * 操作描述
     **/
    private String operateDesc;

    /**
     * 请求状态(1正常 2异常)
     **/
    private Integer requestState;

    /**
     * 请求参数
     **/
    private String requestParam;

    /**
     * 返回参数
     **/
    private String responseParam;

    /**
     * 异常名称
     **/
    private String exceptionName;

    /**
     * 异常信息
     **/
    private String exceptionMessage;

    /**
     * 操作时间
     **/
    private Date createTime;

    /**
     * 用户名
     **/
    private String userName;

    /**
     * 请求时长(秒)
     **/
    private int sessionTime;
}