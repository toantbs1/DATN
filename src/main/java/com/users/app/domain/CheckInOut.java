package com.users.app.domain;

import com.users.app.domain.generate.BaseEntity;
import com.users.app.domain.generate.EntityGenerator;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.Instant;

/**
 * A CheckInOut.
 */
@Data
@Entity
@Table(name = "check_in_out")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@EntityListeners({AuditingEntityListener.class, EntityGenerator.class})
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class CheckInOut extends BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "check_in_time")
    private Instant checkInTime;

    @Column(name = "check_in_lat", precision = 15, scale = 10)
    private BigDecimal checkInLat;

    @Column(name = "check_in_lng", precision = 15, scale = 10)
    private BigDecimal checkInLng;

    @Column(name = "check_out_time")
    private Instant checkOutTime;

    @Column(name = "check_out_lat", precision = 15, scale = 10)
    private BigDecimal checkOutLat;

    @Column(name = "check_out_lng", precision = 15, scale = 10)
    private BigDecimal checkOutLng;

    @Column(name = "authorizer_id")
    private Long authorizerId;

    @Column(name = "check_in_alt", precision = 15, scale = 10)
    private BigDecimal checkInAlt;

    @Column(name = "check_out_alt", precision = 15, scale = 10)
    private BigDecimal checkOutAlt;
}
