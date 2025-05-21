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
 * A LeaveRequest.
 */
@Entity
@Table(name = "leave_request")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@EntityListeners({AuditingEntityListener.class, EntityGenerator.class})
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class LeaveRequest extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "leave_date")
    private Instant leaveDate;

    @Column(name = "reason")
    private String reason;

    @Column(name = "status")
    private String status;

    @Column(name = "approved_user_id")
    private Long approvedUserId;

    @Column(name = "approved_name")
    private String approvedName;

    @Column(name = "reply")
    private String reply;

    // prettier-ignore
    @Override
    public String toString() {
        return "LeaveRequest{" +
            "id=" + getId() +
            ", leaveDate='" + getLeaveDate() + "'" +
            ", reason='" + getReason() + "'" +
            ", status='" + getStatus() + "'" +
            ", approvedUserId=" + getApprovedUserId() +
            ", approvedName='" + getApprovedName() + "'" +
            ", reply='" + getReply() + "'" +
            ", userId=" + getUserId() +
            ", createdDate='" + getCreatedDate() + "'" +
            ", createdBy='" + getCreatedBy() + "'" +
            ", lastModifiedDate='" + getLastModifiedDate() + "'" +
            ", lastModifiedBy='" + getLastModifiedBy() + "'" +
            "}";
    }
}
