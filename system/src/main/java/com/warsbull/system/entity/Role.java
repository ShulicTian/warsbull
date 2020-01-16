package com.warsbull.system.entity;

import com.warsbull.commons.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.List;

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

    @OneToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "tsys_role_menu",
            joinColumns = {@JoinColumn(name = "role_id")},
            inverseJoinColumns = {@JoinColumn(name = "menu_id")})
    private List<Menu> menuList;

    @Transient
    private User user;

}
