import { createSelector } from 'reselect';
import type {RootState} from "../../rootState";

export const getState = (state: RootState) => state.launchpad;
export const getIsLoading = (state: RootState) => state.launchpad.isLoading;

export const getAllFiles = createSelector([getState, getIsLoading], (state, isLoading: boolean) =>
    isLoading === false ? state.files : [],
);