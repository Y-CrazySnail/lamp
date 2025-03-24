package com.lamp.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lamp.common.entity.BaseEntity;
import com.lamp.infrastructure.xui.entity.XuiInbound;
import com.lamp.infrastructure.xui.entity.XuiResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Map;

@Data
@Slf4j
@EqualsAndHashCode(callSuper = true)
@TableName("lamp_sku")
public class LampSKU extends BaseEntity {
    private Long spuId;
    private BigDecimal skuPrice;
    private String skuAttribute;
    @TableField(exist = false)
    private Map<String, Object> attributeMap;

    public void parseAttribute() {
        ObjectMapper objectMapper = new ObjectMapper();
        if (skuAttribute != null) {
            try {
                this.attributeMap = objectMapper.readValue(skuAttribute, new TypeReference<Map<String, Object>>() {
                });
            } catch (IOException e) {
                log.error("反序列化异常:", e);
            }
        }
    }
}
