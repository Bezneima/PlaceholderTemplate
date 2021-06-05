import React, {useEffect, useState} from "react";
import Select from 'react-select';
import {Button, ButtonToolbar, ControlLabel, Form, FormGroup} from "rsuite";
import axios from "axios";

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
const ButtonToolbarStyle = {
    color: 'blue',
    textAlign: "right",
};

function ProfileBody({loadUsersGroups, loadUsersNames, profileState, loadGroups, avatar, login}) {
    const [usersGroups, setUsersGroups] = useState([])
    const [selectedUser, setSelectedUser] = useState('');
    useEffect(() => {
        loadGroups();
        loadUsersNames();
    }, []);
    useEffect(() => {
        loadUsersGroups(selectedUser);
    }, [selectedUser])
    useEffect(() => {
        setUsersGroups(profileState.usersGroups)
    }, [profileState.usersGroups]);
    return (
        <div style={BodyWrapper}>
            <img alt={"avatar"} style={Avatar} src={avatar}/>
            <div style={descriptionWrapper}>
                <h3 style={{color: '#fff', marginBottom: '10px'}}>{login}</h3>
                <p style={{color: '#fff', fontSize: '16px', marginBottom: '10px'}}>Изменить составы групп: </p>
                <Form onSubmit={() => {
                    console.log(usersGroups)
                    axios.post(`http://localhost:8080/users/setUserToGroups?userName=${selectedUser}`, usersGroups)
                        .then(res => {
                            console.log(res.data)
                        })
                        .catch(err => {
                            console.log(err)
                        })
                }}>
                    <FormGroup>
                        <ControlLabel>Имя пользователя</ControlLabel>
                        <Select
                            closeMenuOnSelect={true}
                            options={profileState.usersNames}
                            className="basic-multi-select"
                            placeholder="Выберите пользователя"
                            onChange={(e) => {
                                setSelectedUser(e.value)
                            }}
                        />
                        <ControlLabel>Добавьте или удалите группы, в которых пользователь будет
                            состоять: </ControlLabel>
                        <Select
                            value={usersGroups}
                            isMulti
                            name="colors"
                            className="basic-multi-select"
                            classNamePrefix="select"
                            placeholder="Выберите группы"
                            onChange={(value) => {
                                setUsersGroups(value)
                            }}
                            options={profileState.groupsNames}
                        />
                    </FormGroup>
                    <FormGroup style={ButtonToolbarStyle}>
                        <ButtonToolbar>
                            <Button
                                type="submit"
                                appearance="primary"
                                disabled={selectedUser === ''}>
                                Submit
                            </Button>
                        </ButtonToolbar>
                    </FormGroup>
                </Form>

            </div>
        </div>
    )
}

export default ProfileBody;