import type {FilesState} from "./model";
import * as actions from "./actions/fileActions";

const initialState: FilesState = {
    files: [],
    isLoading:false,
};

export default (state: FilesState = initialState, action: any): FilesState => {
    switch (action.type) {
        case actions.LOAD_FILES_REQUESTED: {
            return {
                ...state,
                isLoading: true,
            };
        }
        case actions.LOAD_FILES_SUCCESS: {
            console.log('HUI',action)
            return {
                ...state,
                files: action.files,
                isLoading: false,
            };
        }
        case actions.LOAD_FILES_FAILURE: {
            return {
                ...state,
                isLoading: false,
            };
        }
        default:
            return state;
    }
}