import React, {useEffect, useState} from 'react';
import {Navigate, NavLink, Route, Routes, useParams} from "react-router-dom";
import RoomMain from "./RoomMain/RoomMain";
import RoomTasks from "./RoomTasks/RoomTasks";
import RoomMembers from "./RoomMembers/RoomMembers";
import RoomDescription from "./RoomDescription/RoomDescription";
import RoomTeaching from "./RoomTeaching/RoomTeaching";

const Room = ({ onMount }) => {
    const [room, setRoom] = useState(null);
    const [roomOwner, setRoomOwner] = useState(null);
    const [role, setRole] = useState(null);
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

        async function getRoomOwner() {
            let params = new URLSearchParams({roomID: roomID}),
                response = await fetch(`/room/get-room-owner?${params.toString()}`, {
                    method: 'get',
                    headers: { 'Content-Type': 'application/json', 'Accept': 'application/json' }
                })
            if (response.ok)
                setRoomOwner(await response.json());
        }

        async function getRole() {
            let response = await fetch(`/room-customer-role/get?roomID=${roomID}`, {
                method: "get",
                headers: {"Accept": "application/json"}
            });

            if (response.ok)
                setRole(await response.text());
        }

        onMount();
        void getRoom();
        void getRoomOwner();
        void getRole();
    }, []);

    return (
        <div className={"room"}>
            {(room != null && roomOwner != null) ?
                <div className="room__header">
                    <p className={"room__header__name"}> {room.name} </p>
                    <div className={"room__header__owner"}>
                        <p className={"room__header__owner__article"}> Организатор: </p>
                        <p className={"room__header__owner__name"}> {roomOwner.name} </p>
                    </div>
                </div> : null
            }
            <div className="room__content">
                <div className="room__content__header content-header">
                    <NavLink className={"content-header__link"} to={"main"}>
                        Главная
                    </NavLink>
                    <NavLink className={"content-header__link"} to={"tasks"}>
                        Задания
                    </NavLink>
                    <NavLink className={"content-header__link"} to={"members"}>
                        Участники
                    </NavLink>
                    <NavLink className={"content-header__link"} to={"description"}>
                        Описание
                    </NavLink>
                    { (role === "teacher") ?
                        <NavLink className={"content-header__link"} to={"teaching"}>
                            Преподавание
                        </NavLink> : null
                    }
                </div>
                <div className="room__content__body">
                    <Routes>
                        <Route path={"description"} element={<RoomDescription room={room}/>}/>
                        <Route path={"members"} element={<RoomMembers/>}/>
                        <Route path={"tasks"} element={<RoomTasks role={role}/>}/>
                        <Route path={"main"} element={<RoomMain/>}/>
                        <Route path={"teaching/*"} element={<RoomTeaching room={room}/>}/>
                        <Route path={"*"} element={<Navigate to={"main"}/>}/>
                    </Routes>
                </div>
            </div>
        </div>
    );
};

export default Room;