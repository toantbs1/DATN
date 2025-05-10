import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './assign-task.reducer';
import { IAssignTask } from 'app/shared/model/assign-task.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IAssignTaskDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const AssignTaskDetail = (props: IAssignTaskDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { assignTaskEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          AssignTask [<b>{assignTaskEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="taskId">Task Id</span>
          </dt>
          <dd>{assignTaskEntity.taskId}</dd>
          <dt>
            <span id="assigneeId">Assignee Id</span>
          </dt>
          <dd>{assignTaskEntity.assigneeId}</dd>
          <dt>
            <span id="assignAt">Assign At</span>
          </dt>
          <dd>
            {assignTaskEntity.assignAt ? <TextFormat value={assignTaskEntity.assignAt} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="userId">User Id</span>
          </dt>
          <dd>{assignTaskEntity.userId}</dd>
          <dt>
            <span id="createdDate">Created Date</span>
          </dt>
          <dd>
            {assignTaskEntity.createdDate ? <TextFormat value={assignTaskEntity.createdDate} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="createdBy">Created By</span>
          </dt>
          <dd>{assignTaskEntity.createdBy}</dd>
          <dt>
            <span id="lastModifiedDate">Last Modified Date</span>
          </dt>
          <dd>
            {assignTaskEntity.lastModifiedDate ? (
              <TextFormat value={assignTaskEntity.lastModifiedDate} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="lastModifiedBy">Last Modified By</span>
          </dt>
          <dd>{assignTaskEntity.lastModifiedBy}</dd>
        </dl>
        <Button tag={Link} to="/assign-task" replace color="info">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/assign-task/${assignTaskEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ assignTask }: IRootState) => ({
  assignTaskEntity: assignTask.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(AssignTaskDetail);
