import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { ICrudGetAllAction, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './workout.reducer';
import { IWorkout } from 'app/shared/model/workout.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IWorkoutProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const Workout = (props: IWorkoutProps) => {
  useEffect(() => {
    props.getEntities();
  }, []);

  const { workoutList, match, loading } = props;
  return (
    <div>
      <h2 id="workout-heading">
        Workouts
        <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
          <FontAwesomeIcon icon="plus" />
          &nbsp; Create new Workout
        </Link>
      </h2>
      <div className="table-responsive">
        {workoutList && workoutList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>ID</th>
                <th>Name</th>
                <th>Date</th>
                <th>Comment</th>
                <th>Workout Goal</th>
                <th>User</th>
                <th />
              </tr>
            </thead>
            <tbody>
              {workoutList.map((workout, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${workout.id}`} color="link" size="sm">
                      {workout.id}
                    </Button>
                  </td>
                  <td>{workout.name}</td>
                  <td>{workout.date ? <TextFormat type="date" value={workout.date} format={APP_LOCAL_DATE_FORMAT} /> : null}</td>
                  <td>{workout.comment}</td>
                  <td>{workout.workoutGoalId ? <Link to={`workout-goal/${workout.workoutGoalId}`}>{workout.workoutGoalId}</Link> : ''}</td>
                  <td>{workout.userId ? workout.userId : ''}</td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${workout.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${workout.id}/edit`} color="primary" size="sm">
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${workout.id}/delete`} color="danger" size="sm">
                        <FontAwesomeIcon icon="trash" /> <span className="d-none d-md-inline">Delete</span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && <div className="alert alert-warning">No Workouts found</div>
        )}
      </div>
    </div>
  );
};

const mapStateToProps = ({ workout }: IRootState) => ({
  workoutList: workout.entities,
  loading: workout.loading,
});

const mapDispatchToProps = {
  getEntities,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(Workout);
