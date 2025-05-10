import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import TaskHistory from './task-history';
import TaskHistoryDetail from './task-history-detail';
import TaskHistoryUpdate from './task-history-update';
import TaskHistoryDeleteDialog from './task-history-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={TaskHistoryUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={TaskHistoryUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={TaskHistoryDetail} />
      <ErrorBoundaryRoute path={match.url} component={TaskHistory} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={TaskHistoryDeleteDialog} />
  </>
);

export default Routes;
