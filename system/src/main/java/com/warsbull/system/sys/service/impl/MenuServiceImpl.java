package com.warsbull.system.sys.service.impl;

import com.warsbull.system.sys.dao.MenuDao;
import com.warsbull.system.sys.entity.Menu;
import com.warsbull.system.sys.entity.Role;
import com.warsbull.system.sys.entity.User;
import com.warsbull.system.sys.service.MenuService;
import com.warsbull.system.sys.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 系统菜单相关操作
 *
 * @author ShulicTian
 * @date 2020/01/16
 */
@Service
public class MenuServiceImpl implements MenuService {

    @Autowired
    private MenuDao menuDao;

    @Override
    public List<Menu> findAllList() {
        return menuDao.findAll();
    }
}
