package com.users.app.domain;

import com.users.app.domain.generate.BaseEntity;
import com.users.app.domain.generate.EntityGenerator;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

import java.io.Serializable;
import java.time.Instant;

/**
 * A TaskHistory.
 */
@Entity
@Table(name = "task_history")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@EntityListeners({AuditingEntityListener.class, EntityGenerator.class})
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class TaskHistory extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "task_id")
    private Long taskId;

    @Column(name = "action_code")
    private String actionCode;

    @Column(name = "param")
    private String param;

    @Override
    public String toString() {
        return "TaskHistory{" +
            "id=" + getId() +
            ", taskId=" + getTaskId() +
            ", actionCode='" + getActionCode() + "'" +
            ", param='" + getParam() + "'" +
            ", userId=" + getUserId() +
            ", createdDate='" + getCreatedDate() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", lastModifiedDate='" + getLastModifiedDate() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            "}";
    }
}
