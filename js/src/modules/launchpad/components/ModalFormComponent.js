import React, {useEffect, useState} from 'react';
import {Button, ButtonToolbar, ControlLabel, Divider, Form, FormControl, FormGroup, HelpBlock} from "rsuite";
import axios from "axios";


function ModalFormComponent(props) {
    const {close, inputFieldsNames, modalState, fileHashName, fileName} = props;
    const [forms, setForms] = useState([])
    const [filesQuantity, setFilesQuantity] = useState(1)
    let formParams = null;
    const allInputFields = JSON.parse(inputFieldsNames);
    const renderedInputFields = allInputFields.map((field, index) =>
        <FormGroup>
            <ControlLabel>{field.value}</ControlLabel>
            <FormControl onChange={
                (value) => {
                    let item;
                    item = modalState[modalState.length-1].find(obj => obj.key === field.key)
                    if (item === undefined) {
                        modalState[modalState.length-1].push({key: field.key, value: field.value, valueTo: value});
                    } else {
                        modalState[modalState.length-1].pop();
                        modalState[modalState.length-1].push({key: field.key, value: field.value, valueTo: value});
                    }
                    console.log(modalState)
                }
            } name={field.key}/>
            <HelpBlock>Required</HelpBlock>
        </FormGroup>)

    useEffect(() => {
        modalState.push([]);
        setForms([renderedInputFields, ...forms])
    }, [])
    return (
        <Form onSubmit={() => {
            let data = [];
            modalState.forEach(item => {
                data.push({key: item.value, value: item.valueTo});
            });
            console.log("me", modalState);
            axios.post(`http://localhost:8080/files/downloadFilledTemplate?groupName=1&fileHashName=${fileHashName}&fileName=${fileName}`, data)
                .then(function (response) {
                    close();
                    console.log(response);
                    window.location.href = `http://localhost:8080/files/download?groupName=&isTemplate=true&fileHashName=${response.data}&fileName=${fileName}`
                })
                .catch(function (error) {
                    console.log(error);
                });

        }} layout="horizontal">
            {forms}
            <FormGroup>
                <ButtonToolbar>
                    <Button type="submit" appearance="primary">Submit</Button>
                    <Button onClick={close} appearance="default">Cancel</Button>
                    <Button onClick={() => {
                        modalState.push([]);
                        setFilesQuantity(filesQuantity + 1)
                        setForms([...forms,
                                <>
                                    <Divider/>
                                    {filesQuantity+1}
                                    {renderedInputFields}
                                </>
                            ]
                        )
                    }
                    } appearance="default">New File</Button>
                </ButtonToolbar>
            </FormGroup>
        </Form>)
}

export default ModalFormComponent;

