import React, {useState} from 'react';
import {useNavigate, useParams} from "react-router-dom";
import FoldView from "../../../../UI/FoldView/FoldView";

const VerifiedTaskLabel = ({ task, onClick, solutions }) => {
    const { roomID } = useParams();
    const [isFoldViewOpened, setIsFoldViewOpened] = useState(false);
    const navigate = useNavigate();

    async function onTaskClick() {
        onClick(task.roomTaskID);
        setIsFoldViewOpened(!isFoldViewOpened);
    }

    function onSolutionClick(roomSolutionID) {
        navigate(`/room/${roomID}/teaching/verified/solution/${roomSolutionID}`);
    }

    return (
        <div className={"room-teaching__body__verified__task verified-task-label"} onClick={onTaskClick}>
            <div className={"verified-task-label__wrapper"}>
                <img className={"verified-task-label__wrapper__icon"} src={"/assets/images/icons/task.png"} alt={"Icon"}/>
                <div className={"verified-task-label__wrapper__body"}>
                    <p className={"verified-task-label__wrapper__body__header"}> Задание: {task.name} </p>
                    <p className={"verified-task-label__wrapper__body__date"}> { (new Date(task.added)).toLocaleString("ru") } </p>
                </div>
            </div>
            { (typeof(solutions) !== "undefined") ?
                <FoldView header={""} state={isFoldViewOpened}>
                    {solutions.map((solution) => {
                        return <p className={"verified-task-label__fold-view__solution"} key={solution.roomSolutionID}
                            onClick={() => onSolutionClick(solution.roomSolutionID)}>
                            {solution.customerName}
                        </p>
                    })}
                </FoldView> : null
            }
        </div>
    );
};

export default VerifiedTaskLabel;