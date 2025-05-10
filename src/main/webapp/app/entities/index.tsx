import React from 'react';
import { Switch } from 'react-router-dom';

// eslint-disable-next-line @typescript-eslint/no-unused-vars
import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import CheckInOut from './check-in-out';
import Task from './task';
import AssignTask from './assign-task';
import TaskHistory from './task-history';
/* jhipster-needle-add-route-import - JHipster will add routes here */

const Routes = ({ match }) => (
  <div>
    <Switch>
      {/* prettier-ignore */}
      <ErrorBoundaryRoute path={`${match.url}check-in-out`} component={CheckInOut} />
      <ErrorBoundaryRoute path={`${match.url}task`} component={Task} />
      <ErrorBoundaryRoute path={`${match.url}assign-task`} component={AssignTask} />
      <ErrorBoundaryRoute path={`${match.url}task-history`} component={TaskHistory} />
      {/* jhipster-needle-add-route-path - JHipster will add routes here */}
    </Switch>
  </div>
);

export default Routes;
