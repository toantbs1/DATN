import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { getEntity, updateEntity, createEntity, reset } from './check-in-out.reducer';
import { ICheckInOut } from 'app/shared/model/check-in-out.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface ICheckInOutUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const CheckInOutUpdate = (props: ICheckInOutUpdateProps) => {
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { checkInOutEntity, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/check-in-out' + props.location.search);
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
    values.checkInTime = convertDateTimeToServer(values.checkInTime);
    values.checkOutTime = convertDateTimeToServer(values.checkOutTime);
    values.createdDate = convertDateTimeToServer(values.createdDate);
    values.lastModifiedDate = convertDateTimeToServer(values.lastModifiedDate);

    if (errors.length === 0) {
      const entity = {
        ...checkInOutEntity,
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
          <h2 id="datnApp.checkInOut.home.createOrEditLabel">Create or edit a CheckInOut</h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : checkInOutEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="check-in-out-id">ID</Label>
                  <AvInput id="check-in-out-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="checkInTimeLabel" for="check-in-out-checkInTime">
                  Check In Time
                </Label>
                <AvInput
                  id="check-in-out-checkInTime"
                  type="datetime-local"
                  className="form-control"
                  name="checkInTime"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.checkInOutEntity.checkInTime)}
                />
              </AvGroup>
              <AvGroup>
                <Label id="checkInLatLabel" for="check-in-out-checkInLat">
                  Check In Lat
                </Label>
                <AvField id="check-in-out-checkInLat" type="text" name="checkInLat" />
              </AvGroup>
              <AvGroup>
                <Label id="checkInLngLabel" for="check-in-out-checkInLng">
                  Check In Lng
                </Label>
                <AvField id="check-in-out-checkInLng" type="text" name="checkInLng" />
              </AvGroup>
              <AvGroup>
                <Label id="checkOutTimeLabel" for="check-in-out-checkOutTime">
                  Check Out Time
                </Label>
                <AvInput
                  id="check-in-out-checkOutTime"
                  type="datetime-local"
                  className="form-control"
                  name="checkOutTime"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.checkInOutEntity.checkOutTime)}
                />
              </AvGroup>
              <AvGroup>
                <Label id="checkOutLatLabel" for="check-in-out-checkOutLat">
                  Check Out Lat
                </Label>
                <AvField id="check-in-out-checkOutLat" type="text" name="checkOutLat" />
              </AvGroup>
              <AvGroup>
                <Label id="checkOutLngLabel" for="check-in-out-checkOutLng">
                  Check Out Lng
                </Label>
                <AvField id="check-in-out-checkOutLng" type="text" name="checkOutLng" />
              </AvGroup>
              <AvGroup>
                <Label id="userIdLabel" for="check-in-out-userId">
                  User Id
                </Label>
                <AvField id="check-in-out-userId" type="string" className="form-control" name="userId" />
              </AvGroup>
              <AvGroup>
                <Label id="createdDateLabel" for="check-in-out-createdDate">
                  Created Date
                </Label>
                <AvInput
                  id="check-in-out-createdDate"
                  type="datetime-local"
                  className="form-control"
                  name="createdDate"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.checkInOutEntity.createdDate)}
                />
              </AvGroup>
              <AvGroup>
                <Label id="createdByLabel" for="check-in-out-createdBy">
                  Created By
                </Label>
                <AvField id="check-in-out-createdBy" type="text" name="createdBy" />
              </AvGroup>
              <AvGroup>
                <Label id="lastModifiedDateLabel" for="check-in-out-lastModifiedDate">
                  Last Modified Date
                </Label>
                <AvInput
                  id="check-in-out-lastModifiedDate"
                  type="datetime-local"
                  className="form-control"
                  name="lastModifiedDate"
                  placeholder={'YYYY-MM-DD HH:mm'}
                  value={isNew ? displayDefaultDateTime() : convertDateTimeFromServer(props.checkInOutEntity.lastModifiedDate)}
                />
              </AvGroup>
              <AvGroup>
                <Label id="lastModifiedByLabel" for="check-in-out-lastModifiedBy">
                  Last Modified By
                </Label>
                <AvField id="check-in-out-lastModifiedBy" type="text" name="lastModifiedBy" />
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/check-in-out" replace color="info">
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
  checkInOutEntity: storeState.checkInOut.entity,
  loading: storeState.checkInOut.loading,
  updating: storeState.checkInOut.updating,
  updateSuccess: storeState.checkInOut.updateSuccess,
});

const mapDispatchToProps = {
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(CheckInOutUpdate);
