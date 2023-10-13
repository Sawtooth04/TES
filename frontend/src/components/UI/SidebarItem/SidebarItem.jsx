import React from 'react';

const SidebarItem = ({ src, alt, text }) => {
    return (
        <button className={"sidebar__item sidebar-item"}>
            <img src={src} alt={alt}/>
            <p className={"sidebar-item__text"}> { text } </p>
        </button>
    );
};

export default SidebarItem;