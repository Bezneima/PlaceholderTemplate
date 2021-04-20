import type {UserState} from "./modules/launchpad/model";
import type {ProfileState} from "./modules/profile/model";
import type {FilesState, UserState} from "./modules/launchpad/model";

export type RootState = {
    isLoading:boolean,
    userState:UserState,
    filesState:FilesState
    profileState:ProfileState,
}