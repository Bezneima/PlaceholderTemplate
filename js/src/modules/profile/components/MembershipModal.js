import React, {useEffect, useState} from "react";
import {
    Button,
    ButtonToolbar,
    CheckPicker,
    ControlLabel,
    Form,
    FormGroup,
    Modal,
    Notification,
    SelectPicker
} from "rsuite";
import axios from "axios";

const ButtonToolbarStyle = {
    color: 'blue',
    textAlign: "right",
};
function MembershipModal({opened, close, loadUsersGroups, loadUsersNames, profileState, loadGroups}){
    const [usersGroups, setUsersGroups] = useState([])
    const [selectedUser, setSelectedUser] = useState('');
    useEffect(() => {
        setSelectedUser('');
        loadGroups();
        loadUsersNames();
    }, []);
    useEffect(() => {
        setUsersGroups([]);
        loadUsersGroups(selectedUser);
    }, [selectedUser])
    useEffect(() => {
        if(profileState.usersGroups !== undefined)
            setUsersGroups(profileState.usersGroups.map(group => {
                return group.value
            }))
    }, [profileState.usersGroups]);
    return(
        <div className="modal-container">
            <Modal show={opened} onHide={()=> {
                close()
            }}>
                <Modal.Header>
                    <Modal.Title>Изменить составы групп: </Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    <Form onSubmit={() => {
                        axios.post(`http://localhost:8080/users/setUserToGroups?userName=${selectedUser}`, usersGroups)
                            .then(res => {
                                Notification['success']({
                                    title: 'Success',
                                    description: <p>{res.data}</p>
                                });
                            })
                            .then(() => {
                                setUsersGroups([]);
                                setSelectedUser('')
                            })
                            .then(() => {
                                loadGroups();
                            })
                            .catch(err => {
                                console.log(err)
                            })
                    }}>
                        <FormGroup>
                            <ControlLabel>Имя пользователя</ControlLabel>
                            <SelectPicker
                                style={{width: '90%'}}
                                data={profileState.usersNames}
                                placeholder="Выберите пользователя"
                                searchable={false}
                                onChange={(e) => {
                                    setSelectedUser(e)
                                }}
                            />
                            <br/>
                            <ControlLabel>Добавьте или удалите группы, в которых пользователь будет
                                состоять: </ControlLabel>
                            <CheckPicker
                                data={profileState.groupsNames}
                                searchable={false}
                                style={{ width: '90%' }}
                                placeholder="Выберите группы"
                                onChange={(value) => {
                                    setUsersGroups(value)
                                }}
                                value={usersGroups}
                            />
                        </FormGroup>
                        <FormGroup style={ButtonToolbarStyle}>
                            <ButtonToolbar>
                                <Button
                                    onClick={()=> {
                                        close()
                                    }}
                                    type="submit"
                                    appearance="primary"
                                    disabled={selectedUser === ''}>
                                    Ok
                                </Button>
                                <Button onClick={()=> {
                                    setSelectedUser('');
                                    setUsersGroups([]);
                                    close();
                                }} appearance="subtle">
                                    Cancel
                                </Button>
                            </ButtonToolbar>
                        </FormGroup>
                    </Form>
                </Modal.Body>
            </Modal>
        </div>
    )
}

export default MembershipModal;