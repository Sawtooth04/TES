import React, {useEffect, useState} from 'react';
import {useParams} from "react-router-dom";

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
                <div className="room-task__content__comments">
                    <div className="room-task__content__comments__header">

                    </div>
                    <div className="room-task__content__comments_comments">

                    </div>
                </div>
            </div>
            <div className={"room-task__controls"}>

            </div>
        </div>
    );
};

export default RoomTask;