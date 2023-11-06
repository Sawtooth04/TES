import React from 'react';

const ErrorOutput = ({ children }) => {
    return (
        <div className={"solution-output__error-output solution-output__output"}>
            <p className={"solution-output__error-output__text"}> {children} </p>
        </div>
    );
};

export default ErrorOutput;