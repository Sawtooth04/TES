import React, {useState} from 'react';
import InfiniteScrollPaginator from "../../../UI/InfiniteScrollPaginator/InfiniteScrollPaginator";
import {maxTasksPerPagesCount, tasksPerPagesCount} from "../../../../constants";
import VerifiedTaskLabel from "./VerifiedTaskLabel/VerifiedTaskLabel";

const RoomTeachingVerified = ({ roomID }) => {
    const [verifiedTasks, setVerifiedTasks] = useState([]);
    const [solutionsDictionary, setSolutionsDictionary] = useState({});

    async function onTaskClick(roomTaskID) {
        if (typeof(solutionsDictionary[roomTaskID]) === "undefined") {
            let newDictionary;
            let response = await fetch(`/solution/get-verified?roomTaskID=${roomTaskID}`, {
                method: "get",
                headers: {"Accept": "application/json"}
            });

            newDictionary = {...solutionsDictionary}
            newDictionary[roomTaskID.toString()] = await response.json();
            setSolutionsDictionary(newDictionary);
        }
    }

    return (
        <div className={"room__teaching__body__verified"}>
            <InfiniteScrollPaginator params={{"roomID": roomID}} endpoint={'/task/get-verified-page'} data={verifiedTasks}
                countByPage={tasksPerPagesCount} maxCountByPage={maxTasksPerPagesCount} updateData={setVerifiedTasks}>
                {verifiedTasks.map((task) => {
                    return <VerifiedTaskLabel task={task} key={task.roomTaskID} onClick={onTaskClick}
                        solutions={solutionsDictionary[`${task.roomTaskID}`]}/>
                })}
            </InfiniteScrollPaginator>
        </div>
    );
};

export default RoomTeachingVerified;