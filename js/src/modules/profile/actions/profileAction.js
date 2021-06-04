import type {ProfileState} from "../model";

export const LOAD_PROFILE_REQUESTED = 'profile/LOAD_PROFILE_REQUESTED';
export const LOAD_PROFILE_SUCCESS = 'profile/LOAD_PROFILE_SUCCESS';
export const LOAD_PROFILE_FAILURE = 'profile/LOAD_PROFILE_FAILURE';
export const LOAD_GROUPS_REQUESTED = 'profile/LOAD_GROUPS_REQUESTED';
export const LOAD_GROUPS_SUCCESS = 'profile/LOAD_GROUPS_SUCCESS';
export const LOAD_GROUPS_FAILURE = 'profile/LOAD_GROUPS_FAILURE';
export const LOAD_USERS_NAMES_REQUESTED = 'profile/LOAD_USERS_NAMES_REQUESTED';
export const LOAD_USERS_NAMES_SUCCESS = 'profile/LOAD_USERS_NAMES_SUCCESS';
export const LOAD_USERS_NAMES_FAILURE = 'profile/LOAD_USERS_NAMES_FAILURE';
export const LOAD_USERS_GROUPS_REQUESTED = 'profile/LOAD_USERS_GROUPS_REQUESTED';
export const LOAD_USERS_GROUPS_SUCCESS = 'profile/LOAD_USERS_GROUPS_SUCCESS';
export const LOAD_USERS_GROUPS_FAILURE = 'profile/LOAD_USERS_GROUPS_FAILURE';

export type loadProfileAction = {
    type: LOAD_PROFILE_REQUESTED;
    userName: "";
    token: "";
}
export type loadGroupsAction = {
    type: LOAD_GROUPS_REQUESTED;
    token: "";
}
export type loadUsersNamesAction = {
    type: LOAD_USERS_NAMES_REQUESTED;
    token: "";
}
export type loadUsersGroupsAction = {
    type: LOAD_USERS_GROUPS_REQUESTED;
    userName: "";
}

export function loadUsersGroups(userName: string): loadGroupsAction {
    return {type: LOAD_USERS_GROUPS_REQUESTED, userName};
}

export function loadUsersGroupsSuccess(groups: groups) {
    return {type: LOAD_USERS_GROUPS_SUCCESS, groups};
}

export function loadUsersGroupsFailure() {
    return {type: LOAD_USERS_GROUPS_FAILURE};
}


export function loadUsersNames(token: string): loadUsersNamesAction {
    return {type: LOAD_USERS_NAMES_REQUESTED, token};
}

export function loadUsersNamesSuccess(users: users) {
    return {type: LOAD_USERS_NAMES_SUCCESS, users};
}

export function loadUsersNamesFailure() {
    return {type: LOAD_USERS_NAMES_FAILURE};
}

export function loadGroups(token: string): loadGroupsAction {
    return {type: LOAD_GROUPS_REQUESTED, token};
}

export function loadGroupsSuccess(groups: groups) {
    return {type: LOAD_GROUPS_SUCCESS, groups};
}

export function loadGroupsFailure() {
    return {type: LOAD_GROUPS_FAILURE};
}

export function loadProfile(token: string, userName: string): loadProfileAction {
    return {type: LOAD_PROFILE_REQUESTED, token, userName};
}

export function loadProfileSuccess(profile: ProfileState) {
    return {type: LOAD_PROFILE_SUCCESS, profile};
}

export function loadProfileFailure() {
    return {type: LOAD_PROFILE_FAILURE};
}

export type LoadProfileSuccessAction = ReturnType<typeof loadProfileSuccess>;
export type LoadProfileFailureAction = ReturnType<typeof loadProfileFailure>;
export type LoadGroupsFailureAction = ReturnType<typeof loadGroupsFailure>;
export type LoadGroupsSuccessAction = ReturnType<typeof loadGroupsSuccess>;
export type LoadUsersNamesSuccessAction = ReturnType<typeof loadUsersNamesFailure>;
export type LoadUsersNamesFailureAction = ReturnType<typeof loadUsersNamesSuccess>;
export type LoadUsersGroupsSuccessAction = ReturnType<typeof loadUsersGroupsFailure>;
export type LoadUsersGroupsFailureAction = ReturnType<typeof loadUsersGroupsSuccess>;

export type ProfileAction =
    | loadProfileAction
    | LoadProfileSuccessAction
    | LoadProfileFailureAction
    | loadGroupsAction
    | LoadGroupsSuccessAction
    | LoadGroupsFailureAction
    | loadUsersNamesAction
    | LoadUsersNamesFailureAction
    | LoadUsersNamesSuccessAction
    | loadUsersGroupsAction
    | LoadUsersGroupsSuccessAction
    | LoadUsersGroupsFailureAction