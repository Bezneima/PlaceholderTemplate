import axios from 'axios';

export const loadUser = (userToken, userName) =>
    axios
        .post(`http://localhost:8080/files/CheckToken`, {
            last_user_token: userToken,
            user_name: userName,
        }, {
            dataType: 'JSON',
            contentType:"application/json; charset=UTF-8",
            'Access-Control-Allow-Origin': '*',
            Accept: 'application/json'
        })
        .then((response) => response.data);
