import React, {useEffect, useState} from 'react';
import {useNavigate} from "react-router-dom";
import RoomLabel from "../RoomLabel/RoomLabel";

const StudyingRoomsList = ({ onMount, showLoading, hideLoading }) => {
    const [rooms, setRooms] = useState([]);
    const navigate = useNavigate();

    useEffect(() => {
        async function getRooms() {
            showLoading();
            let response = await fetch("/rooms/get/studying-rooms", {
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
        <div className={"studying-rooms-list main__wrapper__content__studying-rooms-list"}>
            {rooms.map((room) => {
                return <RoomLabel room={{...room, 'audience': 5}} onClick={onClick} key={room.roomID}/>
            })}
        </div>
    );
};

export default StudyingRoomsList;