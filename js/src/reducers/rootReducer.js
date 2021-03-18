import { combineReducers } from 'redux';
import usersReducer from '../modules/launchpad/usersReducer';

const rootReducer = combineReducers({
    userState: usersReducer,
});

export default rootReducer;