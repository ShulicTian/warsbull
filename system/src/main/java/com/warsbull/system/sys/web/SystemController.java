package com.warsbull.system.sys.web;

import com.warsbull.system.common.entity.ResponseEntity;
import com.warsbull.system.common.utils.StringUtils;
import com.warsbull.system.common.web.BaseController;
import com.warsbull.system.sys.entity.Role;
import com.warsbull.system.sys.entity.User;
import com.warsbull.system.sys.service.RoleService;
import com.warsbull.system.sys.service.UserService;
import com.warsbull.system.sys.utils.UserUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 系统基础信息Controller
 *
 * @author ShulicTian
 * @date 2020/01/10
 */
@RestController
@RequestMapping("/sys/system")
public class SystemController extends BaseController {

    @Autowired
    private RoleService roleService;

    @Autowired
    private UserService userService;

    /**
     * 登录
     *
     * @param userName
     * @param password
     * @return
     */
    @RequestMapping("login")
    public ResponseEntity<User> login(String userName, String password) {
        Subject currentUser = SecurityUtils.getSubject();
        ResponseEntity<User> result = new ResponseEntity();

        if (StringUtils.isEmpty(userName) || StringUtils.isEmpty(password)) {
            result.setDesc("账号密码不能为空");
            result.setCode(ResponseEntity.FAILURE_CODE);
        }

        if (!currentUser.isAuthenticated()) {

            UsernamePasswordToken token = new UsernamePasswordToken(userName, password.toCharArray());
            try {
                currentUser.login(token);
                User user = UserUtils.getByLoginName(userName);
                List<Role> roleList = roleService.getRoleByUser(user);
                user.setRoleList(roleList);

                result.setDesc("登录成功");
                result.setData(user);
                result.setCode(ResponseEntity.SUCCESS_CODE);
            } catch (UnknownAccountException ex) {
                logger.debug("账号错误");
                result.setDesc("账号错误");
                result.setCode(ResponseEntity.FAILURE_CODE);
            } catch (IncorrectCredentialsException ex) {
                logger.debug("密码错误");
                result.setDesc("密码错误");
                result.setCode(ResponseEntity.FAILURE_CODE);
            } catch (LockedAccountException ex) {
                logger.debug("账号已被锁定，请与系统管理员联系");
                result.setDesc("账号已被锁定，请与系统管理员联系");
                result.setCode(ResponseEntity.FAILURE_CODE);
            } catch (AuthenticationException ex) {
                ex.printStackTrace();
                logger.debug("您没有授权!");
                result.setDesc("您没有授权");
                result.setCode(ResponseEntity.FAILURE_CODE);
            }
        } else {
            result.setDesc("请勿重复登入");
            result.setData(UserUtils.getByLoginName(userName));
            result.setCode(ResponseEntity.SUCCESS_CODE);
        }

        return result;
    }

    /**
     * 登出
     *
     * @return
     */
    @RequestMapping(value = "logout")
    public ResponseEntity<String> logout() {

        ResponseEntity<String> result = new ResponseEntity();
        Subject currentUser = SecurityUtils.getSubject();

        if (currentUser.isAuthenticated()) {
            currentUser.logout();
            result.setDesc("登出成功");
            result.setData("");
            result.setCode(ResponseEntity.SUCCESS_CODE);
        } else {
            result.setDesc("您还未登录");
            result.setData("");
            result.setCode(ResponseEntity.SUCCESS_CODE);
        }

        return result;
    }

    /**
     * 注册用户、更改用户信息
     *
     * @return
     */
    @RequestMapping(value = "saveUser")
    public ResponseEntity<String> saveUser(User user) {

        ResponseEntity<String> result = new ResponseEntity();
        try {
            userService.save(user);
            result.setCode(ResponseEntity.SUCCESS_CODE);
        } catch (Exception e) {
            e.printStackTrace();
            result.setCode(ResponseEntity.FAILURE_CODE);
            logger.info("用户：" + user.getLoginName() + user.getName() + "保存异常");
        }
        return result;
    }

}
