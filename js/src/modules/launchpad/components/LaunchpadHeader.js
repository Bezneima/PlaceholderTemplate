import React from "react";
import styled from 'styled-components'
import {RouteComponentProps} from "react-router-dom";
import {Dropdown} from "rsuite";
import { Icon } from 'rsuite';

export const HeaderContainer = styled.div`
    height: 40px;
    background-color: #272c36;
    display: flex;
    justify-content: space-between;
    align-items: center;
    color: #fff;
    padding: 0 10px;
`
export const headerLeft = styled.div`
    display: inline-block;
`
export const headerRight = styled.div`
    display: inline-block;
`

interface props extends RouteComponentProps<{}> {
  username: ''
}

function logOut(){
    localStorage.removeItem('login');
    localStorage.removeItem('token');
    window.location.reload()
}

function LaunchpadHeader(props){
    return <HeaderContainer>
        <headerLeft>
            <Icon icon = "file-text" size = "2x"/>
        </headerLeft>
        <headerRight>

            <Dropdown title={props.username} placement="bottomEnd">
                <Dropdown.Item icon={<Icon icon="avatar"/>}>Профиль</Dropdown.Item>
                <Dropdown.Item onSelect={logOut}  icon={<Icon icon="sign-out"/>}>Выход</Dropdown.Item>
            </Dropdown>
        </headerRight>
    </HeaderContainer>
}
export default LaunchpadHeader