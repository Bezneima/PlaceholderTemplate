import type {RootState} from "../../rootState";
import {createSelector} from "reselect";

export const getProfileState = (state: RootState) => state.profileState;
export const getIsLoading = (state: RootState) => state.profileState.isLoading;

export const getProfileInfo = createSelector([getProfileState, getIsLoading], (profileState, isLoading: boolean) =>
    isLoading === false ? profileState : {},
    );
