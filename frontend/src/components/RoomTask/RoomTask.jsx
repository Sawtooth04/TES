import React, {useEffect, useState} from 'react';
import {useParams} from "react-router-dom";
import RoomTaskComments from "./RoomTaskComments/RoomTaskComments";
import RoomTaskControls from "./RoomTaskControls/RoomTaskControls";

const RoomTask = () => {
    const { roomID, roomTaskID } = useParams();
    const [task, setTask] = useState(null);
    const [taskVariant, setTaskVariant] = useState(null);
    const [role, setRole] = useState(null);

    useEffect(() => {
        async function getTask() {
            let params = new URLSearchParams();
            params.set("roomID", roomID);
            params.set("roomTaskID", roomTaskID);
            let response = await fetch(`/task/get?${params.toString()}`, {
                method: "get",
                headers: {"Accept": "application/json"}
            });

            if (response.ok)
                setTask(await response.json());
        }

        async function getTaskVariant() {
            let params = new URLSearchParams();
            params.set("roomID", roomID);
            params.set("roomTaskID", roomTaskID);
            let response = await fetch(`/task-variant/get?${params.toString()}`, {
                method: "get",
                headers: {"Accept": "application/json"}
            });

            if (response.ok)
                setTaskVariant(await response.json());
        }

        async function getRole() {
            let response = await fetch(`/room-customer-role/get?roomID=${roomID}`, {
                method: "get",
                headers: {"Accept": "application/json"}
            });

            if (response.ok)
                setRole(await response.text());
        }

        void getTask();
        void getTaskVariant();
        void getRole();
    }, []);

    return (
        <div className={"room-task"}>
            <div className={"room-task__content"}>
                <div className="room-task__content__header">
                    <img className={"room-task__content__header__logo"} src={"/assets/images/icons/task.png"} alt={"Logo"}/>
                    <p className={"room-task__content__header__name"}> { (task != null) ? task.name : null } </p>
                </div>
                <div className={"room-task__content__description"}>
                    <p className={"room-task__content__description__text"}> {task?.description} </p>
                </div>
                {(role !== "teacher") ?
                    <div className={"room-task__content__description"}>
                        <p className={"room-task__content__description__text"}> {taskVariant?.description} </p>
                    </div> : null
                }

                <RoomTaskComments roomTaskID = {roomTaskID}/>
            </div>
            <RoomTaskControls roomID={roomID} roomTaskID={roomTaskID} role={role}/>
        </div>
    );
};

export default RoomTask;