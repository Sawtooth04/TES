import React, {useEffect, useState} from 'react';
import {SolutionStatus} from "../../../../../constants";
import InfoOutput from "./InfoOutput/InfoOutput";
import ErrorOutput from "./ErrorOutput/ErrorOutput";
import SuccessOutput from "./SuccessOutput/SuccessOutput";
import IncorrectResultOutput from "./IncorrectResultOutput/IncorrectResultOutput";

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

            { (solutionStatus === SolutionStatus.TESTED && testResult.isCompiled && testResult.isSuccessful) ?
                <SuccessOutput>
                    Проект прошел тестирование.
                </SuccessOutput> : null
            }

            { (solutionStatus === SolutionStatus.TESTED && testResult.isCompiled && !testResult.isSuccessful) ? [
                <ErrorOutput>
                    <p> Проект не прошел тестирование. </p>
                    <p> {`Пройдено тестов: ${testResult.correctLaunchResults.length}.`} </p>
                    <p> {`Не пройдено тестов: ${testResult.incorrectLaunchResults.length}.`} </p>
                </ErrorOutput>,

                ...testResult.incorrectLaunchResults.map((result) => {
                     return <IncorrectResultOutput launchResult={result}/>
                 })] : null
            }
        </div>
    );
};

export default RoomTaskControlsSolutionOutput;