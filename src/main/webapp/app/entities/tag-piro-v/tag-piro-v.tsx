import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntities } from './tag-piro-v.reducer';
import { ITagPiroV } from 'app/shared/model/tag-piro-v.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const TagPiroV = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const tagList = useAppSelector(state => state.tag.entities);
  const loading = useAppSelector(state => state.tag.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  const { match } = props;

  return (
    <div>
      <h2 id="tag-piro-v-heading" data-cy="TagHeading">
        <Translate contentKey="piro360App.tag.home.title">Tags</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="piro360App.tag.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="piro360App.tag.home.createLabel">Create new Tag</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {tagList && tagList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="piro360App.tag.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="piro360App.tag.contents">Contents</Translate>
                </th>
                <th>
                  <Translate contentKey="piro360App.tag.location">Location</Translate>
                </th>
                <th>
                  <Translate contentKey="piro360App.tag.commentDate">Comment Date</Translate>
                </th>
                <th>
                  <Translate contentKey="piro360App.tag.pirovideo">Pirovideo</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {tagList.map((tag, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`${match.url}/${tag.id}`} color="link" size="sm">
                      {tag.id}
                    </Button>
                  </td>
                  <td>{tag.contents}</td>
                  <td>{tag.location}</td>
                  <td>{tag.commentDate ? <TextFormat type="date" value={tag.commentDate} format={APP_DATE_FORMAT} /> : null}</td>
                  <td>{tag.pirovideo ? <Link to={`pirovideo-piro-v/${tag.pirovideo.id}`}>{tag.pirovideo.id}</Link> : ''}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${tag.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${tag.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${tag.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
                        <FontAwesomeIcon icon="trash" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.delete">Delete</Translate>
                        </span>
                      </Button>
                    </div>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        ) : (
          !loading && (
            <div className="alert alert-warning">
              <Translate contentKey="piro360App.tag.home.notFound">No Tags found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default TagPiroV;
