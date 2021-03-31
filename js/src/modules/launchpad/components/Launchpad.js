import React from 'react';
import {RouteComponentProps} from 'react-router-dom';
import AuthFormComponent from "./AuthFormComponent";

interface Props extends RouteComponentProps<{}> {
    loadUsers: () => { type: string };
    authUser: () => { type: string };
    userState: {
        isLoading: boolean;
        isAuth: boolean;
    }
}

class Launchpad extends React.Component<Props> {
    componentDidMount() {

        this.props.loadUsers(localStorage.getItem('token'), localStorage.getItem('login'));

    }

    render() {
        const {isAuth} = this.props.userState;
        const {authUser, userState} = this.props;
        console.log(this.props);
        if (isAuth)
            return <div>{"Основной контейнер"}</div>;
        else {
            return <AuthFormComponent authUser={authUser} userState={userState}/>;
        }
    }

}

export default Launchpad;