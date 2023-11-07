import React from 'react';

const SuccessOutput = ({ children }) => {
    return (
        <div className={"solution-output__success-output solution-output__output"}>
            <p className={"solution-output__success-output__text"}> {children} </p>
        </div>
    );
};

export default SuccessOutput;