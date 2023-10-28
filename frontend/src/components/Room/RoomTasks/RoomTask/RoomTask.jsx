import React from 'react';
import ContextMenuButton from "../../../UI/ContextMenuButton/ContextMenuButton";

const RoomTask = ({ task }) => {
    return (
        <div className={"room-tasks__tasks__task room-task"}>
            <img className={"room-task__icon"} src={"/assets/images/icons/task.png"} alt={"Icon"}/>
            <div className={"room-task__body"}>
                <p className={"room-task__body__header"}> Добавлено новое задание: {task.name} </p>
                <p className={"room-task__body__date"}> { (new Date(task.added)).toLocaleString("ru") } </p>
            </div>
            <ContextMenuButton />
        </div>
    );
};

export default RoomTask;