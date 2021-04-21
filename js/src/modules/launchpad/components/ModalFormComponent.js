import React, {useEffect} from 'react';
import {Button, ButtonToolbar, ControlLabel, Form, FormControl, FormGroup, HelpBlock} from "rsuite";
import axios from "axios";


function ModalFormComponent(props) {
    const {close, inputFieldsNames, modalState} = props;
    let formParams = null;
    const allInputFields = JSON.parse(inputFieldsNames);
    const renderedInputFields = allInputFields.map((field, index) =>
        <FormGroup>
            <ControlLabel>{field.value}</ControlLabel>
            <FormControl onChange={
                (value) => {
                    if(!modalState[field.key]){
                        let result;
                        result[field.key] = value;
                        modalState.push(result);
                    } else {
                        modalState
                    }

                    console.log(modalState);
                }
            } name={field.key}/>
            <HelpBlock>Required</HelpBlock>
        </FormGroup>)

    return (
        <Form onSubmit={() => {
            console.log(modalState);
            axios.post('http://localhost:8080/files/downloadFilledTemplate?groupName=1&fileName=3707B2FC918C40BCFB1869D6E48E456D', modalState)
                .then(function (response) {
                    close();
                    console.log(response);
                })
                .catch(function (error) {
                    console.log(error);
                });

        }} layout="horizontal">
            {renderedInputFields}
            <FormGroup>
                <ButtonToolbar>
                    <Button type="submit" appearance="primary">Submit</Button>
                    <Button onClick={close} appearance="default">Cancel</Button>
                </ButtonToolbar>
            </FormGroup>
        </Form>)
}

export default ModalFormComponent;

