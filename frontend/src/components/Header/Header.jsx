import React from 'react';

const Header = () => {
    return (
        <header className={"header main__header"}>
            <button className={"header__sidebar-button"}>
                <img src={"/assets/images/icons/sidebar.png"} alt={"Sidebar"}/>
            </button>
            <img className={"header__logo"} src={"/assets/images/logo/text-logo.png"} alt={"Logo"}/>
            <nav className={"header__navigation navigation"}>
                <button className={"header__navigation__button navigation__button"}>
                    <img src={"/assets/images/icons/add-course.png"} alt={"Sidebar"}/>
                </button>
                <button className={"header__navigation__button navigation__button"}>
                    <img src={"/assets/images/icons/notifications.png"} alt={"Sidebar"}/>
                </button>
            </nav>
        </header>
    );
};

export default Header;