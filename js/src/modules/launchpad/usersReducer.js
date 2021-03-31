import { UserState } from './model';
import * as actions from './actions';

const initialState: UserState = {
    isLoading: false,
    isAuth: false,
};

export default (state: UserState = initialState, action: any): UserState => {
    switch (action.type) {
        case actions.LOAD_USERS_REQUESTED: {
            return {
                ...state,
                isLoading: true,
            };
        }
        case actions.LOAD_USERS_SUCCESS: {
            return {
                ...state,
                isAuth: action.isAuth,
                isLoading: false,
            };
        }
        case actions.LOAD_USERS_FAILURE: {
            return {
                ...state,
                isLoading: false,
            };
        }
        case actions.AUTH_USERS_REQUESTED: {
            return {
                ...state,
                failedLogin:false,
                token:"",
                logining: true,
            };
        }
        case actions.AUTH_USERS_SUCCESS: {
            localStorage.setItem('token',action.user.last_user_token);
            localStorage.setItem('login',action.user.user_name);
            return {
                ...state,
                token: action.user.last_user_token,
                login: action.user.user_name,
                isAuth:true,
                logining:false,
            };
        }
        case actions.AUTH_USERS_FAILURE: {
            return {
                ...state,
                failedLogin:true,
                logining: false,
            };
        }
        default:
            return state;
    }
}