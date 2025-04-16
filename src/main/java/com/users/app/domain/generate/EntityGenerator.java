package com.users.app.domain.generate;

import com.google.common.base.Strings;
import com.users.app.security.SecurityUtils;
import com.users.app.service.dto.UserInfoDetail;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Component;

import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.time.Instant;
import java.util.Optional;

@Component
public class EntityGenerator {

    @PrePersist
    private void beforeCreated(BaseEntity entity) {
        Optional<UserInfoDetail> userInfoDetails = Optional.ofNullable(SecurityUtils.getInfoCurrentUserLogin());
        Instant now = Instant.now();
        Optional.of(userInfoDetails).ifPresentOrElse(
            user -> {
                entity.setUserId(user.get().getId());
                entity.setCreatedBy(Strings.isNullOrEmpty(user.get().getLogin()) ? null : user.get().getLogin());
                entity.setLastModifiedBy(Strings.isNullOrEmpty(user.get().getLogin()) ? null : user.get().getLogin());
            },
            () -> {
                entity.setUserId(null);
                entity.setCreatedBy("anonymous");
                entity.setLastModifiedBy("anonymous");
            }
        );
        entity.setCreatedDate(now);
        entity.setLastModifiedDate(now);
    }

    @PreUpdate
    private void beforeUpdate(BaseEntity entity) {
        Optional<String> userLogin = SecurityUtils.getCurrentUserLogin();
        entity.setLastModifiedBy(userLogin.orElse(null));
        entity.setLastModifiedDate(Instant.now());
    }
}
