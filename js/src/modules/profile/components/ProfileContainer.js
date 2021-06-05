import Profile from "./Profile";
import {createStructuredSelector} from "reselect";
import {bindActionCreators} from "redux";
import {Action, Dispatch} from "redux";
import {withRouter} from "react-router-dom";
import {connect} from "react-redux";
import {loadGroups, loadProfile, loadUsersGroups, loadUsersNames} from "../actions/profileAction";
import {getIsLoading, getProfileInfo} from "../selectors";

const mapStateToProps = () => createStructuredSelector({
    isLoading: getIsLoading,
    profileState: getProfileInfo
});

const mapDispatchToProps = (dispatch: Dispatch<Action>) =>
    bindActionCreators(
        {
            loadProfile,
            loadGroups,
            loadUsersNames,
            loadUsersGroups,
        },
        dispatch,
    );


const ProfileContainer = withRouter(connect(mapStateToProps, mapDispatchToProps)(Profile));

export default ProfileContainer;