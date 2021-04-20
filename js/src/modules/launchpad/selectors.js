import { createSelector } from 'reselect';
import type {RootState} from "../../rootState";

export const getUserState = (state: RootState) => state.userState;
export const getFilesState = (state: RootState) => state.filesState;
export const getIsLoading = (state: RootState) => state.userState.isLoading;

export const getUserInfo = createSelector([getUserState, getIsLoading], (userState, isLoading: boolean) =>
    isLoading === false ? userState : {},
);
export const getFilesInfo = createSelector([getFilesState, getIsLoading], (filesState, isLoading: boolean) =>
    isLoading === false ? filesState : {},
);
