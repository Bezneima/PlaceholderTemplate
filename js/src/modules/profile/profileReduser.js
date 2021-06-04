import * as actions from "./actions/profileAction"
import { ProfileState } from "./model"

const initialState: ProfileState = {
    id: 0,
    avatarImgPath: '',
    role: '',
    userName: '',
    isLoading: false,
    groupsNames: [],
    groupsLoading: false,
    usersNames: [],
    usersLoading: false,
    usersGroups: [],
    usersGroupsLoading: false
};

export default (state: ProfileState = initialState, action: any): ProfileState => {
    switch (action.type) {
        case actions.LOAD_USERS_GROUPS_REQUESTED: {
            return {
                ...state,
                usersGroupsLoading: true
            }
        }
        case actions.LOAD_USERS_GROUPS_SUCCESS: {
            return {
                ...state,
                usersGroupsLoading: false,
                usersGroups: action.groups,
            }
        }
        case actions.LOAD_USERS_GROUPS_FAILURE: {
            return {
                ...state,
                usersGroupsLoading: false,
            }
        }
        case actions.LOAD_USERS_NAMES_REQUESTED: {
            return {
                ...state,
                usersLoading: true
            }
        }
        case actions.LOAD_USERS_NAMES_SUCCESS: {
            return {
                ...state,
                usersLoading: false,
                usersNames: action.users,
            }
        }
        case actions.LOAD_USERS_NAMES_FAILURE: {
            return {
                ...state,
                usersLoading: false,
            }
        }
        case actions.LOAD_GROUPS_REQUESTED: {
            return {
                ...state,
                groupsLoading: true
            }
        }
        case actions.LOAD_GROUPS_SUCCESS: {
            return {
                ...state,
                groupsLoading: false,
                groupsNames: action.groups,
            }
        }
        case actions.LOAD_GROUPS_FAILURE: {
            return {
                ...state,
                groupsLoading: false,
            }
        }
        case actions.LOAD_PROFILE_REQUESTED: {
            return {
                ...state,
                isLoading: true,
            };
        }
        case actions.LOAD_PROFILE_SUCCESS: {
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