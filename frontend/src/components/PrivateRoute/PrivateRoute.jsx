import React, {useState} from 'react';
import {Navigate} from "react-router-dom";
import ContentRoutes from "../ContentRoutes/ContentRoutes";

const PrivateRoute = () => {
    const [isLoggedIn, setIsLoggedIn] = useState(true);

    async function onNavigate() {
        setIsLoggedIn(true);
    }

    return isLoggedIn ? <ContentRoutes onNavigate={onNavigate}/> : <Navigate to={"/login"} replace/>
};

export default PrivateRoute;