import * as actions from "./actions/profileAction"
import { ProfileState } from "./model"

const initialState: ProfileState = {
    id: 0,
    avatarImgPath: '',
    role: '',
    userName: '',
    isLoading: false,
};

export default (state: ProfileState = initialState, action: any): ProfileState => {
    switch (action.type) {
        case actions.LOAD_PROFILE_REQUESTED: {
            return {
                ...state,
                isLoading: true,
            };
        }
        case actions.LOAD_PROFILE_SUCCESS: {
            console.log(action)
            return {
                ...state,
                id: action.profile.id,
                avatarImgPath: action.profile.avatar_img_path,
                role: action.profile.role,
                userName: action.profile.user_name,
                isLoading: false
            };
        }
        case actions.LOAD_PROFILE_FAILURE: {
            return {
                ...state,
                isLoading: false,
            };
        }

        default:
            return state;
    }
}