import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import ExerciseType from './exercise-type';
import ExerciseTypeDetail from './exercise-type-detail';
import ExerciseTypeUpdate from './exercise-type-update';
import ExerciseTypeDeleteDialog from './exercise-type-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={ExerciseTypeUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={ExerciseTypeUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={ExerciseTypeDetail} />
      <ErrorBoundaryRoute path={match.url} component={ExerciseType} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={ExerciseTypeDeleteDialog} />
  </>
);

export default Routes;
