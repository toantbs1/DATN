import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './task-history.reducer';
import { ITaskHistory } from 'app/shared/model/task-history.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ITaskHistoryDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const TaskHistoryDetail = (props: ITaskHistoryDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { taskHistoryEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          TaskHistory [<b>{taskHistoryEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="taskId">Task Id</span>
          </dt>
          <dd>{taskHistoryEntity.taskId}</dd>
          <dt>
            <span id="actionCode">Action Code</span>
          </dt>
          <dd>{taskHistoryEntity.actionCode}</dd>
          <dt>
            <span id="param">Param</span>
          </dt>
          <dd>{taskHistoryEntity.param}</dd>
          <dt>
            <span id="userId">User Id</span>
          </dt>
          <dd>{taskHistoryEntity.userId}</dd>
          <dt>
            <span id="createdDate">Created Date</span>
          </dt>
          <dd>
            {taskHistoryEntity.createdDate ? (
              <TextFormat value={taskHistoryEntity.createdDate} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="createdBy">Created By</span>
          </dt>
          <dd>{taskHistoryEntity.createdBy}</dd>
          <dt>
            <span id="lastModifiedDate">Last Modified Date</span>
          </dt>
          <dd>
            {taskHistoryEntity.lastModifiedDate ? (
              <TextFormat value={taskHistoryEntity.lastModifiedDate} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="lastModifiedBy">Last Modified By</span>
          </dt>
          <dd>{taskHistoryEntity.lastModifiedBy}</dd>
        </dl>
        <Button tag={Link} to="/task-history" replace color="info">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/task-history/${taskHistoryEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ taskHistory }: IRootState) => ({
  taskHistoryEntity: taskHistory.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(TaskHistoryDetail);
