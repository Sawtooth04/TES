import React, {useState} from 'react';
import {maxTasksPerPagesCount, tasksPerPagesCount} from "../../../../constants";
import InfiniteScrollPaginator from "../../../UI/InfiniteScrollPaginator/InfiniteScrollPaginator";
import UnverifiedTaskLabel from "./UnverifiedTaskLabel/UnverifiedTaskLabel";
import {useParams} from "react-router-dom";

const RoomTeachingUnverified = () => {
    const {roomID} = useParams();
    const [unverifiedTasks, setUnverifiedTasks] = useState([]);
    const [solutionsDictionary, setSolutionsDictionary] = useState({});

    async function onTaskClick(roomTaskID) {
        if (typeof(solutionsDictionary[roomTaskID]) === "undefined") {
            let newDictionary;
            let response = await fetch(`/solution/get-unverified?roomTaskID=${roomTaskID}`, {
                method: "get",
                headers: {"Accept": "application/json"}
            });

            newDictionary = {...solutionsDictionary}
            newDictionary[roomTaskID.toString()] = await response.json();
            setSolutionsDictionary(newDictionary);
        }
    }

    return (
        <div className={"room__teaching__body__unverified"}>
            <InfiniteScrollPaginator params={{"roomID": roomID}} endpoint={'/task/get-unverified-page'} data={unverifiedTasks}
                countByPage={tasksPerPagesCount} maxCountByPage={maxTasksPerPagesCount} updateData={setUnverifiedTasks}>
                {unverifiedTasks.map((task) => {
                    return <UnverifiedTaskLabel task={task} key={task.roomTaskID} onClick={onTaskClick}
                        solutions={solutionsDictionary[`${task.roomTaskID}`]}/>
                })}
            </InfiniteScrollPaginator>
        </div>
    );
};

export default RoomTeachingUnverified;