import React from 'react';
import SidebarItem from "../UI/SidebarItem/SidebarItem";

const Sidebar = ({ hidden }) => {
    return (
        <nav className={`main__wrapper__sidebar sidebar sidebar_${hidden ? 'hidden' : 'opened'}`}>
            <SidebarItem src={"/assets/images/icons/home.png"} alt={"Icon"} text={"Главная"}/>
            <div className={"sidebar__delimiter"}/>
            <SidebarItem src={"/assets/images/icons/taught-courses.png"} alt={"Icon"} text={"Ваши комнаты"}/>
            <SidebarItem src={"/assets/images/icons/your-courses.png"} alt={"Icon"} text={"Комнаты"}/>
        </nav>
    );
};

export default Sidebar;