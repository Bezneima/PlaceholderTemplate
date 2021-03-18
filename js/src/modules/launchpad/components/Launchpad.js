import React from 'react';
import {RouteComponentProps} from 'react-router-dom';

interface Props extends RouteComponentProps<{}> {
    loadUsers: () => { type: string };
    isLoading:boolean;
    isAuth:boolean;
}

class Launchpad extends React.Component<Props> {
    componentDidMount() {//localStorage.getItem('token')
        this.props.loadUsers("last_user_token","user_name");
    }
    render() {
        console.log(this.props);
        return <div>{"Какой-то текст"}</div>
    }

}

export default Launchpad;