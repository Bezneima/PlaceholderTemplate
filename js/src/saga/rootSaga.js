import {all} from 'redux-saga/effects';

import UsersRootSaga from '../modules/launchpad/saga';

export default function* rootSaga(): Generator<any> {
    yield all([
        UsersRootSaga(),
    ]);
}