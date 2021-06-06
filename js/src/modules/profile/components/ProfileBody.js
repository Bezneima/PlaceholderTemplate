import React, {useEffect, useState} from "react";
import Select from 'react-select';
import {Button, ButtonToolbar, ControlLabel, Form, FormGroup} from "rsuite";
import axios from "axios";
import MembershipModal from "./MembershipModal";
import AddGroupModal from "./AddGroupModal";
import AddUserModal from "./AddUserModal";

const BodyWrapper = {
    margin: 'auto',
    marginTop: '50px',
    maxWidth: '1060px',
    display: 'flex',
    justifyContent: 'flex-start',
    flexWrap: 'wrap'
}
const descriptionWrapper = {
    flexDirection: 'column',
    color: '#333',
    minWidth: '50%'
}
const Avatar = {
    marginRight: '50px',
    borderRadius: '6%'
}

function ProfileBody({loadUsersGroups, loadUsersNames, profileState, loadGroups, avatar, login}) {
    const [isMembershipModalOpened, setIsMembershipModalOpened] = useState(false);
    const [isAddGroupModalOpened, setIsAddGroupModalOpened] = useState(false);
    const [isAddUserModalOpened, setIsAddUserModalOpened] = useState(false)
    const openMembershipModal = () => {
        setIsMembershipModalOpened(true)
    }
    const openAddGroupModal = () => {
        setIsAddGroupModalOpened(true);
    }
    const openAddUserModal = () => {
        setIsAddUserModalOpened(true);
    }
    const closeAllModals = () => {
        setIsMembershipModalOpened(false)
        setIsAddGroupModalOpened(false)
        setIsAddUserModalOpened(false)
    }

    return (
        <div style={BodyWrapper}>
            <img alt={"avatar"} style={Avatar} src={avatar}/>
            <div style={descriptionWrapper}>
                <h3 style={{color: '#fff', marginBottom: '10px'}}>{login}</h3>
                <Button onClick={() => openMembershipModal()}>Изменить составы групп</Button>
                <Button onClick={() => openAddGroupModal()}>Добавить группу</Button>
                <Button onClick={() => openAddUserModal()}>Добавить пользователя</Button>
                <MembershipModal
                    opened={isMembershipModalOpened}
                    close={closeAllModals}
                    loadUsersNames={loadUsersNames}
                    profileState={profileState}
                    avatar={avatar}
                    loadGroups={loadGroups}
                    loadUsersGroups={loadUsersGroups}
                    login={login}
                />
                <AddGroupModal
                    loadGroups={loadGroups}
                    profileState={profileState}
                    opened={isAddGroupModalOpened}
                    close={closeAllModals}
                />
                {/*<AddUserModal
                    loadUsersNames={loadUsersNames}
                    profileState={profileState}
                    opened={isAddUserModalOpened}
                    close={closeAllModals}
                />*/}


            </div>
        </div>
    )
}

export default ProfileBody;