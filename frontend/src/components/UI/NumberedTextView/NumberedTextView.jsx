import React from 'react';

const NumberedTextView = ({ text }) => {
    return (
        <div className={"numbered-text-view"}>
            {text.map((line, index) => {
                return <div className="numbered-text-view__line" key={index}>
                    <p className="numbered-text-view__line__number"> {index + 1} </p>
                    <p className="numbered-text-view__line__text"> {line.replace("\t", "\u2003")} </p>
                </div>
            })}
        </div>
    );
};

export default NumberedTextView;