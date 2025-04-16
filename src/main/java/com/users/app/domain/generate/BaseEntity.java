package com.users.app.domain.generate;

import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.time.Instant;

@Data
@MappedSuperclass
@SuperBuilder
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class BaseEntity {

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "created_date", updatable = false)
    private Instant createdDate;

    @Column(name = "created_by", length = 50, updatable = false)
    private String createdBy;

    @Column(name = "last_modified_date")
    private Instant lastModifiedDate;

    @Column(name = "last_modified_by", length = 50)
    private String lastModifiedBy;
}
