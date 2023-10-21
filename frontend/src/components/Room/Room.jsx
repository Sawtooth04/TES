import React, {useEffect, useState} from 'react';
import {useParams} from "react-router-dom";

const Room = ({ onMount }) => {
    const [room, setRoom] = useState(null);
    const { roomID } = useParams();

    useEffect(() => {
        async function getRoom() {
            let params = new URLSearchParams({roomID: roomID}),
                response = await fetch(`/room/get-room?${params.toString()}`, {
                    method: 'get',
                    headers: { 'Content-Type': 'application/json', 'Accept': 'application/json' }
                })
            if (response.ok)
                setRoom(await response.json());
        }

        onMount();
        void getRoom();
    }, []);

    return (
        <div className={"room"}>
            <div className="room__header">

            </div>
            <div className="room__content">
                <div className="room__content__header">

                </div>
                <div className="room__content__body">

                </div>
            </div>
        </div>
    );
};

export default Room;