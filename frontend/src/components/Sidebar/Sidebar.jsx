import React from 'react';
import SidebarItem from "../UI/SidebarItem/SidebarItem";
import {useNavigate} from "react-router-dom";

const Sidebar = ({ hidden }) => {
    const navigate = useNavigate();

    function onHome() {
        navigate("/");
    }

    function onJoin() {
        navigate("/rooms/join");
    }

    return (
        <nav className={`main__wrapper__sidebar sidebar sidebar_${hidden ? 'hidden' : 'opened'}`}>
            <SidebarItem src={"/assets/images/icons/home.png"} alt={"Icon"} text={"Главная"} onClick={onHome}/>
            <div className={"sidebar__delimiter"}/>
            <SidebarItem src={"/assets/images/icons/taught-courses.png"} alt={"Icon"} text={"Присоединиться"} onClick={onJoin}/>
            <SidebarItem src={"/assets/images/icons/taught-courses.png"} alt={"Icon"} text={"Ваши комнаты"} onClick={onHome}/>
            <SidebarItem src={"/assets/images/icons/your-courses.png"} alt={"Icon"} text={"Комнаты"} onClick={onHome}/>
        </nav>
    );
};

export default Sidebar;