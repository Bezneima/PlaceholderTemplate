import type {ProfileState} from "../model";

export const LOAD_PROFILE_REQUESTED = 'profile/LOAD_PROFILE_REQUESTED';
export const LOAD_PROFILE_SUCCESS = 'profile/LOAD_PROFILE_SUCCESS';
export const LOAD_PROFILE_FAILURE = 'profile/LOAD_PROFILE_FAILURE';

export type loadProfileAction = {
    type: LOAD_PROFILE_REQUESTED;
    userName: "";
    token: "";
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

export type ProfileAction =
    | loadProfileAction
    | LoadProfileSuccessAction
    | LoadProfileFailureAction