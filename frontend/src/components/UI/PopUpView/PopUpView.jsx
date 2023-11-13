import React from 'react';

const PopUpView = ({ state, children }) => {
    return (
        <div className={`pop-up-view${state ? "_opened" : "_closed"}`}>
            {children}
        </div>
    );
};

export default PopUpView;