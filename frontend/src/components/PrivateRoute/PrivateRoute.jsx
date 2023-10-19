import React, {useEffect, useState} from 'react';
import {Navigate} from "react-router-dom";
import ContentRoutes from "../ContentRoutes/ContentRoutes";

const PrivateRoute = () => {
    const [isLoggedIn, setIsLoggedIn] = useState(true);

    useEffect(() => {
        void onNavigate()
    }, [])

    async function onNavigate() {
        let response = await fetch("/authentication/check", {
            method: 'get',
            headers: {Accept: 'application/json'}
        });
        setIsLoggedIn(await response.json());
    }

    return isLoggedIn ? <ContentRoutes onNavigate={onNavigate}/> : <Navigate to={"/login"} replace/>
};

export default PrivateRoute;