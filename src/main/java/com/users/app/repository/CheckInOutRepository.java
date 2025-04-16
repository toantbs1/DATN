package com.users.app.repository;

import com.users.app.domain.CheckInOut;

import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.sql.Time;
import java.time.Instant;
import java.util.List;
import java.util.Optional;

/**
 * Spring Data  repository for the CheckInOut entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CheckInOutRepository extends JpaRepository<CheckInOut, Long> {

    @Query(value = "select c from CheckInOut c where c.authorizerId =:authorizerId and DATE(c.createdDate) = DATE(:inputDate)")
    Optional<CheckInOut> findByAuthorizerIdAndCreatedDate(@Param("authorizerId") Long authorizerId, @Param("inputDate") Instant inputDate);

    @Query(value = "select c from CheckInOut c where " +
        "c.authorizerId =:authorizerId and " +
        "(:fromDate is null or DATE(c.createdDate) >= DATE(:fromDate)) and " +
        "(:toDate is null or DATE(c.createdDate) <= DATE(:toDate)) and " +
        "(TIME(c.checkInTime) >:timeStartWork or TIME(c.checkOutTime) <:timeEndWork)"
    )
    List<CheckInOut> findByAuthorizerIdAndCreatedDateBetween(
        @Param("authorizerId") Long authorizerId,
        @Param("fromDate") Instant fromDate,
        @Param("toDate") Instant toDate,
        @Param("timeStartWork") Time timeStartWork,
        @Param("timeEndWork") Time timeEndWork
    );

    @Query(value = "select c from CheckInOut c where " +
        "c.authorizerId in :authorizerIds and " +
        "(:fromDate is null or DATE(c.createdDate) >= DATE(:fromDate)) and " +
        "(:toDate is null or DATE(c.createdDate) <= DATE(:toDate)) and " +
        "(TIME(c.checkInTime) >:timeStartWork or TIME(c.checkOutTime) <:timeEndWork)"
    )
    List<CheckInOut> findAllByAuthorizerIdAndCreatedDateBetween(
        @Param("authorizerIds") List<Long> authorizerIds,
        @Param("fromDate") Instant fromDate,
        @Param("toDate") Instant toDate,
        @Param("timeStartWork") Time timeStartWork,
        @Param("timeEndWork") Time timeEndWork
    );
}
