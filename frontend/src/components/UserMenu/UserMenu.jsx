import React from 'react';
import {useNavigate} from "react-router-dom";

const UserMenu = ({ hidden }) => {
    const navigate = useNavigate();

    async function onLogout() {
        await fetch("/authentication/logout", {
            method: "get"
        });
        navigate("/login");
    }

    return (
        <div className={`user-menu user-menu_${hidden ? 'hidden' : 'opened'}`}>
            <button className={"user-menu__button"} onClick={onLogout}> Выйти </button>
        </div>
    );
};

export default UserMenu;