import React, {useEffect, useState} from 'react';
import {useParams} from "react-router-dom";
import {maxTasksPerPagesCount, tasksPerPagesCount} from "../../../constants";
import RoomLastTask from "../RoomLastTask/RoomLastTask";
import InfiniteScroll from "../../UI/InfiniteScroll/InfiniteScroll";
import RoomTask from "./RoomTask/RoomTask";

const RoomTasks = () => {
    const { roomID } = useParams();
    const [isLoading, setIsLoading] = useState(true);
    const [scrolledToTheEnd, setScrolledToTheEnd] = useState(false);
    const [tasks, setTasks]  = useState([]);
    const [tasksStart, setTasksStart] = useState(0);
    const [tasksEnd, setTasksEnd] = useState(10);
    const [isPrev, setIsPrev] = useState(true);
    const [latestTasks, setLatestTasks]  = useState([]);

    useEffect(() => {
        void getLatestTasks();
    }, [])

    useEffect(() => {
        void getTasks();
    }, [tasksStart, tasksEnd]);

    async function getTasks() {
        let params = new URLSearchParams();
        params.append('roomID', roomID);
        (isPrev) ? params.append('start', String(tasksStart)) :
            params.append('start', String(tasksEnd - tasksPerPagesCount));
        params.append('count', String(tasksPerPagesCount));
        setIsLoading(true);

        let response = await fetch(`/task/get?${params.toString()}`, {
            method: "get",
            headers: {
                'Content-Type': 'application/json',
                'Accept': 'application/json'
            }
        })
        if (response.ok)
            appendTasks(await response.json());
    }

    function appendTasks(arr) {
        if (!isPrev) {
            if (arr.length !== 0) {
                if (tasks.length + tasksPerPagesCount > maxTasksPerPagesCount)
                    tasks.splice(0, tasksPerPagesCount);
                setTasks([...tasks, ...arr])
            }
            else
                setScrolledToTheEnd(true);
        }
        else {
            if (tasks.length + tasksPerPagesCount > maxTasksPerPagesCount)
                tasks.splice(tasks.length - tasksPerPagesCount, tasksPerPagesCount);
            setTasks([...arr, ...tasks])
            setScrolledToTheEnd(false);
        }
        setIsLoading(false);
    }

    function onNextPage() {
        if (!isLoading && !scrolledToTheEnd) {
            let newEnd = tasksEnd + tasksPerPagesCount;

            setIsPrev(false);
            setTasksEnd(newEnd);
            if (newEnd - tasksStart > maxTasksPerPagesCount && newEnd !== 2 * maxTasksPerPagesCount)
                setTasksStart(tasksStart + tasksPerPagesCount)
        }
    }

    function onPrevPage() {
        if (tasksStart >= tasksPerPagesCount && !isLoading) {
            let newStart = tasksStart - tasksPerPagesCount;

            setIsPrev(true);
            setTasksStart(newStart);
            if (tasksEnd - newStart > maxTasksPerPagesCount)
                setTasksEnd(tasksEnd - tasksPerPagesCount * (tasksEnd - newStart) / maxTasksPerPagesCount);
            return true;
        }
        return false;
    }

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

    return (
        <div className={"room__content__body__tasks room-tasks"}>
            <div className="room-tasks__last-updates">
                {latestTasks.map((task) => {
                    return <RoomLastTask task={task} key={task.roomTaskID}/>
                })}
            </div>
            <div className="room-tasks__tasks">
                <InfiniteScroll onNext={onNextPage} onPrev={onPrevPage}>
                    {tasks.map((task) => {
                        return <RoomTask task={task} key={task.roomTaskID}/>
                    })}
                </InfiniteScroll>
            </div>
        </div>
    );
};

export default RoomTasks;