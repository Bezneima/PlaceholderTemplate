import {RouteComponentProps} from "react-router-dom";
import React from "react";
import {loadProfile} from "../actions/profileAction";

interface Props extends RouteComponentProps<{}> {
    loadProfile: () => { type: string };
    profileState: {
        isLoading: boolean,
    }
}
class Profile extends React.Component<Props> {
    componentDidMount() {
        console.log(this.props)
        this.props.loadProfile(localStorage.getItem('token'), localStorage.getItem('login'));
    }

    render() {

        return <div>
            <div>

            </div>
        </div>;
    }
}
export default Profile;