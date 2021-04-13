import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IWorkoutGoal } from 'app/shared/model/workout-goal.model';
import { getEntities as getWorkoutGoals } from 'app/entities/workout-goal/workout-goal.reducer';
import { IUser } from 'app/shared/model/user.model';
import { getUsers } from 'app/modules/administration/user-management/user-management.reducer';
import { getEntity, updateEntity, createEntity, reset } from './workout.reducer';
import { IWorkout } from 'app/shared/model/workout.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IWorkoutUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const WorkoutUpdate = (props: IWorkoutUpdateProps) => {
  const [workoutGoalId, setWorkoutGoalId] = useState('0');
  const [userId, setUserId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { workoutEntity, workoutGoals, users, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/workout');
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getWorkoutGoals();
    props.getUsers();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...workoutEntity,
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
          <h2 id="backEndApp.workout.home.createOrEditLabel">Create or edit a Workout</h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : workoutEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="workout-id">ID</Label>
                  <AvInput id="workout-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="nameLabel" for="workout-name">
                  Name
                </Label>
                <AvField
                  id="workout-name"
                  type="text"
                  name="name"
                  validate={{
                    required: { value: true, errorMessage: 'This field is required.' },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="dateLabel" for="workout-date">
                  Date
                </Label>
                <AvField
                  id="workout-date"
                  type="date"
                  className="form-control"
                  name="date"
                  validate={{
                    required: { value: true, errorMessage: 'This field is required.' },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="commentLabel" for="workout-comment">
                  Comment
                </Label>
                <AvField id="workout-comment" type="text" name="comment" />
              </AvGroup>
              <AvGroup>
                <Label for="workout-workoutGoal">Workout Goal</Label>
                <AvInput id="workout-workoutGoal" type="select" className="form-control" name="workoutGoalId" required>
                  {workoutGoals
                    ? workoutGoals.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
                <AvFeedback>This field is required.</AvFeedback>
              </AvGroup>
              <AvGroup>
                <Label for="workout-user">User</Label>
                <AvInput id="workout-user" type="select" className="form-control" name="userId" required>
                  {users
                    ? users.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
                <AvFeedback>This field is required.</AvFeedback>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/workout" replace color="info">
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
  workoutGoals: storeState.workoutGoal.entities,
  users: storeState.userManagement.users,
  workoutEntity: storeState.workout.entity,
  loading: storeState.workout.loading,
  updating: storeState.workout.updating,
  updateSuccess: storeState.workout.updateSuccess,
});

const mapDispatchToProps = {
  getWorkoutGoals,
  getUsers,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(WorkoutUpdate);
