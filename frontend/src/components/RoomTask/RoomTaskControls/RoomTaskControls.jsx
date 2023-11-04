import React from 'react';
import RoomTaskControlsSolution from "./RoomTaskControlsSolution/RoomTaskControlsSolution";

const RoomTaskControls = ({ roomID, roomTaskID }) => {
    return (
        <div className={"room-task__controls"}>
            <RoomTaskControlsSolution roomID={roomID} roomTaskID={roomTaskID}/>
            <div className={"room-task__controls__solution-comments"}>

            </div>
        </div>
    );
};

export default RoomTaskControls;