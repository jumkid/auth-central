import React from 'react'
import MainLayout from "./MainLayout";
import Provider from "react-redux/es/components/Provider";
import store from "./App.redux-store";

const App = () => {
    return (
        <Provider store={store}>
            <MainLayout />
        </Provider>
    )
}

export default App
