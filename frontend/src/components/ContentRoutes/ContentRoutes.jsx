import React from 'react';
import {Route, Routes} from "react-router-dom";
import Header from "../Header/Header";

const ContentRoutes = ({ onNavigate }) => {
    function onMount() {
        onNavigate();
    }

    return (
        <div className="main">
            <Header/>
            <div className="main__wrapper">
                <div className="main__wrapper__content">
                    <Routes>
                        <Route path="*" element={<div/>}/>
                    </Routes>
                </div>
            </div>
        </div>
    );
};

export default ContentRoutes;