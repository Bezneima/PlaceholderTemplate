import axios from 'axios';

export const loadFileList = () =>
    axios
        .get(`http://localhost:8080/files/test`, {
            headers: {
                token: "",
                Accept: 'application/json',
            }
        })
        .then((response) => response.data);
