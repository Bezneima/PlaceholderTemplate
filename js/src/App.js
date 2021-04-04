import React from 'react';
import {Route, Switch} from 'react-router-dom';
import './App.css';
import Loadable from 'react-loadable';
import Loading from '../src/common/components/Loading';
import 'rsuite/dist/styles/rsuite-default.css';

const LaunchpadContainer = Loadable({
    loader: () =>
        import(
            './modules/launchpad/components/LaunchpadContainer'
            ),
    loading: Loading,
});
const ProfileContainer = Loadable({
    loader: () =>
        import(
            './modules/profile/components/ProfileContainer'
            ),
    loading: Loading,
});

const App = () => (
    <Switch>
        <Route exact path="/" component={LaunchpadContainer} />
        <Route exact path="/profile" component={ProfileContainer} />
    </Switch>
);

export default App;
