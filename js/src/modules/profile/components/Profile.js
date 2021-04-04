import {RouteComponentProps} from "react-router-dom";
import React from "react";
import {loadProfile} from "../actions/profileAction";

interface Props extends RouteComponentProps<{}> {
    loadProfile: () => { type: string };
    profileState: {
        isLoading: boolean,
    },
    backURL: () => {type: string};
}
class Profile extends React.Component<Props> {
    componentDidMount() {
        this.backURL = 'http://localhost:8080/';
        this.props.loadProfile(localStorage.getItem('token'), localStorage.getItem('login'));
    }

    render() {
        return <div>
            <div>
                <img src={this.backURL+"users/getAvatar?userToken="+localStorage.getItem('token')}/>
                <h3>{localStorage.getItem('login')}</h3>
            </div>

        </div>;
    }
}
export default Profile;