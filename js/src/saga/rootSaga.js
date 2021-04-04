import {all} from 'redux-saga/effects';

import UsersRootSaga from '../modules/launchpad/saga';
import ProfileRootSaga from "../modules/profile/saga";

export default function* rootSaga(): Generator<any> {
    yield all([
        UsersRootSaga(),
        ProfileRootSaga(),
    ]);
}