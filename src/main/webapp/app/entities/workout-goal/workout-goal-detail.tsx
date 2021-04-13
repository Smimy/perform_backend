import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './workout-goal.reducer';
import { IWorkoutGoal } from 'app/shared/model/workout-goal.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IWorkoutGoalDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const WorkoutGoalDetail = (props: IWorkoutGoalDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { workoutGoalEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          WorkoutGoal [<b>{workoutGoalEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="name">Name</span>
          </dt>
          <dd>{workoutGoalEntity.name}</dd>
        </dl>
        <Button tag={Link} to="/workout-goal" replace color="info">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/workout-goal/${workoutGoalEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ workoutGoal }: IRootState) => ({
  workoutGoalEntity: workoutGoal.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(WorkoutGoalDetail);
