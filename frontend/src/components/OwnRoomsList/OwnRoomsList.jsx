import React, {useEffect, useState} from 'react';
import {useNavigate} from "react-router-dom";
import RoomLabel from "../RoomLabel/RoomLabel";

const OwnRoomsList = ({ onMount, showLoading, hideLoading }) => {
    const [rooms, setRooms] = useState([]);
    const navigate = useNavigate();

    useEffect(() => {
        async function getRooms() {
            showLoading();
            let response = await fetch("/rooms/get/own-rooms", {
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
        <div className={"own-rooms-list main__wrapper__content__own-rooms-list"}>
            {rooms.map((room) => {
                return <RoomLabel room={{...room, 'audience': 5}} onClick={onClick} key={room.roomID}/>
            })}
        </div>
    );
};

export default OwnRoomsList;