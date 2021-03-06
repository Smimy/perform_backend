import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './exercise.reducer';
import { IExercise } from 'app/shared/model/exercise.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IExerciseDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const ExerciseDetail = (props: IExerciseDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { exerciseEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          Exercise [<b>{exerciseEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="name">Name</span>
          </dt>
          <dd>{exerciseEntity.name}</dd>
          <dt>
            <span id="number">Number</span>
          </dt>
          <dd>{exerciseEntity.number}</dd>
          <dt>
            <span id="comment">Comment</span>
          </dt>
          <dd>{exerciseEntity.comment}</dd>
          <dt>Workout</dt>
          <dd>{exerciseEntity.workoutId ? exerciseEntity.workoutId : ''}</dd>
          <dt>Exercise Type</dt>
          <dd>{exerciseEntity.exerciseTypeId ? exerciseEntity.exerciseTypeId : ''}</dd>
        </dl>
        <Button tag={Link} to="/exercise" replace color="info">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/exercise/${exerciseEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ exercise }: IRootState) => ({
  exerciseEntity: exercise.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ExerciseDetail);
