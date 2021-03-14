export type LaunchpadState = {
    isLoading: boolean;
    files:string;
    files1: Array<FileInfo>;
};

export type FileInfo = {
    fileId: number,
    fileName: string,
    path: string,
    fileFormat:string,
    fileHashName:string,
    inputFieldsNames: Array<string>;
};