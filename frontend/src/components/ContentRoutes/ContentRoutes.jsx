import React, {useEffect, useState} from 'react';
import {Route, Routes} from "react-router-dom";
import Header from "../Header/Header";
import Sidebar from "../Sidebar/Sidebar";
import RoomsList from "../RoomsList/RoomsList";
import CreateRoomForm from "../CreateRoomForm/CreateRoomForm";
import Room from "../Room/Room";
import RoomTask from "../RoomTask/RoomTask";
import OwnRoomsList from "../OwnRoomsList/OwnRoomsList";
import StudyingRoomsList from "../StudyingRoomsList/StudyingRoomsList";
import Notifications from "../Notifications/Notifications";

const ContentRoutes = ({ onNavigate }) => {
    const [isSidebarHidden, setIsSidebarHidden] = useState(true);
    const [isNotificationsHidden, setIsNotificationsHidden] = useState(true);
    const [createRoomDialogOpened, setCreateRoomDialogOpened] = useState(false);

    useEffect(() => {
        onNavigate();
    }, []);

    function onMount() {
        onNavigate();
    }

    function switchCreateRoomDialogState() {
        setCreateRoomDialogOpened(!createRoomDialogOpened);
    }

    function onSidebarStateSwitch() {
        setIsSidebarHidden(!isSidebarHidden);
    }

    function onNotificationsStateSwitch() {
        setIsNotificationsHidden(!isNotificationsHidden);
    }

    function hideMenus(event) {
        setIsSidebarHidden(true);
        setIsNotificationsHidden(true);
        event.stopPropagation();
    }

    return (
        <div className="main">
            <Header onSidebarClick={onSidebarStateSwitch} onNotificationsClick={onNotificationsStateSwitch} switchCreateRoomDialogState={switchCreateRoomDialogState}/>
            <div className="main__wrapper">
                <Sidebar hidden={isSidebarHidden}/>
                <Notifications hidden={isNotificationsHidden}/>
                <div className="main__wrapper__content" onClick={hideMenus}>
                    {(createRoomDialogOpened) ? <CreateRoomForm onCreate={switchCreateRoomDialogState}/> : null}
                    <Routes>
                        <Route path="/room/:roomID/task/:roomTaskID" element={<RoomTask onMount={onMount}/>}/>
                        <Route path="/room/:roomID/*" element={<Room onMount={onMount}/>}/>
                        <Route path="/rooms/own" element={<OwnRoomsList onMount={onMount}/>}/>
                        <Route path="/rooms/studying" element={<StudyingRoomsList onMount={onMount}/>}/>
                        <Route path="/rooms/join" element={<RoomsList onMount={onMount} isJoining={true}/>}/>
                        <Route path="*" element={<RoomsList onMount={onMount} isJoining={false}/>}/>
                    </Routes>
                </div>
            </div>
        </div>
    );
};

export default ContentRoutes;