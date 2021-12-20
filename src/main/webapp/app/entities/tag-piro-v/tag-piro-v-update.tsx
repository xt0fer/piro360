import React, { useState, useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col, FormText } from 'reactstrap';
import { isNumber, Translate, translate, ValidatedField, ValidatedForm } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { IPirovideoPiroV } from 'app/shared/model/pirovideo-piro-v.model';
import { getEntities as getPirovideos } from 'app/entities/pirovideo-piro-v/pirovideo-piro-v.reducer';
import { getEntity, updateEntity, createEntity, reset } from './tag-piro-v.reducer';
import { ITagPiroV } from 'app/shared/model/tag-piro-v.model';
import { convertDateTimeFromServer, convertDateTimeToServer, displayDefaultDateTime } from 'app/shared/util/date-utils';
import { mapIdList } from 'app/shared/util/entity-utils';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const TagPiroVUpdate = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  const [isNew] = useState(!props.match.params || !props.match.params.id);

  const pirovideos = useAppSelector(state => state.pirovideo.entities);
  const tagEntity = useAppSelector(state => state.tag.entity);
  const loading = useAppSelector(state => state.tag.loading);
  const updating = useAppSelector(state => state.tag.updating);
  const updateSuccess = useAppSelector(state => state.tag.updateSuccess);
  const handleClose = () => {
    props.history.push('/tag-piro-v');
  };

  useEffect(() => {
    if (isNew) {
      dispatch(reset());
    } else {
      dispatch(getEntity(props.match.params.id));
    }

    dispatch(getPirovideos({}));
  }, []);

  useEffect(() => {
    if (updateSuccess) {
      handleClose();
    }
  }, [updateSuccess]);

  const saveEntity = values => {
    values.commentDate = convertDateTimeToServer(values.commentDate);

    const entity = {
      ...tagEntity,
      ...values,
      pirovideo: pirovideos.find(it => it.id.toString() === values.pirovideo.toString()),
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
          commentDate: displayDefaultDateTime(),
        }
      : {
          ...tagEntity,
          commentDate: convertDateTimeFromServer(tagEntity.commentDate),
          pirovideo: tagEntity?.pirovideo?.id,
        };

  return (
    <div>
      <Row className="justify-content-center">
        <Col md="8">
          <h2 id="piro360App.tag.home.createOrEditLabel" data-cy="TagCreateUpdateHeading">
            <Translate contentKey="piro360App.tag.home.createOrEditLabel">Create or edit a Tag</Translate>
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
                  id="tag-piro-v-id"
                  label={translate('global.field.id')}
                  validate={{ required: true }}
                />
              ) : null}
              <ValidatedField
                label={translate('piro360App.tag.contents')}
                id="tag-piro-v-contents"
                name="contents"
                data-cy="contents"
                type="text"
              />
              <ValidatedField
                label={translate('piro360App.tag.location')}
                id="tag-piro-v-location"
                name="location"
                data-cy="location"
                type="text"
              />
              <ValidatedField
                label={translate('piro360App.tag.commentDate')}
                id="tag-piro-v-commentDate"
                name="commentDate"
                data-cy="commentDate"
                type="datetime-local"
                placeholder="YYYY-MM-DD HH:mm"
              />
              <ValidatedField
                id="tag-piro-v-pirovideo"
                name="pirovideo"
                data-cy="pirovideo"
                label={translate('piro360App.tag.pirovideo')}
                type="select"
              >
                <option value="" key="0" />
                {pirovideos
                  ? pirovideos.map(otherEntity => (
                      <option value={otherEntity.id} key={otherEntity.id}>
                        {otherEntity.id}
                      </option>
                    ))
                  : null}
              </ValidatedField>
              <Button tag={Link} id="cancel-save" data-cy="entityCreateCancelButton" to="/tag-piro-v" replace color="info">
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

export default TagPiroVUpdate;
