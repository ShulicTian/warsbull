package com.warsbull.commons.security;

import com.warsbull.commons.entity.Principal;
import com.warsbull.commons.utils.Encodes;
import com.warsbull.commons.utils.SpringContextHolder;
import com.warsbull.system.entity.Menu;
import com.warsbull.system.entity.Role;
import com.warsbull.system.entity.User;
import com.warsbull.system.service.UserService;
import com.warsbull.system.utils.UserUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;


/**
 * Shiro认证及授权
 *
 * @author ShulicTian
 * @date 2020/01/09
 */
@Component
public class SystemAuthorizingRealm extends AuthorizingRealm {

    private UserService userService;

    /**
     * 认证授权信息
     *
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        getService();
        Principal principal = (Principal) getAvailablePrincipal(principalCollection);
        User user = userService.getUserByLoginName(principal.getLoginName());
        if (user != null) {
            SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
            List<Menu> list = UserUtils.getMenuList();
            for (Menu menu : list) {
                if (StringUtils.isNotBlank(menu.getPermission())) {
                    // 添加基于Permission的权限信息
                    for (String permission : StringUtils.split(menu.getPermission(), ",")) {
                        info.addStringPermission(permission);
                    }
                }
            }
            // 添加用户权限
            info.addStringPermission("user");
            // 添加用户角色信息
            for (Role role : user.getRoleList()) {
                info.addRole(role.getRoleCode());
            }
            // 更新登录IP和时间
            userService.save(user);
            // 记录登录日志
//            LogUtils.saveLog(Servlets.getRequest(), "系统登录");
            return info;
        }
        return null;
    }

    /**
     * 认证用户信息
     *
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        getService();
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;

        // 校验用户名密码
        User user = UserUtils.getByLoginName(token.getUsername());
        if (user != null) {
            if ("1".equals(user.getLoginFlag())) {
                throw new AuthenticationException("msg:该已帐号禁止登录.");
            }
            byte[] salt = Encodes.decodeHex(user.getPassword().substring(0, 16));
            return new SimpleAuthenticationInfo(new Principal(user),
                    user.getPassword().substring(16), ByteSource.Util.bytes(salt), getName());
        }
        return null;
    }

    /**
     * 注入对应service
     */
    public void getService() {
        if (userService == null) {
            userService = SpringContextHolder.getBean(UserService.class);
        }
    }

    /**
     * 设置密码解析方式
     */
    @PostConstruct
    public void initCredentialsMatcher() {
        HashedCredentialsMatcher matcher = new HashedCredentialsMatcher("SHA-1");
        matcher.setHashIterations(1024);
        setCredentialsMatcher(matcher);
    }
}
