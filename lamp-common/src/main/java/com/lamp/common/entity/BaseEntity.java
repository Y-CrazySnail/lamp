package com.lamp.common.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
public class BaseEntity implements Serializable {

    /**
     * 主键ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    /**
     * 创建人
     *
     * @ignore
     */
    @TableField(fill = FieldFill.INSERT)
    private String createUser;

    /**
     * 创建时间
     *
     * @ignore
     */
    @TableField(fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    /**
     * 更新人
     *
     * @ignore
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private String updateUser;

    /**
     * 更新时间
     *
     * @ignore
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;

    /**
     * 删除标识
     *
     * @ignore
     */
    private Boolean deleteFlag;

    /**
     * 批量操作ID
     */
    @TableField(exist = false)
    private List<Long> idList;

    @TableField(exist = false)
    private Page<BaseEntity> page;

    public enum BaseField {
        ID("id"),
        CREATE_USER("create_user"),
        CREATE_TIME("create_time"),
        UPDATE_USER("update_user"),
        UPDATE_TIME("update_time"),
        DELETE_FLAG("delete_flag");
        private final String name;

        BaseField(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    public static <T extends BaseEntity> void setDeleteFlagCondition(LambdaQueryWrapper<T> queryWrapper) {
        queryWrapper.eq(BaseEntity::getDeleteFlag, 0);
    }
}
