import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { getEntity, updateEntity, createEntity, reset } from './task.reducer';
import { ITask } from 'app/shared/model/task.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface ITaskUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const TaskUpdate = (props: ITaskUpdateProps) => {
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { taskEntity, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/task' + props.location.search);
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    values.startTime = convertDateTimeToServer(values.startTime);
    values.createdDate = convertDateTimeToServer(values.createdDate);
    values.lastModifiedDate = convertDateTimeToServer(values.lastModifiedDate);

    if (errors.length === 0) {
      const entity = {
        ...taskEntity,
        ...values,
      };

      if (isNew) {
        props.createEntity(entity);
      } else {
        props.updateEntity(entity);
      }
    }
  };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="datnApp.task.home.createOrEditLabel">Create or edit a Task</h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : taskEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="task-id">ID</Label>
                  <AvInput id="task-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="nameLabel" for="task-name">
                  Name
                </Label>
                <AvField id="task-name" type="text" name="name" />
              </AvGroup>
              <AvGroup>
                <Label id="descriptionLabel" for="task-description">
                  Description
                </Label>
                <AvField id="task-description" type="text" name="description" />
              </AvGroup>
              <AvGroup>
                <Label id="startTimeLabel" for="task-startTime">
                  Start Time
                </Label>
                <AvInput
                  id="task-startTime"
                  type="datetime-local"
                  className="form-control"
                  name="startTime"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.taskEntity.startTime)}
                />
              </AvGroup>
              <AvGroup>
                <Label id="latitudeLabel" for="task-latitude">
                  Latitude
                </Label>
                <AvField id="task-latitude" type="text" name="latitude" />
              </AvGroup>
              <AvGroup>
                <Label id="longitudeLabel" for="task-longitude">
                  Longitude
                </Label>
                <AvField id="task-longitude" type="text" name="longitude" />
              </AvGroup>
              <AvGroup>
                <Label id="altitudeLabel" for="task-altitude">
                  Altitude
                </Label>
                <AvField id="task-altitude" type="text" name="altitude" />
              </AvGroup>
              <AvGroup>
                <Label id="statusLabel" for="task-status">
                  Status
                </Label>
                <AvField id="task-status" type="text" name="status" />
              </AvGroup>
              <AvGroup>
                <Label id="userIdLabel" for="task-userId">
                  User Id
                </Label>
                <AvField id="task-userId" type="string" className="form-control" name="userId" />
              </AvGroup>
              <AvGroup>
                <Label id="createdDateLabel" for="task-createdDate">
                  Created Date
                </Label>
                <AvInput
                  id="task-createdDate"
                  type="datetime-local"
                  className="form-control"
                  name="createdDate"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.taskEntity.createdDate)}
                />
              </AvGroup>
              <AvGroup>
                <Label id="createdByLabel" for="task-createdBy">
                  Created By
                </Label>
                <AvField id="task-createdBy" type="text" name="createdBy" />
              </AvGroup>
              <AvGroup>
                <Label id="lastModifiedDateLabel" for="task-lastModifiedDate">
                  Last Modified Date
                </Label>
                <AvInput
                  id="task-lastModifiedDate"
                  type="datetime-local"
                  className="form-control"
                  name="lastModifiedDate"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.taskEntity.lastModifiedDate)}
                />
              </AvGroup>
              <AvGroup>
                <Label id="lastModifiedByLabel" for="task-lastModifiedBy">
                  Last Modified By
                </Label>
                <AvField id="task-lastModifiedBy" type="text" name="lastModifiedBy" />
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/task" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">Back</span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp; Save
              </Button>
            </AvForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

const mapStateToProps = (storeState: IRootState) => ({
  taskEntity: storeState.task.entity,
  loading: storeState.task.loading,
  updating: storeState.task.updating,
  updateSuccess: storeState.task.updateSuccess,
});

const mapDispatchToProps = {
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(TaskUpdate);
