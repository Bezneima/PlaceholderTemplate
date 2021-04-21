import React, {useEffect, useState} from 'react';
import {RouteComponentProps} from "react-router-dom";
import {Col, Dropdown, Icon, Row} from "rsuite";

import 'rsuite/dist/styles/rsuite-dark.css';
import '../styles/style.css';
import FillerModalComponent from "./FillerModalComponent";

interface Props extends RouteComponentProps<{}> {
    loadFiles: () => { type: string };
}

const fileStyle = {
    float: 'left',
    border: '1px solid #272c36',
    marginLeft: '10px',
    marginTop: "10px",
    borderRadius: 8,

}
const filesWrapper = {
    margin: 'auto',
    maxWidth: '1060px',
    display: 'flex',
    justifyContent: 'flex-start',
    flexWrap: 'wrap'

}
function downloadDoc(groupId, fileHash){
    window.location.href = `http://localhost:8080/files/download?groupName=${groupId}&isTempalte=false&fileName=${fileHash}`
}


function LaunchpadBody(props) {
    const close = () => {
        setOpened(false);
        filesState.modalState = [];
    }
    const open = (file) => {
        setSelectedFile(file);
        setOpened(true);
    }
    const {filesState} = props;
    const {files} = filesState;
    const [opened, setOpened] = useState(false);
    const [selectedFile, setSelectedFile] = useState(null);
    useEffect(() => {
        // Обновляем заголовок документа с помощью API браузера

    });

    return (
        <div style={filesWrapper}>
            {   selectedFile &&
                <FillerModalComponent selectedFile={selectedFile} modalState={filesState.modalState} opened={opened} close={close}/>
            }
                {
                    files.map((file, index) => {
                        return (
                            <div className="dropdown-group" style={fileStyle}>
                                <Dropdown toggleClassName={'customDropdown'} title={file.fileName} placement="bottomEnd" icon={<Icon icon="file-word-o" size="2x"/>}>
                                    <Dropdown.Item onSelect={()=>{downloadDoc(file.groupId, file.fileHashName)}} eventKey="a">Скачать Шаблон</Dropdown.Item>
                                    <Dropdown.Item onSelect={()=>{open(file)}} eventKey="b">Заполнить</Dropdown.Item>
                                    <Dropdown.Item eventKey="c">Rest API</Dropdown.Item>
                                    <Dropdown.Item eventKey="d">Удалить</Dropdown.Item>
                                </Dropdown>
                            </div>
                        )

                    })
                }
        </div>);
}

export default LaunchpadBody;