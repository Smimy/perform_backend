import React, { useEffect } from 'react';
import { connect } from 'react-redux';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { ICrudGetAction } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IRootState } from 'app/shared/reducers';
import { getEntity } from './exercise-type.reducer';
import { IExerciseType } from 'app/shared/model/exercise-type.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';

export interface IExerciseTypeDetailProps extends StateProps, DispatchProps, RouteComponentProps<{ id: string }> {}

export const ExerciseTypeDetail = (props: IExerciseTypeDetailProps) => {
  useEffect(() => {
    props.getEntity(props.match.params.id);
  }, []);

  const { exerciseTypeEntity } = props;
  return (
    <Row>
      <Col md="8">
        <h2>
          ExerciseType [<b>{exerciseTypeEntity.id}</b>]
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="name">Name</span>
          </dt>
          <dd>{exerciseTypeEntity.name}</dd>
          <dt>
            <span id="description">Description</span>
          </dt>
          <dd>{exerciseTypeEntity.description}</dd>
        </dl>
        <Button tag={Link} to="/exercise-type" replace color="info">
          <FontAwesomeIcon icon="arrow-left" /> <span className="d-none d-md-inline">Back</span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/exercise-type/${exerciseTypeEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" /> <span className="d-none d-md-inline">Edit</span>
        </Button>
      </Col>
    </Row>
  );
};

const mapStateToProps = ({ exerciseType }: IRootState) => ({
  exerciseTypeEntity: exerciseType.entity,
});

const mapDispatchToProps = { getEntity };

type StateProps = ReturnType<typeof mapStateToProps>;
type DispatchProps = typeof mapDispatchToProps;

export default connect(mapStateToProps, mapDispatchToProps)(ExerciseTypeDetail);
