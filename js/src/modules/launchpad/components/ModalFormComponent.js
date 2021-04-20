import React, {useEffect} from 'react';
import {Button, ButtonToolbar, ControlLabel, Form, FormControl, FormGroup, HelpBlock} from "rsuite";


function ModalFormComponent (props) {
    const { close, inputFieldsNames } = props;
            const inputFields = JSON.parse(inputFieldsNames).map((field, index) =>
                <FormGroup>
                    <ControlLabel>{field.value}</ControlLabel>
                    <FormControl onChange={} name={field.key}/>
                    <HelpBlock>Required</HelpBlock>
                </FormGroup>)


    return (
        <Form onSubmit={(data) => {console.log(data)}} layout="horizontal">
            {inputFields}
            <FormGroup>
                <ButtonToolbar>
                    <Button type="submit" appearance="primary">Submit</Button>
                    <Button onClick={close} appearance="default">Cancel</Button>
                </ButtonToolbar>
            </FormGroup>
        </Form>)
}

export default ModalFormComponent;

