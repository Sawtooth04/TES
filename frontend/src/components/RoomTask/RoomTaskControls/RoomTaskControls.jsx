import React from 'react';
import RoomTaskControlsSolution from "./RoomTaskControlsSolution/RoomTaskControlsSolution";
import CreateCommentForm from "../../UI/CreateCommentForm/CreateCommentForm";

const RoomTaskControls = ({ roomID, roomTaskID }) => {
    async function onSend(text) {
        await fetch("/room-customer-message/add-member", {
            method: "post",
            headers: {"Content-Type": "application/json", "Accept": "application/json"},
            body: JSON.stringify({
                "roomID": roomID,
                "roomTaskID": roomTaskID,
                "text": text
            })
        });
    }

    return (
        <div className={"room-task__controls"}>
            <RoomTaskControlsSolution roomID={roomID} roomTaskID={roomTaskID}/>
            <div className={"room-task__controls__solution-comments"}>
                <CreateCommentForm placeholder={"Оставьте комментарий"} onSendCallback={onSend}/>
            </div>
        </div>
    );
};

export default RoomTaskControls;