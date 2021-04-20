export const LOAD_FILES_REQUESTED = 'launchpad/LOAD_FILES_REQUESTED';
export const LOAD_FILES_SUCCESS = 'launchpad/LOAD_FILES_SUCCESS';
export const LOAD_FILES_FAILURE = 'launchpad/LOAD_FILES_FAILURE';

export type loadFilesAction = {
    type: LOAD_FILES_REQUESTED;
    userName: "";
    token: "";
}

export function loadFilesSuccess(files:[]) {
    return {type: LOAD_FILES_SUCCESS,files:files};
}

export function loadFilesFailure() {
    return {type: LOAD_FILES_FAILURE};
}

export function loadFiles(token: string, userName): loadFilesAction {
    return {type: LOAD_FILES_REQUESTED, token, userName};
}

export type LoadFilesSuccessAction = ReturnType<typeof loadFilesSuccess>;
export type LoadFilesFailureAction = ReturnType<typeof loadFilesFailure>;

export type FilesAction =
    | loadFilesAction
    | LoadFilesSuccessAction
    | LoadFilesFailureAction
