import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, Label } from 'reactstrap';
import { AvFeedback, AvForm, AvGroup, AvInput, AvField } from 'availity-reactstrap-validation';
import { ICrudGetAction, ICrudGetAllAction, ICrudPutAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { IRootState } from 'app/shared/reducers';

import { IExercise } from 'app/shared/model/exercise.model';
import { getEntities as getExercises } from 'app/entities/exercise/exercise.reducer';
import { getEntity, updateEntity, createEntity, reset } from './serie.reducer';
import { ISerie } from 'app/shared/model/serie.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';

export interface ISerieUpdateProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const SerieUpdate = (props: ISerieUpdateProps) => {
  const [exerciseId, setExerciseId] = useState('0');
  const [isNew, setIsNew] = useState(!props.match.params || !props.match.params.id);

  const { serieEntity, exercises, loading, updating } = props;

  const handleClose = () => {
    props.history.push('/serie');
  };

  useEffect(() => {
    if (isNew) {
      props.reset();
    } else {
      props.getEntity(props.match.params.id);
    }

    props.getExercises();
  }, []);

  useEffect(() => {
    if (props.updateSuccess) {
      handleClose();
    }
  }, [props.updateSuccess]);

  const saveEntity = (event, errors, values) => {
    if (errors.length === 0) {
      const entity = {
        ...serieEntity,
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
          <h2 id="backEndApp.serie.home.createOrEditLabel">Create or edit a Serie</h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <AvForm model={isNew ? {} : serieEntity} onSubmit={saveEntity}>
              {!isNew ? (
                <AvGroup>
                  <Label for="serie-id">ID</Label>
                  <AvInput id="serie-id" type="text" className="form-control" name="id" required readOnly />
                </AvGroup>
              ) : null}
              <AvGroup>
                <Label id="numberLabel" for="serie-number">
                  Number
                </Label>
                <AvField
                  id="serie-number"
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
                <Label id="repsAchievedLabel" for="serie-repsAchieved">
                  Reps Achieved
                </Label>
                <AvField
                  id="serie-repsAchieved"
                  type="string"
                  className="form-control"
                  name="repsAchieved"
                  validate={{
                    required: { value: true, errorMessage: 'This field is required.' },
                    number: { value: true, errorMessage: 'This field should be a number.' },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="weightLabel" for="serie-weight">
                  Weight
                </Label>
                <AvField
                  id="serie-weight"
                  type="string"
                  className="form-control"
                  name="weight"
                  validate={{
                    required: { value: true, errorMessage: 'This field is required.' },
                    number: { value: true, errorMessage: 'This field should be a number.' },
                  }}
                />
              </AvGroup>
              <AvGroup>
                <Label id="percent1RMLabel" for="serie-percent1RM">
                  Percent 1 RM
                </Label>
                <AvField id="serie-percent1RM" type="string" className="form-control" name="percent1RM" />
              </AvGroup>
              <AvGroup>
                <Label id="restingTimeLabel" for="serie-restingTime">
                  Resting Time
                </Label>
                <AvField id="serie-restingTime" type="string" className="form-control" name="restingTime" />
              </AvGroup>
              <AvGroup>
                <Label for="serie-exercise">Exercise</Label>
                <AvInput id="serie-exercise" type="select" className="form-control" name="exerciseId" required>
                  {exercises
                    ? exercises.map(otherEntity => (
                        <option value={otherEntity.id} key={otherEntity.id}>
                          {otherEntity.id}
                        </option>
                      ))
                    : null}
                </AvInput>
                <AvFeedback>This field is required.</AvFeedback>
              </AvGroup>
              <Button tag={Link} id="cancel-save" to="/serie" replace color="info">
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
  exercises: storeState.exercise.entities,
  serieEntity: storeState.serie.entity,
  loading: storeState.serie.loading,
  updating: storeState.serie.updating,
  updateSuccess: storeState.serie.updateSuccess,
});

const mapDispatchToProps = {
  getExercises,
  getEntity,
  updateEntity,
  createEntity,
  reset,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(SerieUpdate);
