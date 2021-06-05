import React, {useState, useEffect} from 'react';
import {Button, ButtonToolbar, ControlLabel, Form, FormControl, FormGroup, HelpBlock, Icon, IconButton} from "rsuite";
import axios from "axios";


function ModalFormComponent(props) {
    const {close, inputFieldsNames, setCurrentModalState, currentModalState, fileHashName, fileName, fileId} = props;
    let formParams = null;
    let [allInputFields, setAllInputFields] = useState([]);
    let documents = [];

    // currentModalState - это массив где ключ = fileID а внутри него массив документов
    class Document {
        constructor(index, title, changes) {
            this.index = index;
            this.title = title;
            this.changes = changes;
        }
    }

    const addEmptyDocument = (targetArray) => {
        let changes = JSON.parse(inputFieldsNames);
        changes.forEach(inputField => {
            inputField['valueTo'] = '';
        })
        //console.log("targetArray",targetArray);
        const emptyDocument = new Document(targetArray.length, targetArray.length+" "+fileName, changes);
        targetArray.push(emptyDocument);
    }

    useEffect(() => {
        if (!currentModalState[fileId]) {
            let newCurrentModalState = currentModalState;
            newCurrentModalState[fileId] = [];
            addEmptyDocument(newCurrentModalState[fileId]);
            setCurrentModalState(currentModalState => {
                return {...newCurrentModalState};
            });
        }
    }, []);

    const generateInputFields = (inputFieldArray,index) => {
        let outputFieldsJSX = [];
        outputFieldsJSX.push(inputFieldArray.map(function (inputField) {
            console.log(inputField);
            return (
                <FormGroup>
                    <ControlLabel>{inputField.value}</ControlLabel>
                    <FormControl
                        onChange={(value,event) => {
                            console.log("target",event.target);
                            let newCurrentModalState = currentModalState;
                            newCurrentModalState[fileId][index].changes.forEach(fileChanges => {
                                if (fileChanges.key === inputField.key)
                                    fileChanges.valueTo = value;
                            })
                            setCurrentModalState(currentModalState => {
                                return {...newCurrentModalState};
                            });
                        }}
                        name={inputField.value}
                        key={inputField.key}
                        value={inputField.valueTo}/>
                    <HelpBlock>Required</HelpBlock>
                </FormGroup>
            );
        }));
        return outputFieldsJSX;
    }

    const generateDocumentsField = (documentsArray) => {
        let outputDocumentForm = [];
        console.log(documentsArray);

        outputDocumentForm.push(documentsArray.map(function (document) {
            console.log(document);
            return (
                <>
                    <h6>{document.title}</h6>
                    {generateInputFields(document.changes,document.index)}
                </>
            );
        }));
        return outputDocumentForm;
    }

    const renderInputFieldFunc = () => {
        console.log("currentModalState", currentModalState);

        if (currentModalState[fileId]) {
            const inputFields = generateDocumentsField(currentModalState[fileId]);
            return (
                <>
                    {inputFields}
                </>
            )
        } else {
            return <div>Что-то пошло не так</div>
        }
    }

    return (
        <Form onSubmit={() => {
            let data = [];
            // console.log("me", currentModalState);
            axios.post(`http://localhost:8080/files/downloadFilledTemplate?groupName=1&fileHashName=${fileHashName}&fileName=${fileName}`, currentModalState[fileId])
                .then(function (response) {
                    close();
                    // console.log(response);
                    window.location.href = `http://localhost:8080/files/download?groupName=&isTemplate=true&fileHashName=${response.data}&fileName=${fileName}.zip`
                })
                .catch(function (error) {
                    // console.log(error);
                });

        }} layout="horizontal">
            {
                renderInputFieldFunc()
            }
            <FormGroup>
                <ButtonToolbar>
                    <IconButton
                        size="lg"
                        icon={<Icon icon="plus-square"/>}
                        style={{marginRight: "120px"}}
                        onClick={() => {
                            let newCurrentModalState = currentModalState;
                            addEmptyDocument(newCurrentModalState[fileId]);
                            setCurrentModalState(currentModalState => {
                                return {...newCurrentModalState};
                            });
                        }}
                    />
                    <Button type="submit" appearance="primary">Submit</Button>
                    <Button onClick={() => {
                        close();
                    }} appearance="default">Cancel</Button>
                </ButtonToolbar>
            </FormGroup>
        </Form>)
}

export default ModalFormComponent;

