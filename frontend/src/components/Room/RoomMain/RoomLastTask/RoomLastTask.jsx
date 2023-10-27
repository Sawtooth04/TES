import React from 'react';

const RoomLastTask = ({ task }) => {
    return (
        <div className={"room-main__last-updates__task room-last-task"}>
            <p className={"room-last-task__name"}> {task.name} </p>
            <p className={"room-last-task__description"}> {task.description.slice(0, 30)} </p>
        </div>
    );
};

export default RoomLastTask;