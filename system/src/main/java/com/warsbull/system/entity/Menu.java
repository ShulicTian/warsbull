/**
 * Copyright &copy; 2012-2016 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.warsbull.system.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.warsbull.commons.entity.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.util.List;

/**
 * 系统菜单Entity
 *
 * @author ShulicTian
 * @date 2020/01/15
 */
@Data
@Entity
@Table(name = "tsys_menu")
@EqualsAndHashCode(callSuper = true)
public class Menu extends BaseEntity {

    @OneToOne
    @JoinColumn(name = "parent_id", referencedColumnName = "id")
    private Menu parent;

    @Column(name = "parent_ids")
    private String parentIds;

    @Column(name = "name")
    private String name;

    @Column(name = "href")
    private String href;

    @Column(name = "sort")
    private Integer sort;

    @Column(name = "isShow")
    private String isShow;

    @Column(name = "permission")
    private String permission;

    public Menu() {
        super();
        this.sort = 30;
        this.isShow = "1";
    }

    public Menu(String id) {
        super(id);
    }

    public String getParentId() {
        return parent != null && parent.getId() != null ? parent.getId() : "0";
    }

    @JsonIgnore
    public static void sortList(List<Menu> list, List<Menu> sourcelist, String parentId, boolean cascade) {
        for (int i = 0; i < sourcelist.size(); i++) {
            Menu e = sourcelist.get(i);
            if (e.getParent() != null && e.getParent().getId() != null
                    && e.getParent().getId().equals(parentId)) {
                list.add(e);
                if (cascade) {
                    // 判断是否还有子节点, 有则继续获取子节点
                    for (int j = 0; j < sourcelist.size(); j++) {
                        Menu child = sourcelist.get(j);
                        if (child.getParent() != null && child.getParent().getId() != null
                                && child.getParent().getId().equals(e.getId())) {
                            sortList(list, sourcelist, e.getId(), true);
                            break;
                        }
                    }
                }
            }
        }
    }

    @JsonIgnore
    public static String getRootId() {
        return "1";
    }

    @Override
    public String toString() {
        return name;
    }
}