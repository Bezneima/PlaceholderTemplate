import type {UserState} from "./modules/launchpad/model";

export type RootState = {
    isLoading:boolean,
    userState:UserState,
}