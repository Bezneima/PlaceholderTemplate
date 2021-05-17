import * as userActions from './actions/userActions';
import * as fileActions from './actions/fileActions';
import {all, call, put, takeEvery} from 'redux-saga/effects';
import * as API from "./api";
import type {loadUsersAction, authUserAction} from "./actions/userActions";
import type {deleteFileAction, loadFilesAction} from "./actions/fileActions";

function* loadUsersSaga(action: loadUsersAction): Generator<any, any> {
    try {
        const response = yield call(API.loadUser, action.token, action.userName);

        yield put(userActions.loadUsersSuccess(response));
    } catch (error) {
        yield put(userActions.loadUsersFailure());
    }
}

function* authUserSaga(action: authUserAction): Generator<any> {
    try {
        const response = yield call(API.authUser, action.login, action.password);
        if (response) {
            yield put(fileActions.loadFiles(response.last_user_token, response.user_name));
            yield put(userActions.authUserSuccess(response));
        } else
            yield put(userActions.authUserFailure());
    } catch (error) {
        yield put(userActions.authUserFailure());
    }
}

function* LoadUserFilesSaga(action: loadFilesAction): Generator<any> {
    try {
        const response = yield call(API.loadFiles, action.userName, action.token);
        if (response)
            yield put(fileActions.loadFilesSuccess(response));
        else
            yield put(fileActions.loadFilesFailure());
    } catch (error) {
        yield put(fileActions.loadFilesFailure());
    }
}

function* DeleteUserFileSaga(action: deleteFileAction): Generator<any> {
    try {
        const response = yield call(API.deleteFile, action.groupId, action.fileHashName);
        if (response)
            yield put(fileActions.deleteFileSuccess(response));
        else
            yield put(fileActions.deleteFileFailure());
    } catch (error) {
        yield put(fileActions.deleteFileFailure());
    }
}

export default function* UsersRootSaga(): Generator<any, any> {
    yield all([
        yield takeEvery(userActions.LOAD_USERS_REQUESTED, loadUsersSaga),
        yield takeEvery(userActions.AUTH_USERS_REQUESTED, authUserSaga),
        yield takeEvery(fileActions.LOAD_FILES_REQUESTED, LoadUserFilesSaga),
        yield takeEvery(fileActions.DELETE_FILE_REQUESTED, DeleteUserFileSaga)
    ]);
}
