import React, {useEffect, useState} from 'react';
import {SolutionStatus} from "../../../../../constants";
import InfoOutput from "./InfoOutput/InfoOutput";
import ErrorOutput from "./ErrorOutput/ErrorOutput";

const RoomTaskControlsSolutionOutput = ({ solutionStatus, testResult }) => {
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

            { (solutionStatus === SolutionStatus.TESTING || solutionStatus === SolutionStatus.LOADED) ?
                <InfoOutput>
                    Проект загружен. Выполняется проверка.
                </InfoOutput> : null }

            { (solutionStatus === SolutionStatus.TESTED && testResult.isCompiled) ?
                <InfoOutput>
                    Проект успешно скомпилирован.
                </InfoOutput> : null
            }

            { (solutionStatus === SolutionStatus.TESTED && !testResult.isCompiled) ?
                <ErrorOutput>
                    Произошла ошибка компиляции! Проверьте правильность написанного кода.
                </ErrorOutput> : null
            }
        </div>
    );
};

export default RoomTaskControlsSolutionOutput;