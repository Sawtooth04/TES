import React from 'react';

const UnverifiedTaskLabel = ({ task }) => {
    return (
        <div className={"room-teaching__body__unverified__task unverified-task-label"}>
            <img className={"unverified-task-label__icon"} src={"/assets/images/icons/task.png"} alt={"Icon"}/>
            <div className={"unverified-task-label__body"}>
                <p className={"unverified-task-label__body__header"}> Задание: {task.name} </p>
                <p className={"unverified-task-label__body__date"}> { (new Date(task.added)).toLocaleString("ru") } </p>
            </div>
        </div>
    );
};

export default UnverifiedTaskLabel;