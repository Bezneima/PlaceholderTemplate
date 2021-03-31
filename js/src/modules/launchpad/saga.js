import * as actions from './actions';
import {all, call, put, takeEvery} from 'redux-saga/effects';
import * as API from "./api";
import type {loadUsersAction, authUserAction} from "./actions";

function* loadUsersSaga(action: loadUsersAction): Generator<any, any> {
    try {
        console.log("response",action.token, action.userName);
        const response = yield call(API.loadUser, action.token, action.userName);
        console.log('here',response);
        yield put(actions.loadUsersSuccess(response));
    } catch (error) {
        yield put(actions.loadUsersFailure());
    }
}

function* authUserSaga(action: authUserAction): Generator<any> {
    try {
        const response = yield call(API.authUser, action.login, action.password);
        console.log('response',response);
        if (response)
            yield put(actions.authUserSuccess(response));
        else
            yield put(actions.authUserFailure());
    } catch (error) {
        yield put(actions.authUserFailure());
    }
}

export default function* UsersRootSaga(): Generator<any, any> {
    yield all([
        yield takeEvery(actions.LOAD_USERS_REQUESTED, loadUsersSaga),
        yield takeEvery(actions.AUTH_USERS_REQUESTED, authUserSaga)
    ]);
}
