import React from 'react';

const InfoOutput = ({ children }) => {
    return (
        <div className={"solution-output__info-output solution-output__output"}>
            <p className={"solution-output__info-output__text"}> {children} </p>
        </div>
    );
};

export default InfoOutput;