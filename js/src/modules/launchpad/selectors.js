import { createSelector } from 'reselect';
import type {RootState} from "../../rootState";

export const getUserState = (state: RootState) => state.userState;
export const getIsLoading = (state: RootState) => state.isLoading;

export const getUserInfo = createSelector([getUserState, getIsLoading], (userState, isLoading: boolean) =>
    isLoading === false ? userState : {},
);