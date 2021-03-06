import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { ICrudGetAllAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './workout-goal.reducer';
import { IWorkoutGoal } from 'app/shared/model/workout-goal.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IWorkoutGoalProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const WorkoutGoal = (props: IWorkoutGoalProps) => {
  useEffect(() => {
    props.getEntities();
  }, []);

  const { workoutGoalList, match, loading } = props;
  return (
    <div>
      <h2 id="workout-goal-heading">
        Workout Goals
        <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
          <FontAwesomeIcon icon="plus" />
          &nbsp; Create new Workout Goal
        </Link>
      </h2>
      <div className="table-responsive">
        {workoutGoalList && workoutGoalList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>ID</th>
                <th>Name</th>
                <th />
              </tr>
            </thead>
            <tbody>
              {workoutGoalList.map((workoutGoal, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${workoutGoal.id}`} color="link" size="sm">
                      {workoutGoal.id}
                    </Button>
                  </td>
                  <td>{workoutGoal.name}</td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${workoutGoal.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${workoutGoal.id}/edit`} color="primary" size="sm">
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${workoutGoal.id}/delete`} color="danger" size="sm">
                        <FontAwesomeIcon icon="trash" /> <span className="d-none d-md-inline">Delete</span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && <div className="alert alert-warning">No Workout Goals found</div>
        )}
      </div>
    </div>
  );
};

const mapStateToProps = ({ workoutGoal }: IRootState) => ({
  workoutGoalList: workoutGoal.entities,
  loading: workoutGoal.loading,
});

const mapDispatchToProps = {
  getEntities,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(WorkoutGoal);
