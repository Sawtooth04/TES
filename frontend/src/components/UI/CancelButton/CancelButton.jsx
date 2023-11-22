import React from 'react';

const CancelButton = ({ onClick }) => {
    return (
        <div className={"cancel-button"} onClick={onClick}>
            <div className={"cancel-button__first-line"}/>
            <div className={"cancel-button__second-line"}/>
        </div>
    );
};

export default CancelButton;