package com.warsbull.system.sys.service.impl;

import com.warsbull.system.common.utils.Digests;
import com.warsbull.system.common.utils.Encodes;
import com.warsbull.system.sys.dao.UserDao;
import com.warsbull.system.sys.entity.User;
import com.warsbull.system.sys.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 用户相关操作
 *
 * @author ShulicTian
 * @date 2020/01/15
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public User getUserByLoginName(String userName) {
        return userDao.getByLoginName(userName);
    }

    @Override
    public User get(String id) {
        return userDao.findById(id).get();
    }

    @Override
    public void save(User user) {
        String plain = Encodes.unescapeHtml(user.getPassword());
        byte[] salt = Digests.generateSalt(8);
        byte[] hashPassword = Digests.sha1(plain.getBytes(), salt, 1024);
        user.setPassword(Encodes.encodeHex(salt) + Encodes.encodeHex(hashPassword));
        userDao.save(user);
    }
}
