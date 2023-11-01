import React, {useEffect, useState} from 'react';
import {useParams} from "react-router-dom";
import {maxTasksPerPagesCount, tasksPerPagesCount} from "../../../constants";
import RoomLastTask from "../RoomLastTask/RoomLastTask";
import RoomTaskLabel from "./RoomTaskLabel/RoomTaskLabel";
import CreateRoomTaskForm from "../../UI/CreateRoomTaskForm/CreateRoomTaskForm";
import InfiniteScrollPaginator from "../../UI/InfiniteScrollPaginator/InfiniteScrollPaginator";

const RoomTasks = () => {
    const { roomID } = useParams();
    const [latestTasks, setLatestTasks]  = useState([]);
    const [createTaskDialogOpened, setCreateTaskDialogOpened] = useState(false);
    const [tasks, setTasks] = useState([]);

    useEffect(() => {
        void getLatestTasks();
    }, [])

    async function getLatestTasks() {
        let response = await fetch(`/task/get-latest?roomID=${roomID}`, {
            method: "get",
            headers: {
                'Accept': 'application/json'
            }
        })
        if (response.ok)
            setLatestTasks(await response.json());
    }

    function switchCreateTaskDialogState() {
        setCreateTaskDialogOpened(!createTaskDialogOpened);
    }

    return (
        <div className={"room__content__body__tasks room-tasks"}>
            {createTaskDialogOpened ? <CreateRoomTaskForm onCreate={switchCreateTaskDialogState}/> : null}
            <div className="room-tasks__last-updates">
                {latestTasks.map((task) => {
                    return <RoomLastTask task={task} key={task.roomTaskID}/>
                })}
            </div>
            <div className="room-tasks__tasks">
                <InfiniteScrollPaginator param={roomID} paramName={"roomID"} endpoint={'/task/get-page'} data={tasks}
                    countByPage={tasksPerPagesCount} maxCountByPage={maxTasksPerPagesCount} updateData={setTasks}>
                    <div className={"room-tasks__tasks__add-button"} onClick={switchCreateTaskDialogState}>
                        <img src={"/assets/images/icons/add-task.png"} alt={"Add task"}/>
                    </div>
                    {tasks.map((task) => {
                        return <RoomTaskLabel task={task} key={task.roomTaskID}/>
                    })}
                </InfiniteScrollPaginator>
            </div>
        </div>
    );
};

export default RoomTasks;