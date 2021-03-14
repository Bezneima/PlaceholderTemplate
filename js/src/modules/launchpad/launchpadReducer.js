import { LaunchpadState } from './model';
import * as actions from './actions';

const initialState: LaunchpadState = {
    isLoading: false,
    files: [],
};

export default (state: LaunchpadState = initialState, action: any): LaunchpadState => {
    switch (action.type) {
        case actions.LOAD_LAUNCHPAD_REQUESTED: {//todo Переделать все тут и понять зачем
            return {
                ...state,
                isLoading: true,
            };
        }
        case actions.LOAD_LAUNCHPAD_SUCCESS: {
            return {
                ...state,
                some: action.some,
                files: action.text,
                isLoading: false,
            };
        }
        case actions.LOAD_LAUNCHPAD_FAILURE: {
            return {
                ...state,
                isLoading: false,
            };
        }
        default:
            return state;
    }
}