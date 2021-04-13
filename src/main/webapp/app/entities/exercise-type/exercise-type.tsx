import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { ICrudGetAllAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './exercise-type.reducer';
import { IExerciseType } from 'app/shared/model/exercise-type.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IExerciseTypeProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const ExerciseType = (props: IExerciseTypeProps) => {
  useEffect(() => {
    props.getEntities();
  }, []);

  const { exerciseTypeList, match, loading } = props;
  return (
    <div>
      <h2 id="exercise-type-heading">
        Exercise Types
        <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
          <FontAwesomeIcon icon="plus" />
          &nbsp; Create new Exercise Type
        </Link>
      </h2>
      <div className="table-responsive">
        {exerciseTypeList && exerciseTypeList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>ID</th>
                <th>Name</th>
                <th>Description</th>
                <th />
              </tr>
            </thead>
            <tbody>
              {exerciseTypeList.map((exerciseType, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${exerciseType.id}`} color="link" size="sm">
                      {exerciseType.id}
                    </Button>
                  </td>
                  <td>{exerciseType.name}</td>
                  <td>{exerciseType.description}</td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${exerciseType.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${exerciseType.id}/edit`} color="primary" size="sm">
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${exerciseType.id}/delete`} color="danger" size="sm">
                        <FontAwesomeIcon icon="trash" /> <span className="d-none d-md-inline">Delete</span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && <div className="alert alert-warning">No Exercise Types found</div>
        )}
      </div>
    </div>
  );
};

const mapStateToProps = ({ exerciseType }: IRootState) => ({
  exerciseTypeList: exerciseType.entities,
  loading: exerciseType.loading,
});

const mapDispatchToProps = {
  getEntities,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ExerciseType);
