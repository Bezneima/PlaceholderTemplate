import React from 'react';
import {Button, ButtonToolbar, Modal} from "rsuite";
import ModalFormComponent from "./ModalFormComponent";


class FillerModalComponent extends React.Component {
    constructor(props) {
        super(props);
    }

    componentDidMount() {

    }

    render() {
        const {inputFieldsNames, fileHashName, groupId, path, fileName, fileId} = this.props.selectedFile;
        return (
            <div className="modal-container">
                <Modal show={this.props.opened} onHide={this.props.close}>
                    <Modal.Header>
                        <Modal.Title>{fileName}</Modal.Title>
                    </Modal.Header>
                    <Modal.Body>
                        <ModalFormComponent
                            currentModalState={this.props.currentModalState}
                            setCurrentModalState={this.props.setCurrentModalState}
                            fileHashName={fileHashName}
                            fileName={fileName}
                            inputFieldsNames={inputFieldsNames}
                            fileId = {fileId}
                            close={this.props.close}
                        />
                    </Modal.Body>

                    {/*<Modal.Footer>
                        <Button onClick={this.props.close} appearance="primary">
                            Ok
                        </Button>
                        <Button onClick={this.props.close} appearance="subtle">
                            Cancel
                        </Button>
                    </Modal.Footer>*/}
                </Modal>
            </div>
        );
    }
}

export default FillerModalComponent