import { combineReducers } from 'redux';
import usersReducer from '../modules/launchpad/usersReducer';
import profileReduser from "../modules/profile/profileReduser";

const rootReducer = combineReducers({
    userState: usersReducer,
    profileState: profileReduser
});

export default rootReducer;