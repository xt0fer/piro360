import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './tag-piro-v.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const TagPiroVDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const tagEntity = useAppSelector(state => state.tag.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="tagDetailsHeading">
          <Translate contentKey="piro360App.tag.detail.title">Tag</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{tagEntity.id}</dd>
          <dt>
            <span id="contents">
              <Translate contentKey="piro360App.tag.contents">Contents</Translate>
            </span>
          </dt>
          <dd>{tagEntity.contents}</dd>
          <dt>
            <span id="location">
              <Translate contentKey="piro360App.tag.location">Location</Translate>
            </span>
          </dt>
          <dd>{tagEntity.location}</dd>
          <dt>
            <span id="commentDate">
              <Translate contentKey="piro360App.tag.commentDate">Comment Date</Translate>
            </span>
          </dt>
          <dd>{tagEntity.commentDate ? <TextFormat value={tagEntity.commentDate} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <Translate contentKey="piro360App.tag.pirovideo">Pirovideo</Translate>
          </dt>
          <dd>{tagEntity.pirovideo ? tagEntity.pirovideo.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/tag-piro-v" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/tag-piro-v/${tagEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default TagPiroVDetail;
