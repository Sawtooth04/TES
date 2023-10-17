import React from 'react';

const NearTask = ({ task }) => {
    return (
        <div className={"near-task room-label__nearest-tasks__near-task"}>
            <p className={"near-task__deadline"}> Срок сдачи: Четверг </p>
            <p className={"near-task__name"}> {task.name} </p>
        </div>
    );
};

export default NearTask;