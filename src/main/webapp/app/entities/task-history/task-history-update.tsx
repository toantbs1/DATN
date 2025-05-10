import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { getEntity, updateEntity, createEntity, reset } from './task-history.reducer';
import { ITaskHistory } from 'app/shared/model/task-history.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface ITaskHistoryUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const TaskHistoryUpdate = (props: ITaskHistoryUpdateProps) => {
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { taskHistoryEntity, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/task-history' + props.location.search);
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
    values.createdDate = convertDateTimeToServer(values.createdDate);
    values.lastModifiedDate = convertDateTimeToServer(values.lastModifiedDate);

    if (errors.length === 0) {
      const entity = {
        ...taskHistoryEntity,
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
          <h2 id="datnApp.taskHistory.home.createOrEditLabel">Create or edit a TaskHistory</h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : taskHistoryEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="task-history-id">ID</Label>
                  <AvInput id="task-history-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="taskIdLabel" for="task-history-taskId">
                  Task Id
                </Label>
                <AvField id="task-history-taskId" type="string" className="form-control" name="taskId" />
              </AvGroup>
              <AvGroup>
                <Label id="actionCodeLabel" for="task-history-actionCode">
                  Action Code
                </Label>
                <AvField id="task-history-actionCode" type="text" name="actionCode" />
              </AvGroup>
              <AvGroup>
                <Label id="paramLabel" for="task-history-param">
                  Param
                </Label>
                <AvField id="task-history-param" type="text" name="param" />
              </AvGroup>
              <AvGroup>
                <Label id="userIdLabel" for="task-history-userId">
                  User Id
                </Label>
                <AvField id="task-history-userId" type="string" className="form-control" name="userId" />
              </AvGroup>
              <AvGroup>
                <Label id="createdDateLabel" for="task-history-createdDate">
                  Created Date
                </Label>
                <AvInput
                  id="task-history-createdDate"
                  type="datetime-local"
                  className="form-control"
                  name="createdDate"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.taskHistoryEntity.createdDate)}
                />
              </AvGroup>
              <AvGroup>
                <Label id="createdByLabel" for="task-history-createdBy">
                  Created By
                </Label>
                <AvField id="task-history-createdBy" type="text" name="createdBy" />
              </AvGroup>
              <AvGroup>
                <Label id="lastModifiedDateLabel" for="task-history-lastModifiedDate">
                  Last Modified Date
                </Label>
                <AvInput
                  id="task-history-lastModifiedDate"
                  type="datetime-local"
                  className="form-control"
                  name="lastModifiedDate"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.taskHistoryEntity.lastModifiedDate)}
                />
              </AvGroup>
              <AvGroup>
                <Label id="lastModifiedByLabel" for="task-history-lastModifiedBy">
                  Last Modified By
                </Label>
                <AvField id="task-history-lastModifiedBy" type="text" name="lastModifiedBy" />
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/task-history" replace color="info">
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
  taskHistoryEntity: storeState.taskHistory.entity,
  loading: storeState.taskHistory.loading,
  updating: storeState.taskHistory.updating,
  updateSuccess: storeState.taskHistory.updateSuccess,
});

const mapDispatchToProps = {
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(TaskHistoryUpdate);
