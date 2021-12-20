import React from 'react';
import { Switch } from 'react-router-dom';

import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import PirovideoPiroV from './pirovideo-piro-v';
import PirovideoPiroVDetail from './pirovideo-piro-v-detail';
import PirovideoPiroVUpdate from './pirovideo-piro-v-update';
import PirovideoPiroVDeleteDialog from './pirovideo-piro-v-delete-dialog';

const Routes = ({ match }) => (
  <>
    <Switch>
      <ErrorBoundaryRoute exact path={`${match.url}/new`} component={PirovideoPiroVUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id/edit`} component={PirovideoPiroVUpdate} />
      <ErrorBoundaryRoute exact path={`${match.url}/:id`} component={PirovideoPiroVDetail} />
      <ErrorBoundaryRoute path={match.url} component={PirovideoPiroV} />
    </Switch>
    <ErrorBoundaryRoute exact path={`${match.url}/:id/delete`} component={PirovideoPiroVDeleteDialog} />
  </>
);

export default Routes;
