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

export const loadGroups = (userToken) =>
    axios
        .post(`http://localhost:8080/users/getAllExistingGroupsNames`, {
            last_user_token: userToken,
        }, {
            dataType: 'JSON',
            contentType: "application/json; charset=UTF-8",
            'Access-Control-Allow-Origin': '*',
            Accept: 'application/json'
        })
        .then((res) => res.data);

export const loadUsersNames = (userToken) =>
    axios
        .post(`http://localhost:8080/users/getAllUsers`, {
            last_user_token: userToken,
        }, {
            dataType: 'JSON',
            contentType: "application/json; charset=UTF-8",
            'Access-Control-Allow-Origin': '*',
            Accept: 'application/json'
        })
        .then((res) => res.data);

export const loadUsersGroups = (userName) =>
    axios
        .post(`http://localhost:8080/users/getAllUsersGroups?userName=${userName}`, {

        }, {
            dataType: 'JSON',
            contentType: "application/json; charset=UTF-8",
            'Access-Control-Allow-Origin': '*',
            Accept: 'application/json'
        })
        .then((res) => res.data);


