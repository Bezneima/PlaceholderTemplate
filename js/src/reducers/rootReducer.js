import { combineReducers } from 'redux';
import usersReducer from '../modules/launchpad/usersReducer';
import profileReduser from "../modules/profile/profileReduser";
import fileReducer from "../modules/launchpad/fileReducer";

const rootReducer = combineReducers({
    userState: usersReducer,
    filesState: fileReducer,
    profileState: profileReduser
});

export default rootReducer;