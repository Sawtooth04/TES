import React, {useEffect, useState} from 'react';
import {useNavigate} from "react-router-dom";
import JoinRoomForm from "../JoinRoomForm/JoinRoomForm";
import RoomLabel from "../RoomLabel/RoomLabel";

const OwnRoomsList = ({ onMount }) => {
    const [rooms, setRooms] = useState([]);
    const navigate = useNavigate();

    useEffect(() => {
        async function getRooms() {
            let response = await fetch("/rooms/get/own-rooms", {
                'method': 'get',
                'headers': {'Accept': 'application/json'}
            });
            setRooms(await response.json());
        }

        onMount();
        void getRooms();
    }, []);

    function onClick(roomID) {
        navigate(`/room/${roomID}`);
    }

    return (
        <div className={"own-rooms-list main__wrapper__content__own-rooms-list"}>
            {rooms.map((room) => {
                return <RoomLabel room={{...room, 'audience': 5}} onClick={onClick} key={room.roomID}/>
            })}
        </div>
    );
};

export default OwnRoomsList;