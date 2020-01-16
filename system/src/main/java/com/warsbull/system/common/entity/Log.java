/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.warsbull.system.common.entity;

import com.warsbull.system.common.utils.StringUtils;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;
import java.util.Map;

/**
 * 日志Entity
 *
 * @author ShulicTian
 * @date 2020/01/16
 */
@Data
@Entity
@Table(name = "tsys_log")
@EqualsAndHashCode(callSuper = true)
public class Log extends BaseEntity {

    @Column(name = "type")
    private String type;

    @Column(name = "title")
    private String title;

    @Column(name = "remote_addr")
    private String remoteAddr;

    @Column(name = "request_uri")
    private String requestUri;

    @Column(name = "method")
    private String method;

    @Column(name = "params")
    private String params;

    @Column(name = "user_agent")
    private String userAgent;

    @Column(name = "exception")
    private String exception;

    @Transient
    private Date beginDate;
    @Transient
    private Date endDate;

    public static final String TYPE_ACCESS = "1";
    public static final String TYPE_EXCEPTION = "2";

    public Log() {
        super();
    }

    public Log(String id) {
        super(id);
    }


    /**
     * 设置请求参数
     *
     * @param paramMap
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    public void setParams(Map paramMap) {
        if (paramMap == null) {
            return;
        }
        StringBuilder params = new StringBuilder();
        for (Map.Entry<String, String[]> param : ((Map<String, String[]>) paramMap).entrySet()) {
            params.append(("".equals(params.toString()) ? "" : "&") + param.getKey() + "=");
            String paramValue = (param.getValue() != null && param.getValue().length > 0 ? param.getValue()[0] : "");
            params.append(StringUtils.abbr(StringUtils.endsWithIgnoreCase(param.getKey(), "password") ? "" : paramValue, 100));
        }
        this.params = params.toString();
    }

    @Override
    public String toString() {
        return ReflectionToStringBuilder.toString(this);
    }
}