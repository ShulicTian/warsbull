package com.warsbull.blog.controller;

import com.alibaba.fastjson.JSONObject;
import com.warsbull.system.sys.dao.UserDao;
import com.warsbull.system.sys.entity.User;
import com.warsbull.system.sys.utils.UserUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ThreadContext;
import org.apache.shiro.web.subject.WebSubject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.Resource;

//import org.apache.shiro.SecurityUtils;

@RunWith(SpringRunner.class)
@SpringBootTest
public class InitTest {

    @Resource
    private UserDao userDao;
    @Resource
    private org.apache.shiro.mgt.SecurityManager securityManager;
    @Resource
    private WebApplicationContext webApplicationContext;

    private Subject subject;
    private MockMvc mockMvc;
    private MockHttpServletRequest mockHttpServletRequest;
    private MockHttpServletResponse mockHttpServletResponse;

    @Before
    public void before() {
        mockHttpServletRequest = new MockHttpServletRequest(webApplicationContext.getServletContext());
        mockHttpServletResponse = new MockHttpServletResponse();
        MockHttpSession mockHttpSession = new MockHttpSession(webApplicationContext.getServletContext());
        mockHttpServletRequest.setSession(mockHttpSession);
        SecurityUtils.setSecurityManager(securityManager);
        mockMvc = MockMvcBuilders
                .webAppContextSetup(webApplicationContext)
                .build();
        subject = new WebSubject.Builder(mockHttpServletRequest, mockHttpServletResponse)
                .buildWebSubject();
        if (!subject.isAuthenticated()) {
            UsernamePasswordToken token = new UsernamePasswordToken("admin", "123456");
            subject.login(token);
            ThreadContext.bind(subject);
        }
    }

    @Test
    public void save() {
        User user = new User();
        user.setLoginName("test");
        user.setName("test");
        user.setPassword("123456");
        userDao.save(user);
    }

    @Test
    public void getOne() {
        System.out.println(JSONObject.toJSONString(userDao.findById(UserUtils.getCurrentUserId()).get()));
    }

}