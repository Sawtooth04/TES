import React, {useEffect, useState} from 'react';
import FoldView from "../../../../UI/FoldView/FoldView";
import {useNavigate, useParams} from "react-router-dom";

const UnverifiedTaskLabel = ({ task, onClick, solutions }) => {
    const { roomID } = useParams();
    const [isFoldViewOpened, setIsFoldViewOpened] = useState(false);
    const navigate = useNavigate();

    async function onTaskClick() {
        onClick(task.roomTaskID);
        setIsFoldViewOpened(!isFoldViewOpened);
    }

    function onSolutionClick(roomSolutionID) {
        navigate(`/room/${roomID}/teaching/unverified/solution/${roomSolutionID}`);
    }

    return (
        <div className={"room-teaching__body__unverified__task unverified-task-label"} onClick={onTaskClick}>
            <div className={"unverified-task-label__wrapper"}>
                <img className={"unverified-task-label__wrapper__icon"} src={"/assets/images/icons/task.png"} alt={"Icon"}/>
                <div className={"unverified-task-label__wrapper__body"}>
                    <p className={"unverified-task-label__wrapper__body__header"}> Задание: {task.name} </p>
                    <p className={"unverified-task-label__wrapper__body__date"}> { (new Date(task.added)).toLocaleString("ru") } </p>
                </div>
            </div>
            { (typeof(solutions) !== "undefined") ?
                <FoldView header={""} state={isFoldViewOpened}>
                    {solutions.map((solution) => {
                        return <p className={"unverified-task-label__fold-view__solution"} key={solution.roomSolutionID}
                            onClick={() => onSolutionClick(solution.roomSolutionID)}>
                            {solution.customerName}
                        </p>
                    })}
                </FoldView> : null
            }
        </div>
    );
};

export default UnverifiedTaskLabel;