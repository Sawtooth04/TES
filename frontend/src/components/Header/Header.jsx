import React, {useEffect, useState} from 'react';

const Header = ({ onSidebarClick, onNotificationsClick, switchCreateRoomDialogState, username, onUserClick }) => {
    return (
        <header className={"header main__header"}>
            <button className={"header__sidebar-button"} onClick={onSidebarClick}>
                <img src={"/assets/images/icons/sidebar.png"} alt={"Sidebar"}/>
            </button>
            <img className={"header__logo"} src={"/assets/images/logo/text-logo.png"} alt={"Logo"}/>
            <nav className={"header__navigation navigation"}>
                <button className={"header__navigation__button navigation__button"} onClick={switchCreateRoomDialogState}>
                    <img src={"/assets/images/icons/add-course.png"} alt={"Add room"}/>
                </button>
                <button className={"header__navigation__button navigation__button"} onClick={onNotificationsClick}>
                    <img src={"/assets/images/icons/notifications.png"} alt={"Notifications"}/>
                </button>
            </nav>
            <p className={"header__user"} onClick={onUserClick}> {username} </p>
        </header>
    );
};

export default Header;