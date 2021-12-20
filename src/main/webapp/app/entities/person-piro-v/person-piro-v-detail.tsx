import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, UncontrolledTooltip, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './person-piro-v.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const PersonPiroVDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const personEntity = useAppSelector(state => state.person.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="personDetailsHeading">
          <Translate contentKey="piro360App.person.detail.title">Person</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{personEntity.id}</dd>
          <dt>
            <span id="firstName">
              <Translate contentKey="piro360App.person.firstName">First Name</Translate>
            </span>
            <UncontrolledTooltip target="firstName">
              <Translate contentKey="piro360App.person.help.firstName" />
            </UncontrolledTooltip>
          </dt>
          <dd>{personEntity.firstName}</dd>
          <dt>
            <span id="lastName">
              <Translate contentKey="piro360App.person.lastName">Last Name</Translate>
            </span>
          </dt>
          <dd>{personEntity.lastName}</dd>
          <dt>
            <span id="email">
              <Translate contentKey="piro360App.person.email">Email</Translate>
            </span>
          </dt>
          <dd>{personEntity.email}</dd>
          <dt>
            <span id="phoneNumber">
              <Translate contentKey="piro360App.person.phoneNumber">Phone Number</Translate>
            </span>
          </dt>
          <dd>{personEntity.phoneNumber}</dd>
        </dl>
        <Button tag={Link} to="/person-piro-v" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/person-piro-v/${personEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default PersonPiroVDetail;
