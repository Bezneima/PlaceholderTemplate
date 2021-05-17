import React from 'react';
import {RouteComponentProps} from 'react-router-dom';
import AuthFormComponent from "./AuthFormComponent";
import LaunchpadHeader from "./LaunchpadHeader";
import LaunchpadBody from "./LaunchpadBody";
import {deleteFile, loadFiles} from "../actions/fileActions";

interface Props extends RouteComponentProps<{}> {
    loadUsers: () => { type: string };
    loadFiles: () => { type: string };
    deleteFile: () => {type: string};
    authUser: () => { type: string };
    filesState: {
        files:[];
        isLoading:boolean;
        modalState: {};
    };
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
        this.props.loadFiles(localStorage.getItem('token'), localStorage.getItem('login'));
    }

    render() {
        const {isAuth} = this.props.userState;
        const {authUser, userState,filesState} = this.props;
        if (isAuth)
            return (
                <div>
                    <LaunchpadHeader backURL={this.backURL} username={localStorage.getItem('login')}/>
                    <LaunchpadBody filesState={filesState} deleteFile={this.props.deleteFile} userState={userState}/>
                </div>
            );
        else {
            return <AuthFormComponent authUser={authUser} userState={userState}/>;
        }
    }

}

export default Launchpad;