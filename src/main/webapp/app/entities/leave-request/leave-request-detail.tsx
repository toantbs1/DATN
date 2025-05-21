import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './leave-request.reducer';
import { ILeaveRequest } from 'app/shared/model/leave-request.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ILeaveRequestDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const LeaveRequestDetail = (props: ILeaveRequestDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { leaveRequestEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          LeaveRequest [<b>{leaveRequestEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="leaveDate">Leave Date</span>
          </dt>
          <dd>
            {leaveRequestEntity.leaveDate ? <TextFormat value={leaveRequestEntity.leaveDate} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="reason">Reason</span>
          </dt>
          <dd>{leaveRequestEntity.reason}</dd>
          <dt>
            <span id="status">Status</span>
          </dt>
          <dd>{leaveRequestEntity.status}</dd>
          <dt>
            <span id="approvedUserId">Approved User Id</span>
          </dt>
          <dd>{leaveRequestEntity.approvedUserId}</dd>
          <dt>
            <span id="approvedName">Approved Name</span>
          </dt>
          <dd>{leaveRequestEntity.approvedName}</dd>
          <dt>
            <span id="reply">Reply</span>
          </dt>
          <dd>{leaveRequestEntity.reply}</dd>
          <dt>
            <span id="userId">User Id</span>
          </dt>
          <dd>{leaveRequestEntity.userId}</dd>
          <dt>
            <span id="createdDate">Created Date</span>
          </dt>
          <dd>
            {leaveRequestEntity.createdDate ? (
              <TextFormat value={leaveRequestEntity.createdDate} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="createdBy">Created By</span>
          </dt>
          <dd>{leaveRequestEntity.createdBy}</dd>
          <dt>
            <span id="lastModifiedDate">Last Modified Date</span>
          </dt>
          <dd>
            {leaveRequestEntity.lastModifiedDate ? (
              <TextFormat value={leaveRequestEntity.lastModifiedDate} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="lastModifiedBy">Last Modified By</span>
          </dt>
          <dd>{leaveRequestEntity.lastModifiedBy}</dd>
        </dl>
        <Button tag={Link} to="/leave-request" replace color="info">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/leave-request/${leaveRequestEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ leaveRequest }: IRootState) => ({
  leaveRequestEntity: leaveRequest.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(LeaveRequestDetail);
