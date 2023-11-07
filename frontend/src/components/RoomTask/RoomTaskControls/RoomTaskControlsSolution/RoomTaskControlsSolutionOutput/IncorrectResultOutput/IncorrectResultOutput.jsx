import React from 'react';
import FoldView from "../../../../../UI/FoldView/FoldView";

const IncorrectResultOutput = ({ launchResult }) => {
    function resultFormat(arr) {
        let i = 0;

        return arr.map((str) => {
            i++;
            return {str: `${i}. ${str}`, key: Date.now() + i};
        });
    }

    return (
        <div className={"solution-output__error-output solution-output__output"}>
            <p> {`Код выхода: ${launchResult.launchResults.exitCode}`}  </p>
            <FoldView header={`Входные данные: ${launchResult.launchResults.in.length}`}>
                {resultFormat(launchResult.launchResults.in).map((input) => {
                    return <p key={input.key}> {input.str} </p>
                })}
            </FoldView>
            <FoldView header={`Выходные данные: ${launchResult.launchResults.out.length}`}>
                {resultFormat(launchResult.launchResults.out).map((output) => {
                    return <p key={output.key}> {output.str} </p>
                })}
            </FoldView>
            <FoldView header={`Ожидаемые данные: ${launchResult.expected.length}`}>
                {resultFormat(launchResult.expected).map((output) => {
                    return <p key={output.key}> {output.str} </p>
                })}
            </FoldView>
            <FoldView header={`Исключения и ошибки выполнения: ${launchResult.launchResults.err.length}`}>
                {resultFormat(launchResult.launchResults.err).map((err) => {
                    return <p key={err.key}> {err.str} </p>
                })}
            </FoldView>
        </div>
    );
};

export default IncorrectResultOutput;