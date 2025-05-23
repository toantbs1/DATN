import { combineReducers } from 'redux';
import { loadingBarReducer as loadingBar } from 'react-redux-loading-bar';

import authentication, { AuthenticationState } from './authentication';
import applicationProfile, { ApplicationProfileState } from './application-profile';

import administration, { AdministrationState } from 'app/modules/administration/administration.reducer';
import userManagement, { UserManagementState } from 'app/modules/administration/user-management/user-management.reducer';
import register, { RegisterState } from 'app/modules/account/register/register.reducer';
import activate, { ActivateState } from 'app/modules/account/activate/activate.reducer';
import password, { PasswordState } from 'app/modules/account/password/password.reducer';
import settings, { SettingsState } from 'app/modules/account/settings/settings.reducer';
import passwordReset, { PasswordResetState } from 'app/modules/account/password-reset/password-reset.reducer';
// prettier-ignore
import checkInOut, {
  CheckInOutState
} from 'app/entities/check-in-out/check-in-out.reducer';
// prettier-ignore
import task, {
  TaskState
} from 'app/entities/task/task.reducer';
// prettier-ignore
import assignTask, {
  AssignTaskState
} from 'app/entities/assign-task/assign-task.reducer';
// prettier-ignore
import taskHistory, {
  TaskHistoryState
} from 'app/entities/task-history/task-history.reducer';
// prettier-ignore
import leaveRequest, {
  LeaveRequestState
} from 'app/entities/leave-request/leave-request.reducer';
/* jhipster-needle-add-reducer-import - JHipster will add reducer here */

export interface IRootState {
  readonly authentication: AuthenticationState;
  readonly applicationProfile: ApplicationProfileState;
  readonly administration: AdministrationState;
  readonly userManagement: UserManagementState;
  readonly register: RegisterState;
  readonly activate: ActivateState;
  readonly passwordReset: PasswordResetState;
  readonly password: PasswordState;
  readonly settings: SettingsState;
  readonly checkInOut: CheckInOutState;
  readonly task: TaskState;
  readonly assignTask: AssignTaskState;
  readonly taskHistory: TaskHistoryState;
  readonly leaveRequest: LeaveRequestState;
  /* jhipster-needle-add-reducer-type - JHipster will add reducer type here */
  readonly loadingBar: any;
}

const rootReducer = combineReducers<IRootState>({
  authentication,
  applicationProfile,
  administration,
  userManagement,
  register,
  activate,
  passwordReset,
  password,
  settings,
  checkInOut,
  task,
  assignTask,
  taskHistory,
  leaveRequest,
  /* jhipster-needle-add-reducer-combine - JHipster will add reducer here */
  loadingBar,
});

export default rootReducer;
