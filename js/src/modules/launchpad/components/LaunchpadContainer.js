import Launchpad from "./Launchpad";
import {withRouter} from 'react-router-dom';
import {connect} from 'react-redux';
import {createStructuredSelector} from 'reselect';
import {Action, Dispatch} from 'redux';
import {bindActionCreators} from 'redux';
import {loadUsers,authUser} from "../actions/userActions";
import {getUserInfo, getIsLoading, getFilesInfo} from "../selectors";
import {loadFiles} from "../actions/fileActions";

const mapStateToProps = () => createStructuredSelector({
    isLoading: getIsLoading,
    userState: getUserInfo,
    filesState: getFilesInfo,
});

const mapDispatchToProps = (dispatch: Dispatch<Action>) =>
    bindActionCreators(
        {
            loadUsers,
            loadFiles,
            authUser,
        },
        dispatch,
    );
const LaunchpadContainer = withRouter(connect(mapStateToProps, mapDispatchToProps)(Launchpad));

export default LaunchpadContainer;