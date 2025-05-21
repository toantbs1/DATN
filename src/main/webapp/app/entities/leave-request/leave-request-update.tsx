import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { getEntity, updateEntity, createEntity, reset } from './leave-request.reducer';
import { ILeaveRequest } from 'app/shared/model/leave-request.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface ILeaveRequestUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const LeaveRequestUpdate = (props: ILeaveRequestUpdateProps) => {
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { leaveRequestEntity, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/leave-request' + props.location.search);
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
    values.leaveDate = convertDateTimeToServer(values.leaveDate);
    values.createdDate = convertDateTimeToServer(values.createdDate);
    values.lastModifiedDate = convertDateTimeToServer(values.lastModifiedDate);

    if (errors.length === 0) {
      const entity = {
        ...leaveRequestEntity,
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
          <h2 id="datnApp.leaveRequest.home.createOrEditLabel">Create or edit a LeaveRequest</h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : leaveRequestEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="leave-request-id">ID</Label>
                  <AvInput id="leave-request-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="leaveDateLabel" for="leave-request-leaveDate">
                  Leave Date
                </Label>
                <AvInput
                  id="leave-request-leaveDate"
                  type="datetime-local"
                  className="form-control"
                  name="leaveDate"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.leaveRequestEntity.leaveDate)}
                />
              </AvGroup>
              <AvGroup>
                <Label id="reasonLabel" for="leave-request-reason">
                  Reason
                </Label>
                <AvField id="leave-request-reason" type="text" name="reason" />
              </AvGroup>
              <AvGroup>
                <Label id="statusLabel" for="leave-request-status">
                  Status
                </Label>
                <AvField id="leave-request-status" type="text" name="status" />
              </AvGroup>
              <AvGroup>
                <Label id="approvedUserIdLabel" for="leave-request-approvedUserId">
                  Approved User Id
                </Label>
                <AvField id="leave-request-approvedUserId" type="string" className="form-control" name="approvedUserId" />
              </AvGroup>
              <AvGroup>
                <Label id="approvedNameLabel" for="leave-request-approvedName">
                  Approved Name
                </Label>
                <AvField id="leave-request-approvedName" type="text" name="approvedName" />
              </AvGroup>
              <AvGroup>
                <Label id="replyLabel" for="leave-request-reply">
                  Reply
                </Label>
                <AvField id="leave-request-reply" type="text" name="reply" />
              </AvGroup>
              <AvGroup>
                <Label id="userIdLabel" for="leave-request-userId">
                  User Id
                </Label>
                <AvField id="leave-request-userId" type="string" className="form-control" name="userId" />
              </AvGroup>
              <AvGroup>
                <Label id="createdDateLabel" for="leave-request-createdDate">
                  Created Date
                </Label>
                <AvInput
                  id="leave-request-createdDate"
                  type="datetime-local"
                  className="form-control"
                  name="createdDate"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.leaveRequestEntity.createdDate)}
                />
              </AvGroup>
              <AvGroup>
                <Label id="createdByLabel" for="leave-request-createdBy">
                  Created By
                </Label>
                <AvField id="leave-request-createdBy" type="text" name="createdBy" />
              </AvGroup>
              <AvGroup>
                <Label id="lastModifiedDateLabel" for="leave-request-lastModifiedDate">
                  Last Modified Date
                </Label>
                <AvInput
                  id="leave-request-lastModifiedDate"
                  type="datetime-local"
                  className="form-control"
                  name="lastModifiedDate"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.leaveRequestEntity.lastModifiedDate)}
                />
              </AvGroup>
              <AvGroup>
                <Label id="lastModifiedByLabel" for="leave-request-lastModifiedBy">
                  Last Modified By
                </Label>
                <AvField id="leave-request-lastModifiedBy" type="text" name="lastModifiedBy" />
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/leave-request" replace color="info">
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
  leaveRequestEntity: storeState.leaveRequest.entity,
  loading: storeState.leaveRequest.loading,
  updating: storeState.leaveRequest.updating,
  updateSuccess: storeState.leaveRequest.updateSuccess,
});

const mapDispatchToProps = {
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(LeaveRequestUpdate);
