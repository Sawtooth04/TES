import React from 'react';

const RoomTeachingChatMessage = ({ message }) => {
    return (
        <div className={`message${message.own ? " message_own" : ""}`}>
            <div className={"message__header"}>
                <p className={"message__header__name"}> { message.name } </p>
                <p className={"message__header__time"}>
                    { (new Date(message.sent)).toLocaleString("ru") }
                </p>
            </div>
            <p className={"message__text"}> { message.text } </p>
        </div>
    );
};

export default RoomTeachingChatMessage;