import React, {useState} from 'react';
import {Route, Routes} from "react-router-dom";
import Header from "../Header/Header";
import Sidebar from "../Sidebar/Sidebar";
import RoomsList from "../RoomsList/RoomsList";
import CreateRoomForm from "../CreateRoomForm/CreateRoomForm";

const ContentRoutes = ({ onNavigate }) => {
    const [isSidebarHidden, setIsSidebarHidden] = useState(true);
    const [createRoomDialogOpened, setCreateRoomDialogOpened] = useState(false);

    function onMount() {
        onNavigate();
    }

    function openCreateRoomDialog() {
        setCreateRoomDialogOpened(true);
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
            <Header onSidebarClick={onSidebarStateSwitch} openCreateRoomDialog={openCreateRoomDialog}/>
            <div className="main__wrapper">
                <Sidebar hidden={isSidebarHidden}/>
                <div className="main__wrapper__content" onClick={hideSidebar}>
                    {(createRoomDialogOpened) ? <CreateRoomForm/> : null}
                    <Routes>
                        <Route path="*" element={<RoomsList />}/>
                    </Routes>
                </div>
            </div>
        </div>
    );
};

export default ContentRoutes;