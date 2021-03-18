import * as actions from './actions';
import { all, call, put, takeEvery } from 'redux-saga/effects';
import * as API from "./api";
import type {loadUsersAction} from "./actions";

function* loadUsersSaga(action:loadUsersAction): Generator<any, any> {
    try {
        const response = yield call(API.loadUser,action.token,action.userName);
        yield put(actions.loadUsersSuccess(response));
    } catch (error) {
        yield put(actions.loadUsersFailure());
    }
}

export default function* UsersRootSaga(): Generator<any, any> {
    yield all([yield takeEvery(actions.LOAD_USERS_REQUESTED, loadUsersSaga)]);
}
