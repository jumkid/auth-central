const actionTypes = {
    CHANGE_USERNAME: 'CHANGE_USERNAME',
    CHANGE_CLIENT_DETAILS_ID: 'CHANGE_CLIENT_DETAILS_ID'
};

const changeClientDetailsId = (cid) => ({
    type: actionTypes.CHANGE_CLIENT_DETAILS_ID,
    clientDetailsId: cid
});

const changeUsername = (username) => ({
    type: actionTypes.CHANGE_USERNAME,
    username: username
});

export {
    actionTypes,
    changeClientDetailsId,
    changeUsername
}
