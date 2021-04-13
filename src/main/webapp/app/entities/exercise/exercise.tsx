import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { ICrudGetAllAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './exercise.reducer';
import { IExercise } from 'app/shared/model/exercise.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IExerciseProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const Exercise = (props: IExerciseProps) => {
  useEffect(() => {
    props.getEntities();
  }, []);

  const { exerciseList, match, loading } = props;
  return (
    <div>
      <h2 id="exercise-heading">
        Exercises
        <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
          <FontAwesomeIcon icon="plus" />
          &nbsp; Create new Exercise
        </Link>
      </h2>
      <div className="table-responsive">
        {exerciseList && exerciseList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>ID</th>
                <th>Name</th>
                <th>Number</th>
                <th>Comment</th>
                <th>Workout</th>
                <th>Exercise Type</th>
                <th />
              </tr>
            </thead>
            <tbody>
              {exerciseList.map((exercise, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${exercise.id}`} color="link" size="sm">
                      {exercise.id}
                    </Button>
                  </td>
                  <td>{exercise.name}</td>
                  <td>{exercise.number}</td>
                  <td>{exercise.comment}</td>
                  <td>{exercise.workoutId ? <Link to={`workout/${exercise.workoutId}`}>{exercise.workoutId}</Link> : ''}</td>
                  <td>
                    {exercise.exerciseTypeId ? <Link to={`exercise-type/${exercise.exerciseTypeId}`}>{exercise.exerciseTypeId}</Link> : ''}
                  </td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${exercise.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${exercise.id}/edit`} color="primary" size="sm">
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${exercise.id}/delete`} color="danger" size="sm">
                        <FontAwesomeIcon icon="trash" /> <span className="d-none d-md-inline">Delete</span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && <div className="alert alert-warning">No Exercises found</div>
        )}
      </div>
    </div>
  );
};

const mapStateToProps = ({ exercise }: IRootState) => ({
  exerciseList: exercise.entities,
  loading: exercise.loading,
});

const mapDispatchToProps = {
  getEntities,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(Exercise);
