package com.warsbull.system.sys.entity;

import com.warsbull.system.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

/**
 * 系统角色Entity
 *
 * @author ShulicTian
 * @date 2020/01/15
 */
@Data
@Entity
@Table(name = "tsys_role")
@EqualsAndHashCode(callSuper = true)
public class Role extends BaseEntity {

    @Column(name = "name")
    private String name;

    @Column(name = "role_code")
    private String roleCode;

    @Column(name = "role_type")
    private String roleType;

    @Column(name = "useable")
    private String useable;

    @Transient
    private User user;

}
