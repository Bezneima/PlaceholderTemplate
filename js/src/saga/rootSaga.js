import {all} from 'redux-saga/effects';

import launchpadSaga from '../modules/launchpad/saga';

export default function* rootSaga(): Generator<any, any, any> {
    yield all([
        launchpadSaga(),
    ]);
}