import React, {useState} from 'react';
import {Route, Routes} from "react-router-dom";
import Header from "../Header/Header";
import Sidebar from "../Sidebar/Sidebar";
import RoomLabel from "../RoomLabel/RoomLabel";
import RoomsList from "../RoomsList/RoomsList";

const ContentRoutes = ({ onNavigate }) => {
    const [isSidebarHidden, setIsSidebarHidden] = useState(true);

    function onMount() {
        onNavigate();
    }

    function onSidebarStateSwitch() {
        setIsSidebarHidden(!isSidebarHidden);
    }

    function hideSidebar(event) {
        setIsSidebarHidden(true);
        event.stopPropagation();
    }

    return (
        <div className="main">
            <Header onSidebarClick={onSidebarStateSwitch}/>
            <div className="main__wrapper">
                <Sidebar hidden={isSidebarHidden}/>
                <div className="main__wrapper__content" onClick={hideSidebar}>
                    <Routes>
                        <Route path="*" element={<RoomsList />}/>
                    </Routes>
                </div>
            </div>
        </div>
    );
};

export default ContentRoutes;