import React, {useEffect, useState} from 'react';
import {useParams} from "react-router-dom";
import RoomTaskComments from "./RoomTaskComments/RoomTaskComments";
import RoomTaskControls from "./RoomTaskControls/RoomTaskControls";

const RoomTask = () => {
    const { roomID, roomTaskID } = useParams();
    const [task, setTask] = useState(null);

    useEffect(() => {
        async function getTask() {
            let params = new URLSearchParams();
            params.set("roomID", roomID);
            params.set("roomTaskID", roomTaskID);
            let response = await fetch(`/task/get?${params.toString()}`, {
               method: "get",
               "Accept": "application/json"
            });

            if (response.ok)
                setTask(await response.json());
        }

        void getTask();
    }, []);

    return (
        <div className={"room-task"}>
            <div className={"room-task__content"}>
                <div className="room-task__content__header">
                    <img className={"room-task__content__header__logo"} src={"/assets/images/icons/task.png"} alt={"Logo"}/>
                    <p className={"room-task__content__header__name"}> { (task != null) ? task.name : null } </p>
                </div>
                <RoomTaskComments roomTaskID = {roomTaskID}/>
            </div>
            <RoomTaskControls />
        </div>
    );
};

export default RoomTask;