import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IPersonPiroV } from 'app/shared/model/person-piro-v.model';
import { getEntities as getPeople } from 'app/entities/person-piro-v/person-piro-v.reducer';
import { getEntity, updateEntity, createEntity, reset } from './pirovideo-piro-v.reducer';
import { IPirovideoPiroV } from 'app/shared/model/pirovideo-piro-v.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const PirovideoPiroVUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const people = useAppSelector(state => state.person.entities);
  const pirovideoEntity = useAppSelector(state => state.pirovideo.entity);
  const loading = useAppSelector(state => state.pirovideo.loading);
  const updating = useAppSelector(state => state.pirovideo.updating);
  const updateSuccess = useAppSelector(state => state.pirovideo.updateSuccess);
  const handleClose = () => {
    props.history.push('/pirovideo-piro-v');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getPeople({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    values.recordDate = convertDateTimeToServer(values.recordDate);

    const entity = {
      ...pirovideoEntity,
      ...values,
      owner: people.find(it => it.id.toString() === values.owner.toString()),
    };

    if (isNew) {
      dispatch(createEntity(entity));
    } else {
      dispatch(updateEntity(entity));
    }
  };

  const defaultValues = () =>
    isNew
      ? {
          recordDate: displayDefaultDateTime(),
        }
      : {
          ...pirovideoEntity,
          recordDate: convertDateTimeFromServer(pirovideoEntity.recordDate),
          owner: pirovideoEntity?.owner?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="piro360App.pirovideo.home.createOrEditLabel" data-cy="PirovideoCreateUpdateHeading">
            <Translate contentKey="piro360App.pirovideo.home.createOrEditLabel">Create or edit a Pirovideo</Translate>
          </h2>
        </Col>
      </Row>
      <Row className="justify-content-center">
        <Col md="8">
          {loading ? (
            <p>Loading...</p>
          ) : (
            <ValidatedForm defaultValues={defaultValues()} onSubmit={saveEntity}>
              {!isNew ? (
                <ValidatedField
                  name="id"
                  required
                  readOnly
                  id="pirovideo-piro-v-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('piro360App.pirovideo.title')}
                id="pirovideo-piro-v-title"
                name="title"
                data-cy="title"
                type="text"
              />
              <ValidatedField
                label={translate('piro360App.pirovideo.notes')}
                id="pirovideo-piro-v-notes"
                name="notes"
                data-cy="notes"
                type="text"
              />
              <ValidatedField
                label={translate('piro360App.pirovideo.location')}
                id="pirovideo-piro-v-location"
                name="location"
                data-cy="location"
                type="text"
              />
              <ValidatedField
                label={translate('piro360App.pirovideo.vidurl')}
                id="pirovideo-piro-v-vidurl"
                name="vidurl"
                data-cy="vidurl"
                type="text"
              />
              <ValidatedField
                label={translate('piro360App.pirovideo.recordDate')}
                id="pirovideo-piro-v-recordDate"
                name="recordDate"
                data-cy="recordDate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                id="pirovideo-piro-v-owner"
                name="owner"
                data-cy="owner"
                label={translate('piro360App.pirovideo.owner')}
                type="select"
              >
                <option value="" key="0" />
                {people
                  ? people.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/pirovideo-piro-v" replace color="info">
                <FontAwesomeIcon icon="arrow-left" />
                &nbsp;
                <span className="d-none d-md-inline">
                  <Translate contentKey="entity.action.back">Back</Translate>
                </span>
              </Button>
              &nbsp;
              <Button color="primary" id="save-entity" data-cy="entityCreateSaveButton" type="submit" disabled={updating}>
                <FontAwesomeIcon icon="save" />
                &nbsp;
                <Translate contentKey="entity.action.save">Save</Translate>
              </Button>
            </ValidatedForm>
          )}
        </Col>
      </Row>
    </div>
  );
};

export default PirovideoPiroVUpdate;
