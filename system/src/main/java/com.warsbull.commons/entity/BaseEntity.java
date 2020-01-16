package com.warsbull.commons.entity;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Entity基类
 *
 * @author ShulicTian
 * @date 2020/01/13
 */
@Data
@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
public class BaseEntity implements Serializable {

    @Id
    @GenericGenerator(name = "idGenerator", strategy = "uuid")
    @GeneratedValue(generator = "idGenerator")
    protected String id;

    @CreatedBy
    @Column(name = "create_by", updatable = false, length = 64)
    protected String createBy;

    @CreatedDate
    @Column(name = "create_date", updatable = false)
    protected Date createDate;

    @LastModifiedBy
    @Column(name = "update_by", length = 64)
    protected String updateBy;

    @LastModifiedDate
    @Column(name = "update_date")
    protected Date updateDate;

    @Column(name = "remarks")
    protected String remarks;

    @Column(name = "del_flag")
    protected String delFlag;

    public BaseEntity() {
        this.delFlag = "0";
    }

    public BaseEntity(String id) {
        this.id = id;
        this.delFlag = "0";
    }
}
