import React from 'react';

const RoomTaskControls = () => {
    return (
        <div className={"room-task__controls"}>
            <div className={"room-task__controls__solution"}>
                <div className="room-task__controls__solution__header">
                    <p className={"room-task__controls__solution__header__title"}> Мое решение </p>
                </div>
                <div className="room-task__controls__solution__files">
                </div>
                <button className={"room-task__controls__solution__submit-button"}> Сдать </button>
            </div>
            <div className={"room-task__controls__solution-comments"}>

            </div>
        </div>
    );
};

export default RoomTaskControls;