package com.warsbull.system.sys.entity;

import com.warsbull.system.common.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.List;

/**
 * 系统用户Entity
 *
 * @author ShulicTian
 * @date 2020/01/13
 */
@Data
@Entity
@Table(name = "tsys_user")
@EqualsAndHashCode(callSuper = true)
public class User extends BaseEntity {

    @Column(name = "login_name")
    private String loginName;

    @Column(name = "password")
    private String password;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "mobile")
    private String mobile;

    @Column(name = "photo")
    private String photo;

    @Column(name = "login_ip")
    private String loginIp;

    @Column(name = "login_date")
    private String loginDate;

    @Column(name = "login_flag")
    private String loginFlag;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "tsys_user_role",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "role_id")})
    private List<Role> roleList;

    public boolean isAdmin() {
        return isAdmin(this.id);
    }

    public static boolean isAdmin(String id) {
        return id != null && "1".equals(id);
    }
}
