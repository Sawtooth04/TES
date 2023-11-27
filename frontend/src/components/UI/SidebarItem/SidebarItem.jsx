import React from 'react';

const SidebarItem = ({ src, alt, text, onClick }) => {
    return (
        <button className={"sidebar__item sidebar-item"} onClick={onClick}>
            <img src={src} alt={alt}/>
            <p className={"sidebar-item__text"}> { text } </p>
        </button>
    );
};

export default SidebarItem;