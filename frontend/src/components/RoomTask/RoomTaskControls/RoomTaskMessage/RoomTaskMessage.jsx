import React from 'react';

const RoomTaskMessage = ({ message }) => {
    return (
        <div className={"room-task__controls__solution-messages__message room-task-message"}>
            <div className="room-task-message__header">
                <p className={"room-task-message__header__customer-name"}> {message.name} </p>
                <p className={"room-task-message__header__sent"}> {new Date(message.sent).toLocaleString("ru")} </p>
            </div>
            <p className={"room-task-message__message"}> {message.text} </p>
        </div>
    );
};

export default RoomTaskMessage;