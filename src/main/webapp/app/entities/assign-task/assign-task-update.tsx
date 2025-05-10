import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { getEntity, updateEntity, createEntity, reset } from './assign-task.reducer';
import { IAssignTask } from 'app/shared/model/assign-task.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IAssignTaskUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const AssignTaskUpdate = (props: IAssignTaskUpdateProps) => {
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { assignTaskEntity, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/assign-task' + props.location.search);
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
    values.assignAt = convertDateTimeToServer(values.assignAt);
    values.createdDate = convertDateTimeToServer(values.createdDate);
    values.lastModifiedDate = convertDateTimeToServer(values.lastModifiedDate);

    if (errors.length === 0) {
      const entity = {
        ...assignTaskEntity,
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
          <h2 id="datnApp.assignTask.home.createOrEditLabel">Create or edit a AssignTask</h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : assignTaskEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="assign-task-id">ID</Label>
                  <AvInput id="assign-task-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="taskIdLabel" for="assign-task-taskId">
                  Task Id
                </Label>
                <AvField id="assign-task-taskId" type="string" className="form-control" name="taskId" />
              </AvGroup>
              <AvGroup>
                <Label id="assigneeIdLabel" for="assign-task-assigneeId">
                  Assignee Id
                </Label>
                <AvField id="assign-task-assigneeId" type="string" className="form-control" name="assigneeId" />
              </AvGroup>
              <AvGroup>
                <Label id="assignAtLabel" for="assign-task-assignAt">
                  Assign At
                </Label>
                <AvInput
                  id="assign-task-assignAt"
                  type="datetime-local"
                  className="form-control"
                  name="assignAt"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.assignTaskEntity.assignAt)}
                />
              </AvGroup>
              <AvGroup>
                <Label id="userIdLabel" for="assign-task-userId">
                  User Id
                </Label>
                <AvField id="assign-task-userId" type="string" className="form-control" name="userId" />
              </AvGroup>
              <AvGroup>
                <Label id="createdDateLabel" for="assign-task-createdDate">
                  Created Date
                </Label>
                <AvInput
                  id="assign-task-createdDate"
                  type="datetime-local"
                  className="form-control"
                  name="createdDate"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.assignTaskEntity.createdDate)}
                />
              </AvGroup>
              <AvGroup>
                <Label id="createdByLabel" for="assign-task-createdBy">
                  Created By
                </Label>
                <AvField id="assign-task-createdBy" type="text" name="createdBy" />
              </AvGroup>
              <AvGroup>
                <Label id="lastModifiedDateLabel" for="assign-task-lastModifiedDate">
                  Last Modified Date
                </Label>
                <AvInput
                  id="assign-task-lastModifiedDate"
                  type="datetime-local"
                  className="form-control"
                  name="lastModifiedDate"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.assignTaskEntity.lastModifiedDate)}
                />
              </AvGroup>
              <AvGroup>
                <Label id="lastModifiedByLabel" for="assign-task-lastModifiedBy">
                  Last Modified By
                </Label>
                <AvField id="assign-task-lastModifiedBy" type="text" name="lastModifiedBy" />
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/assign-task" replace color="info">
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
  assignTaskEntity: storeState.assignTask.entity,
  loading: storeState.assignTask.loading,
  updating: storeState.assignTask.updating,
  updateSuccess: storeState.assignTask.updateSuccess,
});

const mapDispatchToProps = {
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(AssignTaskUpdate);
