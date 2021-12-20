import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Table } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntities } from './pirovideo-piro-v.reducer';
import { IPirovideoPiroV } from 'app/shared/model/pirovideo-piro-v.model';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const PirovideoPiroV = (props: RouteComponentProps<{ url: string }>) => {
  const dispatch = useAppDispatch();

  const pirovideoList = useAppSelector(state => state.pirovideo.entities);
  const loading = useAppSelector(state => state.pirovideo.loading);

  useEffect(() => {
    dispatch(getEntities({}));
  }, []);

  const handleSyncList = () => {
    dispatch(getEntities({}));
  };

  const { match } = props;

  return (
    <div>
      <h2 id="pirovideo-piro-v-heading" data-cy="PirovideoHeading">
        <Translate contentKey="piro360App.pirovideo.home.title">Pirovideos</Translate>
        <div className="d-flex justify-content-end">
          <Button className="me-2" color="info" onClick={handleSyncList} disabled={loading}>
            <FontAwesomeIcon icon="sync" spin={loading} />{' '}
            <Translate contentKey="piro360App.pirovideo.home.refreshListLabel">Refresh List</Translate>
          </Button>
          <Link to={`${match.url}/new`} className="btn btn-primary jh-create-entity" id="jh-create-entity" data-cy="entityCreateButton">
            <FontAwesomeIcon icon="plus" />
            &nbsp;
            <Translate contentKey="piro360App.pirovideo.home.createLabel">Create new Pirovideo</Translate>
          </Link>
        </div>
      </h2>
      <div className="table-responsive">
        {pirovideoList && pirovideoList.length > 0 ? (
          <Table responsive>
            <thead>
              <tr>
                <th>
                  <Translate contentKey="piro360App.pirovideo.id">ID</Translate>
                </th>
                <th>
                  <Translate contentKey="piro360App.pirovideo.title">Title</Translate>
                </th>
                <th>
                  <Translate contentKey="piro360App.pirovideo.notes">Notes</Translate>
                </th>
                <th>
                  <Translate contentKey="piro360App.pirovideo.location">Location</Translate>
                </th>
                <th>
                  <Translate contentKey="piro360App.pirovideo.vidurl">Vidurl</Translate>
                </th>
                <th>
                  <Translate contentKey="piro360App.pirovideo.recordDate">Record Date</Translate>
                </th>
                <th>
                  <Translate contentKey="piro360App.pirovideo.owner">Owner</Translate>
                </th>
                <th />
              </tr>
            </thead>
            <tbody>
              {pirovideoList.map((pirovideo, i) => (
                <tr key={`entity-${i}`} data-cy="entityTable">
                  <td>
                    <Button tag={Link} to={`${match.url}/${pirovideo.id}`} color="link" size="sm">
                      {pirovideo.id}
                    </Button>
                  </td>
                  <td>{pirovideo.title}</td>
                  <td>{pirovideo.notes}</td>
                  <td>{pirovideo.location}</td>
                  <td>{pirovideo.vidurl}</td>
                  <td>{pirovideo.recordDate ? <TextFormat type="date" value={pirovideo.recordDate} format={APP_DATE_FORMAT} /> : null}</td>
                  <td>{pirovideo.owner ? <Link to={`person-piro-v/${pirovideo.owner.id}`}>{pirovideo.owner.id}</Link> : ''}</td>
                  <td className="text-end">
                    <div className="btn-group flex-btn-group-container">
                      <Button tag={Link} to={`${match.url}/${pirovideo.id}`} color="info" size="sm" data-cy="entityDetailsButton">
                        <FontAwesomeIcon icon="eye" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.view">View</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${pirovideo.id}/edit`} color="primary" size="sm" data-cy="entityEditButton">
                        <FontAwesomeIcon icon="pencil-alt" />{' '}
                        <span className="d-none d-md-inline">
                          <Translate contentKey="entity.action.edit">Edit</Translate>
                        </span>
                      </Button>
                      <Button tag={Link} to={`${match.url}/${pirovideo.id}/delete`} color="danger" size="sm" data-cy="entityDeleteButton">
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
              <Translate contentKey="piro360App.pirovideo.home.notFound">No Pirovideos found</Translate>
            </div>
          )
        )}
      </div>
    </div>
  );
};

export default PirovideoPiroV;
