package com.warsbull.system.common.service;

import com.warsbull.system.sys.utils.UserUtils;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * SpringDataJpa 审计（即初始化createBy及updateBy）
 *
 * @author ShulicTian
 */
@Component
public class AuditorAwareImpl implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        return Optional.of(UserUtils.getCurrentUserId());
    }

}
