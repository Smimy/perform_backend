import React from 'react';
import { Switch } from 'react-router-dom';

// eslint-disable-next-line @typescript-eslint/no-unused-vars
import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import WorkoutGoal from './workout-goal';
import Workout from './workout';
import Exercise from './exercise';
import ExerciseType from './exercise-type';
import Serie from './serie';
/* jhipster-needle-add-route-import - JHipster will add routes here */

const Routes = ({ match }) => (
  <div>
    <Switch>
      {/* prettier-ignore */}
      <ErrorBoundaryRoute path={`${match.url}workout-goal`} component={WorkoutGoal} />
      <ErrorBoundaryRoute path={`${match.url}workout`} component={Workout} />
      <ErrorBoundaryRoute path={`${match.url}exercise`} component={Exercise} />
      <ErrorBoundaryRoute path={`${match.url}exercise-type`} component={ExerciseType} />
      <ErrorBoundaryRoute path={`${match.url}serie`} component={Serie} />
      {/* jhipster-needle-add-route-path - JHipster will add routes here */}
    </Switch>
  </div>
);

export default Routes;
