export const LOAD_USERS_REQUESTED = 'launchpad/LOAD_USERS_REQUESTED';
export const LOAD_USERS_SUCCESS = 'launchpad/LOAD_USERS_SUCCESS';
export const LOAD_USERS_FAILURE = 'launchpad/LOAD_USERS_FAILURE';
export const AUTH_USERS_REQUESTED = 'launchpad/AUTH_USERS_REQUESTED';
export const AUTH_USERS_SUCCESS = 'launchpad/AUTH_USERS_SUCCESS';
export const AUTH_USERS_FAILURE = 'launchpad/AUTH_USERS_FAILURE';

export type loadUsersAction = {
    type: LOAD_USERS_REQUESTED;
    userName: "";
    token: "";
}

export type authUserAction = {
    type: LOAD_USERS_REQUESTED;
    login: "";
    password: "";
}

export function loadUsersSuccess(isAuth: string) {
    return {type: LOAD_USERS_SUCCESS, isAuth};
}

export function loadUsersFailure() {
    return {type: LOAD_USERS_FAILURE};
}

export function loadUsers(token: string, userName): loadUsersAction {
    return {type: LOAD_USERS_REQUESTED, token, userName};
}

export function authUser(login: string, password: string): authUserAction {
    return {type: AUTH_USERS_REQUESTED, login, password};
}

export function authUserSuccess(user: string) {
    return {type: AUTH_USERS_SUCCESS, user};
}

export function authUserFailure() {
    return {type: AUTH_USERS_FAILURE};
}

export type LoadUsersSuccessAction = ReturnType<typeof loadUsersSuccess>;
export type LoadUsersFailureAction = ReturnType<typeof loadUsersFailure>;
export type AuthUserSuccessAction = ReturnType<typeof authUserSuccess>;
export type AuthUserFailureAction = ReturnType<typeof authUserFailure>;

export type UsersAction =
    | loadUsersAction
    | LoadUsersSuccessAction
    | LoadUsersFailureAction
    | authUserAction
    | AuthUserSuccessAction
    | AuthUserFailureAction
