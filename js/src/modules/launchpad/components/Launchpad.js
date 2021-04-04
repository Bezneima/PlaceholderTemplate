import React from 'react';
import {RouteComponentProps} from 'react-router-dom';
import AuthFormComponent from "./AuthFormComponent";
import LaunchpadHeader from "./LaunchpadHeader";

interface Props extends RouteComponentProps<{}> {
    loadUsers: () => { type: string };
    authUser: () => { type: string };
    userState: {
        isLoading: boolean;
        isAuth: boolean;
    };
    backURL: () => {type: string};
}

class Launchpad extends React.Component<Props> {
    componentDidMount() {
        this.backURL = 'http://localhost:8080/';
        this.props.loadUsers(localStorage.getItem('token'), localStorage.getItem('login'));
    }

    render() {
        const {isAuth} = this.props.userState;
        const {authUser, userState} = this.props;
        console.log(this.props);
        if (isAuth)
            return <div><LaunchpadHeader backURL={this.backURL} username={localStorage.getItem('login')}/></div>;
        else {
            return <AuthFormComponent authUser={authUser} userState={userState}/>;
        }
    }

}

export default Launchpad;