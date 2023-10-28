import React from 'react';

const ContextMenuButton = ({ onClick }) => {
    return (
        <div className={"context-menu-button"} onClick={onClick}>
            <img src={"/assets/images/icons/context-menu.png"} alt={"Context menu"}/>
        </div>
    );
};

export default ContextMenuButton;