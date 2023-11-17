import React from 'react';

const RoomTeachingChatMessage = ({ message }) => {
    return (
        <div className={`room-room__teaching__body__chat__messages__message${message.own ? " message_own" : ""}`}>
            <div className={"room-room__teaching__body__chat__messages__message__header"}>
                <p className={"room-room__teaching__body__chat__messages__message__header__name"}> { message.name } </p>
                <p className={"room-room__teaching__body__chat__messages__message__header__time"}>
                    { (new Date(message.sent)).toLocaleString("ru") }
                </p>
            </div>
            <p className={"room-room__teaching__body__chat__messages__message__text"}> { message.text } </p>
        </div>
    );
};

export default RoomTeachingChatMessage;