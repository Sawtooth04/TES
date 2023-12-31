import React, {useEffect, useRef, useState} from 'react';
import JSZip from "jszip";
import ChooseLanguageDialog from "./ChooseLanguageDialog/ChooseLanguageDialog";
import RoomTaskControlsSolutionOutput from "./RoomTaskControlsSolutionOutput/RoomTaskControlsSolutionOutput";
import {SolutionStatus} from "../../../../constants";

const RoomTaskControlsSolution = ({ roomID, roomTaskID }) => {
    const [solution, setSolution] = useState(null);
    const [solutionName, setSolutionName] = useState(null);
    const [languages, setLanguages] = useState(null);
    const [language, setLanguage] = useState(null);
    const [testResult, setTestResult] = useState(null);
    const [chooseLanguageDialogOpened, setChooseLanguageDialogOpened] = useState(false);
    const [solutionStatus, setSolutionStatus] = useState(SolutionStatus.EMPTY);
    const fileInput = useRef();

    useEffect(() => {
        async function getLanguages() {
            let response = await fetch("/language-configuration/get", { method: "get" });

            if (response.ok)
                setLanguages(await response.json());
        }

        void getLanguages();
        void getSolution();
    }, [])

    useEffect(() => {
        async function testSolution() {
            let params = new URLSearchParams();
            params.set("roomID", roomID);
            params.set("language", language);
            params.set("taskID", roomTaskID);
            params.set("solution", solutionName);
            let response = await fetch(`/test/launch?${params.toString()}`, {
                method: "get"
            })
            setTestResult(await response.json());
            setSolutionStatus(SolutionStatus.TESTED);
        }

        if (solutionStatus === SolutionStatus.TESTING)
            void testSolution();
    }, [solutionStatus]);

    async function getSolution() {
        let response = await fetch(`/solution/get?roomTaskID=${roomTaskID}`, {
            method: "get",
            headers: {"Accept": "application/json"}
        });

        if (response.ok)
            try {
                setSolution(await response.json());
            }
            catch (err) {
                setSolution(null);
            }
    }

    function setLanguageCallback(newLanguage) {
        setLanguage(newLanguage);
        setSolutionStatus(SolutionStatus.TESTING);
    }

    function addSolution() {
        setSolutionStatus(SolutionStatus.LOADING);
        fileInput.current.click();
    }

    function switchChooseLanguageDialogOpened() {
        setChooseLanguageDialogOpened(!chooseLanguageDialogOpened);
    }

    async function sendSolution(solution) {
        let data = new FormData();
        data.append("roomID", roomID);
        data.append("taskID", roomTaskID);
        data.append("file", solution);
        let response = await fetch("/solution/upload", {
            method: "post",
            body: data
        })

        if (response.ok) {
            switchChooseLanguageDialogOpened();
            setSolutionStatus(SolutionStatus.LOADED);
        }
        else
            setSolutionStatus(SolutionStatus.EMPTY);
        fileInput.current.value = null;
    }

    async function onSolutionChange() {
        let root = fileInput.current.files[0].webkitRelativePath.split("/")[0];
        let zip = new JSZip();
        let reader;

        function getFilesCount() {
            let count = 0;

            for (let key in zip.files)
                if (zip.files[key].dir === false)
                    count++;
            return count;
        }

        async function readResultProcessing() {
            zip.file(this.name, this.reader.result);

            if (getFilesCount() === fileInput.current.files.length)  {
                let solution = await zip.generateAsync({ type: "blob" });
                solution.lastModifiedDate = new Date();
                solution.name = root + ".zip";
                await sendSolution(solution);
            }
        }

        for (let i = 0; i < fileInput.current.files.length; i++) {
            reader = new FileReader();
            reader.onload = readResultProcessing.bind({name: fileInput.current.files[i].webkitRelativePath, reader: reader});
            reader.readAsArrayBuffer(fileInput.current.files[i]);
        }
        setSolutionName(root);
    }

    async function setSolutionSuccessfullyTested() {
        let response = await fetch("/solution/set-successfully-tested", {
            method: "post",
            headers: {"Content-type": "application/json"},
            body: JSON.stringify(roomTaskID)
        })
        if (response.ok)
            await getSolution();
    }

    function clearSolution() {
        setSolutionStatus(SolutionStatus.EMPTY);
        setSolution(null);
        setTestResult(null);
    }

    return (
        <div className={"room-task__controls__solution"}>
            { (chooseLanguageDialogOpened) ? <ChooseLanguageDialog languages={languages} setLanguage={setLanguageCallback}
               onChoose={switchChooseLanguageDialogOpened}/> : null }
            <div className="room-task__controls__solution__header">
                <p className={"room-task__controls__solution__header__title"}> Мое решение </p>
            </div>
            <div className="room-task__controls__solution__content">
                <input type={"file"} ref={fileInput} onChange={onSolutionChange} webkitdirectory=""/>
                { (solution != null && solution.isSuccessfullyTested) ?
                    null : <RoomTaskControlsSolutionOutput solutionStatus={solutionStatus} testResult={testResult}/>
                }
            </div>
            <div className="room-task__controls__solution__controls">
                { (solution != null && solution.isSuccessfullyTested) ?
                    <p className={"room-task__controls__solution__controls__paragraph"}>
                        Проект прошел тестирование. Ожидайте результаты проверки преподавателем.
                    </p> :
                    (testResult != null && testResult.isSuccessful) ? [
                            <button className={"room-task__controls__solution__controls__submit-button"} key={1}
                                onClick={setSolutionSuccessfullyTested}>
                                Сдать
                            </button>,
                            <button className={"room-task__controls__solution__controls__submit-button"} key={2}
                                onClick={clearSolution}>
                                Сброс
                            </button>
                        ] :
                        <button className={"room-task__controls__solution__controls__submit-button"} onClick={addSolution}>
                            Загрузить
                        </button>
                }
            </div>
        </div>
    );
};

export default RoomTaskControlsSolution;