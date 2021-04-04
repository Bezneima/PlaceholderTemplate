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
export const HeaderLeft = styled.div`
    display: inline-block;
`
export const HeaderRight = styled.div`
    display: inline-block;
`;
const AvatarStyle = {
    borderRadius: 50,
    marginRight: 10,
}

interface props extends RouteComponentProps<{}> {
  username: '';
}

function logOut(){
    localStorage.removeItem('login');
    localStorage.removeItem('token');
    window.location.reload()
}
function profile(){
    window.location.href = '/profile'
}
function LaunchpadHeader(props){
    return <HeaderContainer>
        <HeaderLeft>
            <Icon icon = "file-text" size = "2x"/>
        </HeaderLeft>
        <HeaderRight>
            <img style={AvatarStyle} src={props.backURL+"users/getAvatar?userToken="+localStorage.getItem('token')}  height={30} width={30} alt=""/>
            <Dropdown title={props.username} placement="bottomEnd">
                <Dropdown.Item onSelect={profile} icon={<Icon icon="avatar"/>}>Профиль</Dropdown.Item>
                <Dropdown.Item onSelect={logOut}  icon={<Icon icon="sign-out"/>}>Выход</Dropdown.Item>
            </Dropdown>
        </HeaderRight>
    </HeaderContainer>
}
export default LaunchpadHeader