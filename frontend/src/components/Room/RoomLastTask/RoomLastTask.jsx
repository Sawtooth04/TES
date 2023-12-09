import React from 'react';
import {useNavigate} from "react-router-dom";

const RoomLastTask = ({ task }) => {
    const navigate = useNavigate();

    function onClick() {
        navigate(`/room/${task.roomID}/task/${task.roomTaskID}`);
    }

    return (
        <div className={"room-last-task"} onClick={onClick}>
            <p className={"room-last-task__name"}> {task.name} </p>
            <p className={"room-last-task__description"}> {task.description.slice(0, 14)} </p>
        </div>
    );
};

export default RoomLastTask;