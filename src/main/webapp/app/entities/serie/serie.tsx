import React, { useState, useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Col, Row, Table } from 'reactstrap';
import { ICrudGetAllAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntities } from './serie.reducer';
import { ISerie } from 'app/shared/model/serie.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ISerieProps extends StateProps, DispatchProps, RouteComponentProps<{ url: string }> {}

export const Serie = (props: ISerieProps) => {
  useEffect(() => {
    props.getEntities();
  }, []);

  const { serieList, match, loading } = props;
  return (
    <div>
      <h2 id="serie-heading">
        Series
        <Link to={`${match.url}/new`} className="btn btn-primary float-right jh-create-entity" id="jh-create-entity">
          <FontAwesomeIcon icon="plus" />
          &nbsp; Create new Serie
        </Link>
      </h2>
      <div className="table-responsive">
        {serieList && serieList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>ID</th>
                <th>Number</th>
                <th>Reps Achieved</th>
                <th>Weight</th>
                <th>Percent 1 RM</th>
                <th>Resting Time</th>
                <th>Exercise</th>
                <th />
              </tr>
            </thead>
            <tbody>
              {serieList.map((serie, i) => (
                <tr key={`entity-${i}`}>
                  <td>
                    <Button tag={Link} to={`${match.url}/${serie.id}`} color="link" size="sm">
                      {serie.id}
                    </Button>
                  </td>
                  <td>{serie.number}</td>
                  <td>{serie.repsAchieved}</td>
                  <td>{serie.weight}</td>
                  <td>{serie.percent1RM}</td>
                  <td>{serie.restingTime}</td>
                  <td>{serie.exerciseId ? <Link to={`exercise/${serie.exerciseId}`}>{serie.exerciseId}</Link> : ''}</td>
                  <td className="text-right">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${serie.id}`} color="info" size="sm">
                        <FontAwesomeIcon icon="eye" /> <span className="d-none d-md-inline">View</span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${serie.id}/edit`} color="primary" size="sm">
                        <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${serie.id}/delete`} color="danger" size="sm">
                        <FontAwesomeIcon icon="trash" /> <span className="d-none d-md-inline">Delete</span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && <div className="alert alert-warning">No Series found</div>
        )}
      </div>
    </div>
  );
};

const mapStateToProps = ({ serie }: IRootState) => ({
  serieList: serie.entities,
  loading: serie.loading,
});

const mapDispatchToProps = {
  getEntities,
};

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(Serie);
