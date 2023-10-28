import React from 'react';

const RoomLastTask = ({ task }) => {
    return (
        <div className={"room-last-task"}>
            <p className={"room-last-task__name"}> {task.name} </p>
            <p className={"room-last-task__description"}> {task.description.slice(0, 18)} </p>
        </div>
    );
};

export default RoomLastTask;