import React, {useEffect, useState} from 'react';
import RoomLabel from "../RoomLabel/RoomLabel";
import {useNavigate} from "react-router-dom";
import JoinRoomForm from "../JoinRoomForm/JoinRoomForm";

const RoomsList = ({ onMount, isJoining, showLoading, hideLoading }) => {
    const [rooms, setRooms] = useState([]);
    const navigate = useNavigate();

    useEffect(() => {
        async function getRooms() {
            showLoading();
            let response = await fetch("/rooms/get/rooms", {
                'method': 'get',
                'headers': {'Accept': 'application/json'}
            });
            setRooms(await response.json());
            hideLoading();
        }

        onMount();
        void getRooms();
    }, []);

    function onClick(roomID) {
        navigate(`/room/${roomID}`);
    }

    return (
        <div className={"rooms-list main__wrapper__content__rooms-list"}>
            {isJoining ? <JoinRoomForm/> : null}
            {rooms.map((room) => {
                return <RoomLabel room={{...room, 'audience': 5}} onClick={onClick} key={room.roomID}/>
            })}
        </div>
    );
};

export default RoomsList;