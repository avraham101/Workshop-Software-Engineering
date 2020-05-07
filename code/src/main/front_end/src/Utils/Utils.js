/**
 * passing data between react components
 * @param history - browser history
 * @param path - destination path
 * @param fromPath - source path
 * @param state - data to pass
 */
export const pass =(history,path,fromPath,state) =>{
    history.push({
        pathname: path,
        fromPath: fromPath,
        state: state
    });
}

