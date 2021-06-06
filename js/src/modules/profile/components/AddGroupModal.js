import React, {useState} from 'react';
import {
    Button,
    ButtonToolbar,
    ControlLabel,
    Form,
    FormControl,
    FormGroup,
    HelpBlock,
    Modal,
    Notification
} from "rsuite";
import axios from "axios";

const ButtonToolbarStyle = {
    color: 'blue',
    textAlign: "right",
};

function AddGroupModal({opened, close, profileState, loadGroups}){
    const [groupName, setGroupName] = useState('');
    return(
        <div className="modal-container">
            <Modal show={opened} onHide={()=> {
                setGroupName('');
                close()
            }}>
                <Modal.Header>
                    <Modal.Title>Добавить группу: </Modal.Title>
                </Modal.Header>
                <Modal.Body>
                    <Form onSubmit={() => {
                        axios.post(`http://localhost:8080/users/addGroup?groupName=${groupName}&userName=${profileState.userName}`)
                            .then(res => {
                                Notification['success']({
                                    title: 'Success',
                                    description: <p>{res.data}</p>
                                });
                            })
                            .then(() => {
                                loadGroups();
                                setGroupName('');
                            })
                            .catch(err => {
                                console.log(err)
                            })
                    }}>
                        <FormGroup>
                            <ControlLabel>Имя группы</ControlLabel>
                            <FormControl
                                onChange={(value)=>{
                                    setGroupName(value);
                                }}
                                name="name"
                            />
                            <HelpBlock>Required</HelpBlock>
                        </FormGroup>
                        <FormGroup style={ButtonToolbarStyle}>
                            <ButtonToolbar>
                                <Button
                                    onClick={()=> {
                                        close();
                                    }}
                                    type="submit"
                                    appearance="primary"
                                    disabled={groupName === ''}
                                >
                                    Ok
                                </Button>
                                <Button onClick={()=> {
                                    setGroupName('');
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

export default AddGroupModal;