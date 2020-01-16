package com.warsbull.commons.dao;

import com.warsbull.commons.entity.Log;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * 日志DAO接口
 * @author ShulicTian
 * @date 2020/01/16
 */
public interface LogDao extends JpaRepository<Log, String> {

}
