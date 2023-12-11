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
import UserMenu from "../UserMenu/UserMenu";
import LoadingView from "../UI/LoadingView/LoadingView";

const ContentRoutes = ({ onNavigate }) => {
    const [username, setUsername] = useState("");
    const [isSidebarHidden, setIsSidebarHidden] = useState(true);
    const [isNotificationsHidden, setIsNotificationsHidden] = useState(true);
    const [isUserMenuHidden, setIsUserMenuHidden] = useState(true);
    const [createRoomDialogOpened, setCreateRoomDialogOpened] = useState(false);
    const [isLoading, setIsLoading] = useState(false);

    useEffect(() => {
        async function getUserName() {
            let response = await fetch("/authentication/get/username");
            setUsername(await response.text());
        }

        onNavigate();
        void getUserName();
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

    function onUserMenuStateSwitch() {
        setIsUserMenuHidden(!isUserMenuHidden);
    }

    function hideMenus(event) {
        setIsSidebarHidden(true);
        setIsNotificationsHidden(true);
        setIsUserMenuHidden(true);
        event.stopPropagation();
    }

    function showLoading() {
        setIsLoading(true);
    }

    function hideLoading() {
        setIsLoading(false);
    }

    return (
        <div className="main">
            <Header onSidebarClick={onSidebarStateSwitch} onNotificationsClick={onNotificationsStateSwitch}
                switchCreateRoomDialogState={switchCreateRoomDialogState} username={username} onUserClick={onUserMenuStateSwitch}/>
            <div className="main__wrapper">
                {isLoading ? <LoadingView/> : null}
                <Sidebar hidden={isSidebarHidden}/>
                <Notifications hidden={isNotificationsHidden}/>
                <UserMenu hidden={isUserMenuHidden}/>
                <div className="main__wrapper__content" onClick={hideMenus}>
                    {(createRoomDialogOpened) ? <CreateRoomForm onCreate={switchCreateRoomDialogState}/> : null}
                    <Routes>
                        <Route path="/room/:roomID/task/:roomTaskID" element={<RoomTask onMount={onMount} showLoading={showLoading}
                            hideLoading={hideLoading}/>}/>
                        <Route path="/room/:roomID/*" element={<Room onMount={onMount} showLoading={showLoading} hideLoading={hideLoading}/>}/>
                        <Route path="/rooms/own" element={<OwnRoomsList onMount={onMount} showLoading={showLoading} hideLoading={hideLoading}/>}/>
                        <Route path="/rooms/studying" element={<StudyingRoomsList onMount={onMount} showLoading={showLoading} hideLoading={hideLoading}/>}/>
                        <Route path="/rooms/join" element={<RoomsList onMount={onMount} isJoining={true} showLoading={showLoading} hideLoading={hideLoading}/>}/>
                        <Route path="*" element={<RoomsList onMount={onMount} isJoining={false} showLoading={showLoading} hideLoading={hideLoading}/>}/>
                    </Routes>
                </div>
            </div>
        </div>
    );
};

export default ContentRoutes;