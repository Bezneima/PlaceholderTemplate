import React from "react";
import {Icon, Nav} from "rsuite";
import {HeaderContainer, HeaderLeft, HeaderRight} from "../../launchpad/components/LaunchpadHeader";

function back(){
    window.location.href = '/'
}

function ProfileHeader(props){
    return (
        <HeaderContainer>
            <HeaderLeft>
                <Icon icon = "file-text" size = "2x"/>
            </HeaderLeft>
            <HeaderRight>
                <Nav>
                    <Nav.Item onSelect={back}><Icon icon='arrow-left'/> Назад</Nav.Item>
                </Nav>
            </HeaderRight>
        </HeaderContainer>
    )
}

export default ProfileHeader;