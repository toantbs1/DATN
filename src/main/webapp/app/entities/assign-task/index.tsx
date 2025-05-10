import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import AssignTask from './assign-task';
import AssignTaskDetail from './assign-task-detail';
import AssignTaskUpdate from './assign-task-update';
import AssignTaskDeleteDialog from './assign-task-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={AssignTaskUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={AssignTaskUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={AssignTaskDetail} />
      <ErrorBoundaryRoute path={match.url} component={AssignTask} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={AssignTaskDeleteDialog} />
  </>
);

export default Routes;
