import { combineReducers } from 'redux';
import launchpadReducer from '../modules/launchpad/launchpadReducer';

const rootReducer = combineReducers({
    launchpad: launchpadReducer,
});

export default rootReducer;