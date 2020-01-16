package com.warsbull.system.sys.utils;

import com.google.common.collect.Lists;
import com.warsbull.system.common.entity.Principal;
import com.warsbull.system.common.utils.CacheUtils;
import com.warsbull.system.common.utils.SpringContextHolder;
import com.warsbull.system.sys.entity.Menu;
import com.warsbull.system.sys.entity.Role;
import com.warsbull.system.sys.entity.User;
import com.warsbull.system.sys.service.MenuService;
import com.warsbull.system.sys.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.UnavailableSecurityManagerException;
import org.apache.shiro.session.InvalidSessionException;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 用户相关工具类
 *
 * @author ShulicTian
 * @date 2020/01/13
 */
public class UserUtils {

    public static final String USER_CACHE = "userCache";
    public static final String CACHE_MENU_LIST = "menuList";
    public static final String USER_CACHE_ID_ = "id_";
    public static final String USER_CACHE_LOGIN_NAME_ = "loginName_";
    private static Logger logger = LoggerFactory.getLogger(UserUtils.class);
    private static UserService userService = SpringContextHolder.getBean(UserService.class);
    private static MenuService menuService = SpringContextHolder.getBean(MenuService.class);

    /**
     * 获取当前用户授权菜单
     *
     * @return
     */
    public static List<Menu> getMenuList() {
        @SuppressWarnings("unchecked")
        List<Menu> menuList = (List<Menu>) getCache(CACHE_MENU_LIST);
        if (menuList == null) {
            User user = getCurrentUser();
            if (user.isAdmin()) {
                menuList = menuService.findAllList();
            } else {
                List<Menu> finalMenuList = Lists.newArrayList();
                List<Role> roleList = getCurrentUser().getRoleList();
                roleList.forEach(role -> finalMenuList.addAll(role.getMenuList()));
                menuList = finalMenuList;
            }
            putCache(CACHE_MENU_LIST, menuList);
        }
        logger.debug("【UserUtils】获取菜单列表");
        return menuList;
    }

    /**
     * 根据ID获取用户
     *
     * @param id
     * @return 取不到返回null
     */
    public static User get(String id) {
        User user = (User) CacheUtils.get(USER_CACHE, USER_CACHE_ID_ + id);
        if (user == null) {
            user = userService.get(id);
            if (user == null) {
                return null;
            }
//            user.setRoleList(roleDao.findList(new Role(user)));
            CacheUtils.put(USER_CACHE, USER_CACHE_ID_ + user.getId(), user);
//            CacheUtils.put(USER_CACHE, USER_CACHE_LOGIN_NAME_ + user.getLoginName(), user);
        }
        return user;
    }

    public static User getByLoginName(String loginName) {
        User user = (User) CacheUtils.get(USER_CACHE, USER_CACHE_LOGIN_NAME_ + loginName);
        if (user == null) {
            user = userService.getUserByLoginName(loginName);
            if (user == null) {
                return null;
            }
            CacheUtils.put(USER_CACHE, USER_CACHE_LOGIN_NAME_ + loginName, user);
        }
        return user;
    }

    public static User getCurrentUser() {
        Principal principal = getPrincipal();
        if (principal != null) {
            User user = get(principal.getId());
            if (user != null) {
                return user;
            }
            return new User();
        }
        return new User();
    }

    public static String getCurrentUserId() {
        User user = getCurrentUser();
        if (user != null) {
            return user.getId();
        }
        return "";
    }

    /**
     * 获取授权主要对象
     */
    public static Subject getSubject() {
        return SecurityUtils.getSubject();
    }

    /**
     * 获取当前登录者对象
     */
    public static Principal getPrincipal() {
        try {
            Subject subject = SecurityUtils.getSubject();
            Principal principal = (Principal) subject.getPrincipal();
            if (principal != null) {
                return principal;
            }
        } catch (UnavailableSecurityManagerException e) {
            e.printStackTrace();
        } catch (InvalidSessionException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static Session getSession() {
        try {
            Subject subject = SecurityUtils.getSubject();
            Session session = subject.getSession(false);
            if (session == null) {
                session = subject.getSession();
            }
            if (session != null) {
                return session;
            }
        } catch (InvalidSessionException e) {

        }
        return null;
    }

    // ============== User Cache ==============

    public static Object getCache(String key) {
        return getCache(key, null);
    }

    public static Object getCache(String key, Object defaultValue) {
        Object obj = getSession().getAttribute(key);
        return obj == null ? defaultValue : obj;
    }

    public static void putCache(String key, Object value) {
        getSession().setAttribute(key, value);
    }

    public static void removeCache(String key) {
        getSession().removeAttribute(key);
    }
}
