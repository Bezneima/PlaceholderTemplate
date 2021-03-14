import * as actions from './actions';
import { all, call, put, takeEvery } from 'redux-saga/effects';
import * as API from "./api";

function* loadServicesSaga(): Generator<any, any, any> {
    try {
        const response = yield call(API.loadFileList);
        console.log(response);
        yield put(actions.loadLaunchpadSuccess(response,123));
    } catch (error) {
        console.log(error,"МОЕ СООБЩЕНИЕ");
        yield put(actions.loadLaunchpadFailure());
    }
}

export default function* serviceGroupsRootSaga(): Generator<any, any, any> {
    yield all([yield takeEvery(actions.LOAD_LAUNCHPAD_REQUESTED, loadServicesSaga)]);
}
