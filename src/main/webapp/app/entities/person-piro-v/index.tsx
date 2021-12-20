import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import PersonPiroV from './person-piro-v';
import PersonPiroVDetail from './person-piro-v-detail';
import PersonPiroVUpdate from './person-piro-v-update';
import PersonPiroVDeleteDialog from './person-piro-v-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={PersonPiroVUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={PersonPiroVUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={PersonPiroVDetail} />
      <ErrorBoundaryRoute path={match.url} component={PersonPiroV} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={PersonPiroVDeleteDialog} />
  </>
);

export default Routes;
