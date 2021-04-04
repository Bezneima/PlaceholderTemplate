import axios from 'axios';

export const loadProfile = (userToken, userName) =>
    axios
        .post(`http://localhost:8080/users/getUserInfo`, {
            last_user_token: userToken,
            user_name: userName,
        }, {
            dataType: 'JSON',
            contentType:"application/json; charset=UTF-8",
            'Access-Control-Allow-Origin': '*',
            Accept: 'application/json'
        })
        .then((response) => response.data);
