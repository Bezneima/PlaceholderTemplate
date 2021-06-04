import {RouteComponentProps} from "react-router-dom";
import React from "react";
import 'rsuite/dist/styles/rsuite-dark.css';

import {loadGroups, loadProfile, loadUsersGroups, loadUsersNames} from "../actions/profileAction";
import ProfileHeader from "./ProfileHeader";
import ProfileBody from "./ProfileBody";

interface Props extends RouteComponentProps<{}> {
    loadProfile: () => { type: string };
    loadGroups: () => { type: string };
    loadUsersNames: () => { type: string };
    loadUsersGroups: () => { type: string };
    profileState: {
        isLoading: boolean,
    },
    backURL: () => { type: string };
}

class Profile extends React.Component<Props> {
    componentDidMount() {
        this.backURL = 'http://localhost:8080/';
        this.props.loadProfile(localStorage.getItem('token'), localStorage.getItem('login'));
        this.userLogin = localStorage.getItem('login');
        this.userAvatar = this.backURL + "users/getAvatar?userToken=" + localStorage.getItem('token');
    }

    render() {
        return (
            <>
                <ProfileHeader/>
                <ProfileBody
                    profileState={this.props.profileState}
                    loadUsersNames={this.props.loadUsersNames}
                    loadGroups={this.props.loadGroups}
                    loadUsersGroups={this.props.loadUsersGroups}
                    login={this.userLogin}
                    avatar={this.userAvatar}
                />
            </>
        );
    }
}

export default Profile;