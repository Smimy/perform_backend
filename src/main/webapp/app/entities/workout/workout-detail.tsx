import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { ICrudGetAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './workout.reducer';
import { IWorkout } from 'app/shared/model/workout.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IWorkoutDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const WorkoutDetail = (props: IWorkoutDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { workoutEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          Workout [<b>{workoutEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="name">Name</span>
          </dt>
          <dd>{workoutEntity.name}</dd>
          <dt>
            <span id="date">Date</span>
          </dt>
          <dd>{workoutEntity.date ? <TextFormat value={workoutEntity.date} type="date" format={APP_LOCAL_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="comment">Comment</span>
          </dt>
          <dd>{workoutEntity.comment}</dd>
          <dt>Workout Goal</dt>
          <dd>{workoutEntity.workoutGoalId ? workoutEntity.workoutGoalId : ''}</dd>
          <dt>User</dt>
          <dd>{workoutEntity.userId ? workoutEntity.userId : ''}</dd>
        </dl>
        <Button tag={Link} to="/workout" replace color="info">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/workout/${workoutEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ workout }: IRootState) => ({
  workoutEntity: workout.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(WorkoutDetail);
