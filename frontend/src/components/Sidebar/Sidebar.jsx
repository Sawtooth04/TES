import React from 'react';
import SidebarItem from "../UI/SidebarItem/SidebarItem";
import {useNavigate} from "react-router-dom";

const Sidebar = ({ hidden }) => {
    const navigate = useNavigate();

    function onHomePage() {
        navigate("/");
    }

    function onOwnRooms() {
        navigate("/rooms/own");
    }

    function onStudyingRooms() {
        navigate("/rooms/studying");
    }

    function onJoin() {
        navigate("/rooms/join");
    }

    return (
        <nav className={`main__wrapper__sidebar sidebar sidebar_${hidden ? 'hidden' : 'opened'}`}>
            <SidebarItem src={"/assets/images/icons/home.png"} alt={"Icon"} text={"Главная"} onClick={onHomePage}/>
            <div className={"sidebar__delimiter"}/>
            <SidebarItem src={"/assets/images/icons/link.png"} alt={"Icon"} text={"Присоединиться"} onClick={onJoin}/>
            <SidebarItem src={"/assets/images/icons/taught-courses.png"} alt={"Icon"} text={"Ваши комнаты"} onClick={onOwnRooms}/>
            <SidebarItem src={"/assets/images/icons/your-courses.png"} alt={"Icon"} text={"Комнаты"} onClick={onStudyingRooms}/>
        </nav>
    );
};

export default Sidebar;