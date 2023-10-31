import React from 'react';
import ContextMenuButton from "../../../UI/ContextMenuButton/ContextMenuButton";
import {useNavigate} from "react-router-dom";

const RoomTaskLabel = ({ task }) => {
    const navigate = useNavigate();

    function onClick() {
        navigate(`/room/${task.roomID}/task/${task.roomTaskID}`);
    }

    return (
        <div className={"room-tasks__tasks__task room-task-label"} onClick={onClick}>
            <img className={"room-task-label__icon"} src={"/assets/images/icons/task.png"} alt={"Icon"}/>
            <div className={"room-task-label__body"}>
                <p className={"room-task-label__body__header"}> Добавлено новое задание: {task.name} </p>
                <p className={"room-task-label__body__date"}> { (new Date(task.added)).toLocaleString("ru") } </p>
            </div>
            <ContextMenuButton />
        </div>
    );
};

export default RoomTaskLabel;