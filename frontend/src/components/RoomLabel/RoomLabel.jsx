import React from 'react';
import NearTask from "../NearTask/NearTask";

const RoomLabel = ({ room, onClick }) => {

    return (
        <div className={"room-label rooms-list__room-label"} onClick={() => {onClick(room.roomID)}}>
            <div className="room-label__header">
                <p className={"room-label__header__name"}> {room.name} </p>
            </div>
            <div className="room-label__meta">
                <p className={"room-label__meta__audience"}> Учащихся: {room.audience} </p>
            </div>
            <div className="room-label__nearest-tasks">
                <NearTask task={{'name': 'Тестовое задание'}}/>
                <NearTask task={{'name': 'Тестовое задание'}}/>
                <NearTask task={{'name': 'Тестовое задание'}}/>
            </div>
        </div>
    );
};

export default RoomLabel;