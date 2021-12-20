import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './pirovideo-piro-v.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const PirovideoPiroVDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const pirovideoEntity = useAppSelector(state => state.pirovideo.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="pirovideoDetailsHeading">
          <Translate contentKey="piro360App.pirovideo.detail.title">Pirovideo</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{pirovideoEntity.id}</dd>
          <dt>
            <span id="title">
              <Translate contentKey="piro360App.pirovideo.title">Title</Translate>
            </span>
          </dt>
          <dd>{pirovideoEntity.title}</dd>
          <dt>
            <span id="notes">
              <Translate contentKey="piro360App.pirovideo.notes">Notes</Translate>
            </span>
          </dt>
          <dd>{pirovideoEntity.notes}</dd>
          <dt>
            <span id="location">
              <Translate contentKey="piro360App.pirovideo.location">Location</Translate>
            </span>
          </dt>
          <dd>{pirovideoEntity.location}</dd>
          <dt>
            <span id="vidurl">
              <Translate contentKey="piro360App.pirovideo.vidurl">Vidurl</Translate>
            </span>
          </dt>
          <dd>{pirovideoEntity.vidurl}</dd>
          <dt>
            <span id="recordDate">
              <Translate contentKey="piro360App.pirovideo.recordDate">Record Date</Translate>
            </span>
          </dt>
          <dd>
            {pirovideoEntity.recordDate ? <TextFormat value={pirovideoEntity.recordDate} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <Translate contentKey="piro360App.pirovideo.owner">Owner</Translate>
          </dt>
          <dd>{pirovideoEntity.owner ? pirovideoEntity.owner.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/pirovideo-piro-v" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/pirovideo-piro-v/${pirovideoEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default PirovideoPiroVDetail;
