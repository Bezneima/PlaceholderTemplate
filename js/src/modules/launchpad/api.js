import axios from 'axios';

export const loadUser = (userToken, userName) =>
    axios
        .post(`http://localhost:8080/users/checkAuthToken`, {
            last_user_token: userToken,
            user_name: userName,
        }, {
            dataType: 'JSON',
            contentType:"application/json; charset=UTF-8",
            'Access-Control-Allow-Origin': '*',
            Accept: 'application/json'
        })
        .then((response) => response.data);

export const authUser = (userName, password) =>
    axios
        .post(`http://localhost:8080/users/auth`, {
            user_name: userName,
            password: password,
        }, {
            dataType: 'JSON',
            contentType:"application/json; charset=UTF-8",
            'Access-Control-Allow-Origin': '*',
            Accept: 'application/json'
        })
        .then((response) => response.data);

export const loadFiles = (userName, token) =>
    axios
        .post(`http://localhost:8080/files/getAllUserFiles`, {
            user_name: userName,
            last_user_token: token,
        }, {
            dataType: 'JSON',
            contentType:"application/json; charset=UTF-8",
            'Access-Control-Allow-Origin': '*',
            Accept: 'application/json'
        })
        .then((response) => response.data);
