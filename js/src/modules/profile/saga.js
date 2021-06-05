import {all, call, put, takeEvery} from "redux-saga/effects";
import type {
    loadGroupsAction,
    loadProfileAction,
    loadUsersGroupsAction,
    loadUsersNamesAction
} from "./actions/profileAction";
import * as actions from "./actions/profileAction";
import * as API from "./api.js"

function* loadProfileSaga(action: loadProfileAction): Generator<any, any> {
    try {
        const response = yield call(API.loadProfile, action.token, action.userName);
        yield put(actions.loadProfileSuccess(response));
    } catch (error) {
        yield put(actions.loadProfileFailure());
    }
}
function* loadGroupsSaga(action: loadGroupsAction): Generator<any, any> {
    try {
        const response = yield call(API.loadGroups, action.token);
        yield put(actions.loadGroupsSuccess(response));
    } catch (error) {
        yield put(actions.loadGroupsFailure());
    }
}

function* loadUsersNamesSaga(action: loadUsersNamesAction): Generator<any, any> {
    try {
        const response = yield call(API.loadUsersNames, action.token);
        yield put(actions.loadUsersNamesSuccess(response));
    } catch (error) {
        yield put(actions.loadUsersNamesFailure());
    }
}

function* loadUsersGroupsSaga(action: loadUsersGroupsAction): Generator<any, any> {
    try {
        const response = yield call(API.loadUsersGroups, action.userName);
        yield put(actions.loadUsersGroupsSuccess(response));
    } catch (error) {
        yield put(actions.loadUsersGroupsFailure());
    }
}

export default function* ProfileRootSaga(): Generator<any, any> {
    yield all([
        yield takeEvery(actions.LOAD_PROFILE_REQUESTED, loadProfileSaga),
        yield takeEvery(actions.LOAD_GROUPS_REQUESTED, loadGroupsSaga),
        yield takeEvery(actions.LOAD_USERS_NAMES_REQUESTED, loadUsersNamesSaga),
        yield takeEvery(actions.LOAD_USERS_GROUPS_REQUESTED, loadUsersGroupsSaga),
    ]);
}