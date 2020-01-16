package com.warsbull.system.service.impl;

import com.warsbull.system.service.MenuService;
import com.warsbull.system.dao.MenuDao;
import com.warsbull.system.entity.Menu;
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
