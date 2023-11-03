import React from 'react';

const ContextMenu = ({ children }) => {
    return (
        <div className={"context-menu"}>
            { children }
        </div>
    );
};

export default ContextMenu;