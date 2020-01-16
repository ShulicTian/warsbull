package com.warsbull.system.dao;

import com.warsbull.system.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 系统菜单持久层
 *
 * @author ShulicTian
 * @date 2020/01/16
 */
@Repository
public interface MenuDao extends JpaRepository<Menu, String> {

}
