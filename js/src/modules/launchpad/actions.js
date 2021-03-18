export const LOAD_USERS_REQUESTED = 'launchpad/LOAD_SERVICES_REQUESTED';
export const LOAD_USERS_SUCCESS = 'launchpad/LOAD_SERVICES_SUCCESS';
export const LOAD_USERS_FAILURE = 'launchpad/LOAD_SERVICES_FAILURE';

export type loadUsersAction = {
    type: LOAD_USERS_REQUESTED;
    userName: "";
    token: "";
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

export type LoadUsersSuccessAction = ReturnType<typeof loadUsersSuccess>;
export type LoadUsersFailureAction = ReturnType<typeof loadUsersFailure>;

export type UsersAction =
    | loadUsersAction
    | LoadUsersSuccessAction
    | LoadUsersFailureAction
