import type {UserState} from "./modules/launchpad/model";
import type {ProfileState} from "./modules/profile/model";

export type RootState = {
    isLoading:boolean,
    userState:UserState,
    profileState:ProfileState,
}