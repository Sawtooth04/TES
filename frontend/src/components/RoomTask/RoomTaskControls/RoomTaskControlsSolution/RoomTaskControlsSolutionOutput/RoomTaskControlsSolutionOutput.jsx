import React, {useState} from 'react';
import {SolutionStatus} from "../../../../../constants";
import InfoOutput from "./InfoOutput/InfoOutput";

const RoomTaskControlsSolutionOutput = ({ solutionStatus }) => {

    return (
        <div className={"room-task__controls__solution__content__output solution-output"}>
            { (solutionStatus === SolutionStatus.EMPTY) ?
                <InfoOutput>
                    Загрузите проект для отправки на проверку. Все данные будут выведены в это окно.
                </InfoOutput> : null }

            { (solutionStatus === SolutionStatus.LOADING) ?
                <InfoOutput>
                    Выполняется загрузка проекта. Пожалуйста, подождите.
                </InfoOutput> : null }

            { (solutionStatus === SolutionStatus.LOADED) ?
                <InfoOutput>
                    Проект загружен. Выполняется проверка.
                </InfoOutput> : null }
        </div>
    );
};

export default RoomTaskControlsSolutionOutput;