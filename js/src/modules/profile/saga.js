import {all, call, put, takeEvery} from "redux-saga/effects";
import type {loadProfileAction} from "./actions/profileAction";
import * as actions from "./actions/profileAction";
import * as API from "./api.js"

function* loadProfileSaga(action: loadProfileAction): Generator<any, any> {
    try {
        console.log("response",action.token, action.userName);
        const response = yield call(API.loadProfile, action.token, action.userName);
        console.log('here',response);
        yield put(actions.loadProfileSuccess(response));
    } catch (error) {
        yield put(actions.loadProfileFailure());
    }
}

export default function* ProfileRootSaga(): Generator<any, any> {
    yield all([
        yield takeEvery(actions.LOAD_PROFILE_REQUESTED, loadProfileSaga)
    ]);
}