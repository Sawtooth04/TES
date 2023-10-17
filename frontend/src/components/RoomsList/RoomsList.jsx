import React, {useEffect, useState} from 'react';
import RoomLabel from "../RoomLabel/RoomLabel";

const RoomsList = () => {
    const [rooms, setRooms] = useState([]);

    useEffect(() => {
        async function getRooms() {
            let response = await fetch("/rooms/get-rooms", {
                'method': 'get',
                'headers': {'Accept': 'application/json'}
            });
            setRooms(await response.json());
        }

        void getRooms();
    }, []);

    return (
        <div className={"rooms-list main__wrapper__content__rooms-list"}>
            {rooms.map((room) => {
                return <RoomLabel room={{...room, 'audience': 5}}/>
            })}
        </div>
    );
};

export default RoomsList;