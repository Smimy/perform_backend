import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import WorkoutGoal from './workout-goal';
import WorkoutGoalDetail from './workout-goal-detail';
import WorkoutGoalUpdate from './workout-goal-update';
import WorkoutGoalDeleteDialog from './workout-goal-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={WorkoutGoalUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={WorkoutGoalUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={WorkoutGoalDetail} />
      <ErrorBoundaryRoute path={match.url} component={WorkoutGoal} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={WorkoutGoalDeleteDialog} />
  </>
);

export default Routes;
