package com.warsbull.system.sys.dao;

import com.warsbull.system.sys.entity.Menu;
import com.warsbull.system.sys.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 系统菜单持久层
 *
 * @author ShulicTian
 * @date 2020/01/16
 */
@Repository
public interface MenuDao extends JpaRepository<Menu, String> {

}
