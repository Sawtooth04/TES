import React from 'react';

const RoomTeachingMessage = ({ message }) => {
    return (
        <div className={`room__teaching__body__messages__message${message.isRead ? "" : " message_unread"}`}>
            <p className={"room__teaching__body__messages__message__name"}> {message.name} </p>
        </div>
    );
};

export default RoomTeachingMessage;