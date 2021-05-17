export const LOAD_FILES_REQUESTED = 'launchpad/LOAD_FILES_REQUESTED';
export const LOAD_FILES_SUCCESS = 'launchpad/LOAD_FILES_SUCCESS';
export const LOAD_FILES_FAILURE = 'launchpad/LOAD_FILES_FAILURE';
export const DELETE_FILE_REQUESTED = 'launchpad/DELETE_FILE_REQUESTED';
export const DELETE_FILE_SUCCESS = 'launchpad/DELETE_FILE_SUCCESS';
export const DELETE_FILE_FAILURE = 'launchpad/DELETE_FILE_FAILURE';

export type loadFilesAction = {
    type: LOAD_FILES_REQUESTED;
    userName: "";
    token: "";
}

export type deleteFileAction = {
    type: DELETE_FILE_REQUESTED;
    groupId: "";
    fileHashName: "";
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

export function deleteFile(groupId:string,fileHashName:string): deleteFileAction {
    return {type: DELETE_FILE_REQUESTED, groupId, fileHashName};
}

export function deleteFileSuccess(files:[]) {
    return {type: DELETE_FILE_SUCCESS,files:files};
}

export function deleteFileFailure() {
    return {type: DELETE_FILE_FAILURE};
}

export type LoadFilesSuccessAction = ReturnType<typeof loadFilesSuccess>;
export type LoadFilesFailureAction = ReturnType<typeof loadFilesFailure>;
export type DeleteFileSuccessAction = ReturnType<typeof deleteFileSuccess>;
export type DeleteFileFailureAction = ReturnType<typeof deleteFileFailure>;

export type FilesAction =
    | loadFilesAction
    | LoadFilesSuccessAction
    | LoadFilesFailureAction
    | deleteFileAction
    | DeleteFileSuccessAction
    | DeleteFileFailureAction