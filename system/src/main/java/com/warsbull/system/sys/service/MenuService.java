package com.warsbull.system.sys.service;

import com.warsbull.system.sys.entity.Menu;

import java.util.List;

/**
 * 系统菜单相关接口
 *
 * @author ShulicTian
 * @date 2020/01/16
 */
public interface MenuService {

    /**
     * 获取所有菜单
     * @return
     */
    List<Menu> findAllList();

}
