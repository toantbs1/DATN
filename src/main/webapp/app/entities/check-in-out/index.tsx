import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import CheckInOut from './check-in-out';
import CheckInOutDetail from './check-in-out-detail';
import CheckInOutUpdate from './check-in-out-update';
import CheckInOutDeleteDialog from './check-in-out-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={CheckInOutUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={CheckInOutUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={CheckInOutDetail} />
      <ErrorBoundaryRoute path={match.url} component={CheckInOut} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={CheckInOutDeleteDialog} />
  </>
);

export default Routes;
