import React, {useState} from 'react';
import {maxTasksPerPagesCount, tasksPerPagesCount} from "../../../../constants";
import InfiniteScrollPaginator from "../../../UI/InfiniteScrollPaginator/InfiniteScrollPaginator";
import UnverifiedTaskLabel from "./UnverifiedTaskLabel/UnverifiedTaskLabel";

const RoomTeachingUnverified = ({ roomID }) => {
    const [unverifiedTasks, setUnverifiedTasks] = useState([]);

    return (
        <div className={"room__teaching__body__unverified"}>
            <InfiniteScrollPaginator param={roomID} paramName={"roomID"} endpoint={'/task/get-unverified-page'} data={unverifiedTasks}
                countByPage={tasksPerPagesCount} maxCountByPage={maxTasksPerPagesCount} updateData={setUnverifiedTasks}>
                {unverifiedTasks.map((task) => {
                    return <UnverifiedTaskLabel task={task} key={task.roomTaskID}/>
                })}
            </InfiniteScrollPaginator>
        </div>
    );
};

export default RoomTeachingUnverified;