import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IWorkout } from 'app/shared/model/workout.model';
import { getEntities as getWorkouts } from 'app/entities/workout/workout.reducer';
import { IExerciseType } from 'app/shared/model/exercise-type.model';
import { getEntities as getExerciseTypes } from 'app/entities/exercise-type/exercise-type.reducer';
import { getEntity, updateEntity, createEntity, reset } from './exercise.reducer';
import { IExercise } from 'app/shared/model/exercise.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface IExerciseUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const ExerciseUpdate = (props: IExerciseUpdateProps) => {
  const [workoutId, setWorkoutId] = useState('0');
  const [exerciseTypeId, setExerciseTypeId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { exerciseEntity, workouts, exerciseTypes, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/exercise');
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getWorkouts();
    props.getExerciseTypes();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...exerciseEntity,
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
          <h2 id="backEndApp.exercise.home.createOrEditLabel">Create or edit a Exercise</h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : exerciseEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="exercise-id">ID</Label>
                  <AvInput id="exercise-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="nameLabel" for="exercise-name">
                  Name
                </Label>
                <AvField
                  id="exercise-name"
                  type="text"
                  name="name"
                  validate={{
                    required: { value: true, errorMessage: 'This field is required.' },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="numberLabel" for="exercise-number">
                  Number
                </Label>
                <AvField
                  id="exercise-number"
                  type="string"
                  className="form-control"
                  name="number"
                  validate={{
                    required: { value: true, errorMessage: 'This field is required.' },
                    number: { value: true, errorMessage: 'This field should be a number.' },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="commentLabel" for="exercise-comment">
                  Comment
                </Label>
                <AvField id="exercise-comment" type="text" name="comment" />
              </AvGroup>
              <AvGroup>
                <Label for="exercise-workout">Workout</Label>
                <AvInput id="exercise-workout" type="select" className="form-control" name="workoutId" required>
                  {workouts
                    ? workouts.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
                <AvFeedback>This field is required.</AvFeedback>
              </AvGroup>
              <AvGroup>
                <Label for="exercise-exerciseType">Exercise Type</Label>
                <AvInput id="exercise-exerciseType" type="select" className="form-control" name="exerciseTypeId" required>
                  {exerciseTypes
                    ? exerciseTypes.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
                <AvFeedback>This field is required.</AvFeedback>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/exercise" replace color="info">
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
  workouts: storeState.workout.entities,
  exerciseTypes: storeState.exerciseType.entities,
  exerciseEntity: storeState.exercise.entity,
  loading: storeState.exercise.loading,
  updating: storeState.exercise.updating,
  updateSuccess: storeState.exercise.updateSuccess,
});

const mapDispatchToProps = {
  getWorkouts,
  getExerciseTypes,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ExerciseUpdate);
