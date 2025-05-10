import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './task.reducer';
import { ITask } from 'app/shared/model/task.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ITaskDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const TaskDetail = (props: ITaskDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { taskEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          Task [<b>{taskEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="name">Name</span>
          </dt>
          <dd>{taskEntity.name}</dd>
          <dt>
            <span id="description">Description</span>
          </dt>
          <dd>{taskEntity.description}</dd>
          <dt>
            <span id="startTime">Start Time</span>
          </dt>
          <dd>{taskEntity.startTime ? <TextFormat value={taskEntity.startTime} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="latitude">Latitude</span>
          </dt>
          <dd>{taskEntity.latitude}</dd>
          <dt>
            <span id="longitude">Longitude</span>
          </dt>
          <dd>{taskEntity.longitude}</dd>
          <dt>
            <span id="altitude">Altitude</span>
          </dt>
          <dd>{taskEntity.altitude}</dd>
          <dt>
            <span id="status">Status</span>
          </dt>
          <dd>{taskEntity.status}</dd>
          <dt>
            <span id="userId">User Id</span>
          </dt>
          <dd>{taskEntity.userId}</dd>
          <dt>
            <span id="createdDate">Created Date</span>
          </dt>
          <dd>{taskEntity.createdDate ? <TextFormat value={taskEntity.createdDate} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="createdBy">Created By</span>
          </dt>
          <dd>{taskEntity.createdBy}</dd>
          <dt>
            <span id="lastModifiedDate">Last Modified Date</span>
          </dt>
          <dd>
            {taskEntity.lastModifiedDate ? <TextFormat value={taskEntity.lastModifiedDate} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="lastModifiedBy">Last Modified By</span>
          </dt>
          <dd>{taskEntity.lastModifiedBy}</dd>
        </dl>
        <Button tag={Link} to="/task" replace color="info">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/task/${taskEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ task }: IRootState) => ({
  taskEntity: task.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(TaskDetail);
