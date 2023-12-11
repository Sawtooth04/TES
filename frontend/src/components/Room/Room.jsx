import React, {useEffect, useState} from 'react';
import {Navigate, NavLink, Route, Routes, useParams} from "react-router-dom";
import RoomMain from "./RoomMain/RoomMain";
import RoomTasks from "./RoomTasks/RoomTasks";
import RoomMembers from "./RoomMembers/RoomMembers";
import RoomDescription from "./RoomDescription/RoomDescription";
import RoomTeaching from "./RoomTeaching/RoomTeaching";

const Room = ({ onMount, showLoading, hideLoading }) => {
    const [room, setRoom] = useState(null);
    const [roomOwner, setRoomOwner] = useState(null);
    const [role, setRole] = useState(null);
    const [background, setBackground] = useState(null);
    const [color, setColor] = useState(null);
    const [headerColor, setHeaderColor] = useState(null);
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

        async function getBackground() {
            let response = await fetch(`/room/get/background?roomID=${roomID}`, {
                method: "get",
                headers: {"Accept": "image/jpeg"}
            });

            if (response.ok)
                setBackground(await response.blob());
        }

        async function getColor() {
            let response = await fetch(`/room/get/color?roomID=${roomID}`, {
                method: "get",
            });
            let newColor, parts;

            if (response.ok) {
                newColor = await response.text();
                parts = newColor.split(", ");
                setHeaderColor(`${255 - Number(parts[0])}, ${255 - Number(parts[1])}, ${255 - Number(parts[2])}`);
                setColor(newColor);
            }
        }

        showLoading();
        onMount();
        void getRoom();
        void getRoomOwner();
        void getRole();
        void getBackground();
        void getColor();
    }, []);

    useEffect(() => {
        if (room && roomOwner && role)
            hideLoading();
    }, [room, roomOwner, role]);

    return (
        <div className={"room"} style = {{"--color": (color) ? color : "0, 37, 86", "--headerColor": (headerColor) ? headerColor : "236,246,255"}}>
            {(room != null && roomOwner != null) ?
                <div className="room__header" style={(background) ? {backgroundImage: `url(${URL.createObjectURL(background)})`} : null}>
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
                        <Route path={"tasks"} element={<RoomTasks role={role} showLoading={showLoading} hideLoading={hideLoading}/>}/>
                        <Route path={"main"} element={<RoomMain/>} />
                        <Route path={"teaching/*"} element={<RoomTeaching room={room}/>}/>
                        <Route path={"*"} element={<Navigate to={"main"}/>}/>
                    </Routes>
                </div>
            </div>
        </div>
    );
};

export default Room;