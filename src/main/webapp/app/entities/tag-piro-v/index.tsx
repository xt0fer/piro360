import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import TagPiroV from './tag-piro-v';
import TagPiroVDetail from './tag-piro-v-detail';
import TagPiroVUpdate from './tag-piro-v-update';
import TagPiroVDeleteDialog from './tag-piro-v-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={TagPiroVUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={TagPiroVUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={TagPiroVDetail} />
      <ErrorBoundaryRoute path={match.url} component={TagPiroV} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={TagPiroVDeleteDialog} />
  </>
);

export default Routes;
