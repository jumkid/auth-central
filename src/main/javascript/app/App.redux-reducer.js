import { actionTypes } from './App.redux-actions'

const clientDetailsReducer = ( state = {}, action )=> {

    const newState = {
        ...state
    };

    switch (action.type) {
        case actionTypes.CHANGE_CLIENT_DETAILS_ID:
            return {
                ...state,
                clientDetailsId: action.clientDetailsId
            };
        default: return newState;
    }
};

const userReducer = ( state = {}, action )=> {

    const newState = {
        ...state
    };

    switch (action.type) {
        case actionTypes.CHANGE_USERNAME:
            return {
                ...state,
                username: action.username
            };
        default: return newState;
    }
};

export {
    clientDetailsReducer,
    userReducer
}
