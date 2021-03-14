import React from 'react';
import Launchpad from "./Launchpad";
import {withRouter} from 'react-router-dom';
import {connect} from 'react-redux';
import {createStructuredSelector} from 'reselect';
import {Action, Dispatch} from 'redux';
import {bindActionCreators} from 'redux';
import {loadLaunchpad} from "../actions";
import {getAllFiles, getIsLoading} from "../selectors";

const mapStateToProps = createStructuredSelector({
    isLoading: getIsLoading,
    files: getAllFiles,
});

const mapDispatchToProps = (dispatch: Dispatch<Action>) =>
    bindActionCreators(
        {
            loadLaunchpad,
        },
        dispatch,
    );

const LaunchpadContainer = withRouter(connect(mapStateToProps, mapDispatchToProps)(Launchpad));

export default LaunchpadContainer;