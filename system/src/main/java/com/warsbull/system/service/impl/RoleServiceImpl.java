package com.warsbull.system.service.impl;

import com.warsbull.system.service.RoleService;
import com.warsbull.system.entity.Role;
import com.warsbull.system.entity.User;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 用户角色相关操作
 *
 * @author ShulicTian
 * @date 2020/01/13
 */
@Service
public class RoleServiceImpl implements RoleService {
    @Override
    public List<Role> getRoleByUser(User user) {
        return null;
    }
}
