import React, {useRef, useState} from 'react';
import JSZip from "jszip";

const RoomTaskControlsSolution = ({ roomID, roomTaskID }) => {
    const [solution, setSolution] = useState(null);
    const fileInput = useRef();

    function addSolution() {
        fileInput.current.click();
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
    }

    return (
        <div className={"room-task__controls__solution"}>
            <div className="room-task__controls__solution__header">
                <p className={"room-task__controls__solution__header__title"}> Мое решение </p>
            </div>
            <div className="room-task__controls__solution__files">
                <input type={"file"} ref={fileInput} onChange={onSolutionChange} webkitdirectory=""/>
            </div>
            { (solution != null) ?
                <button className={"room-task__controls__solution__submit-button"}> Сдать </button> :
                <button className={"room-task__controls__solution__submit-button"} onClick={addSolution}> Добавить решение </button>
            }
        </div>
    );
};

export default RoomTaskControlsSolution;