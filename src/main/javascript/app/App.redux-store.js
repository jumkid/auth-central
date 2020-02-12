import {clientDetailsReducer, userReducer} from "./App.redux-reducer";
import {combineReducers, createStore} from "redux";

const rootReducer = combineReducers({
    clientDetails: clientDetailsReducer,
    user: userReducer
});

const store = createStore(
    rootReducer,
    window.__REDUX_DEVTOOLS_EXTENSION__ && window.__REDUX_DEVTOOLS_EXTENSION__()
);

export default store;
