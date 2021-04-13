import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './serie.reducer';
import { ISerie } from 'app/shared/model/serie.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface ISerieDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const SerieDetail = (props: ISerieDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { serieEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          Serie [<b>{serieEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="number">Number</span>
          </dt>
          <dd>{serieEntity.number}</dd>
          <dt>
            <span id="repsAchieved">Reps Achieved</span>
          </dt>
          <dd>{serieEntity.repsAchieved}</dd>
          <dt>
            <span id="weight">Weight</span>
          </dt>
          <dd>{serieEntity.weight}</dd>
          <dt>
            <span id="percent1RM">Percent 1 RM</span>
          </dt>
          <dd>{serieEntity.percent1RM}</dd>
          <dt>
            <span id="restingTime">Resting Time</span>
          </dt>
          <dd>{serieEntity.restingTime}</dd>
          <dt>Exercise</dt>
          <dd>{serieEntity.exerciseId ? serieEntity.exerciseId : ''}</dd>
        </dl>
        <Button tag={Link} to="/serie" replace color="info">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/serie/${serieEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ serie }: IRootState) => ({
  serieEntity: serie.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(SerieDetail);
