import type {FileInfo} from "./model";

export const LOAD_LAUNCHPAD_REQUESTED = 'launchpad/LOAD_SERVICES_REQUESTED';
export const LOAD_LAUNCHPAD_SUCCESS = 'launchpad/LOAD_SERVICES_SUCCESS';
export const LOAD_LAUNCHPAD_FAILURE = 'launchpad/LOAD_SERVICES_FAILURE';

export function loadLaunchpad() {
    return { type: LOAD_LAUNCHPAD_REQUESTED };
}

export function loadLaunchpadSuccess(files:string/*Array<FileInfo>*/,some:string) {
    return { type: LOAD_LAUNCHPAD_SUCCESS, files ,some };
}

export function loadLaunchpadFailure() {
    return { type: LOAD_LAUNCHPAD_FAILURE };
}

export type LoadLaunchpadAction = ReturnType<typeof loadLaunchpad>;
export type LoadLaunchpadSuccessAction = ReturnType<typeof loadLaunchpadSuccess>;
export type LoadLaunchpadFailureAction = ReturnType<typeof loadLaunchpadFailure>;

export type LaunchpadAction =
    | LoadLaunchpadAction
    | LoadLaunchpadSuccessAction
    | LoadLaunchpadFailureAction
