import React from 'react';
import { Switch } from 'react-router-dom';

// eslint-disable-next-line @typescript-eslint/no-unused-vars
import ErrorBoundaryRoute from 'app/shared/error/error-boundary-route';

import PirovideoPiroV from './pirovideo-piro-v';
import TagPiroV from './tag-piro-v';
import PersonPiroV from './person-piro-v';
/* jhipster-needle-add-route-import - JHipster will add routes here */

const Routes = ({ match }) => (
  <div>
    <Switch>
      {/* prettier-ignore */}
      <ErrorBoundaryRoute path={`${match.url}pirovideo-piro-v`} component={PirovideoPiroV} />
      <ErrorBoundaryRoute path={`${match.url}tag-piro-v`} component={TagPiroV} />
      <ErrorBoundaryRoute path={`${match.url}person-piro-v`} component={PersonPiroV} />
      {/* jhipster-needle-add-route-path - JHipster will add routes here */}
    </Switch>
  </div>
);

export default Routes;
