import React from 'react';
import type {FileInfo} from "../model";
import {RouteComponentProps} from 'react-router-dom';

interface Props extends RouteComponentProps<{}> {
    filter: string;
    items: Array<FileInfo>;
    loadLaunchpad: () => { type: string };
}

class Launchpad extends React.Component<Props> {
    componentDidMount() {
        console.log(this.props.loadLaunchpad());
        this.props.loadLaunchpad();
    }
    render() {
        return <div>{"Какой-то текст"}</div>
    }

}

export default Launchpad;