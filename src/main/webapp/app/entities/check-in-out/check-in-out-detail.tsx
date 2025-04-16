import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './check-in-out.reducer';
import { ICheckInOut } from 'app/shared/model/check-in-out.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ICheckInOutDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const CheckInOutDetail = (props: ICheckInOutDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { checkInOutEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          CheckInOut [<b>{checkInOutEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="checkInTime">Check In Time</span>
          </dt>
          <dd>
            {checkInOutEntity.checkInTime ? <TextFormat value={checkInOutEntity.checkInTime} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="checkInLat">Check In Lat</span>
          </dt>
          <dd>{checkInOutEntity.checkInLat}</dd>
          <dt>
            <span id="checkInLng">Check In Lng</span>
          </dt>
          <dd>{checkInOutEntity.checkInLng}</dd>
          <dt>
            <span id="checkOutTime">Check Out Time</span>
          </dt>
          <dd>
            {checkInOutEntity.checkOutTime ? (
              <TextFormat value={checkInOutEntity.checkOutTime} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="checkOutLat">Check Out Lat</span>
          </dt>
          <dd>{checkInOutEntity.checkOutLat}</dd>
          <dt>
            <span id="checkOutLng">Check Out Lng</span>
          </dt>
          <dd>{checkInOutEntity.checkOutLng}</dd>
          <dt>
            <span id="userId">User Id</span>
          </dt>
          <dd>{checkInOutEntity.userId}</dd>
          <dt>
            <span id="createdDate">Created Date</span>
          </dt>
          <dd>
            {checkInOutEntity.createdDate ? <TextFormat value={checkInOutEntity.createdDate} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="createdBy">Created By</span>
          </dt>
          <dd>{checkInOutEntity.createdBy}</dd>
          <dt>
            <span id="lastModifiedDate">Last Modified Date</span>
          </dt>
          <dd>
            {checkInOutEntity.lastModifiedDate ? (
              <TextFormat value={checkInOutEntity.lastModifiedDate} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="lastModifiedBy">Last Modified By</span>
          </dt>
          <dd>{checkInOutEntity.lastModifiedBy}</dd>
        </dl>
        <Button tag={Link} to="/check-in-out" replace color="info">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/check-in-out/${checkInOutEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ checkInOut }: IRootState) => ({
  checkInOutEntity: checkInOut.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(CheckInOutDetail);
