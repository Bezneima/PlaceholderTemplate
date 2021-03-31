import Launchpad from "./Launchpad";
import {withRouter} from 'react-router-dom';
import {connect} from 'react-redux';
import {createStructuredSelector} from 'reselect';
import {Action, Dispatch} from 'redux';
import {bindActionCreators} from 'redux';
import {loadUsers,authUser} from "../actions";
import {getUserInfo, getIsLoading} from "../selectors";

const mapStateToProps = () => createStructuredSelector({
    isLoading: getIsLoading,
    userState: getUserInfo,
});

const mapDispatchToProps = (dispatch: Dispatch<Action>) =>
    bindActionCreators(
        {
            loadUsers,
            authUser,
        },
        dispatch,
    );
const LaunchpadContainer = withRouter(connect(mapStateToProps, mapDispatchToProps)(Launchpad));

export default LaunchpadContainer;