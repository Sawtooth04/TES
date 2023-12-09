import React from 'react';
import {Navigate, NavLink, Route, Routes} from "react-router-dom";
import RoomTeachingUnverified from "./RoomTeachingUnverified/RoomTeachingUnverified";
import RoomTeachingVerified from "./RoomTeachingVerified/RoomTeachingVerified";
import RoomTeachingUnverifiedSolution from "./RoomTeachingUnverifiedSolution/RoomTeachingUnverifiedSolution";
import RoomTeachingMessages from "./RoomTeachingMessages/RoomTeachingMessages";
import RoomTeachingChat from "./RoomTeachingChat/RoomTeachingChat";
import RoomTeachingVerifiedSolution from "./RoomTeachingVerifiedSolution/RoomTeachingVerifiedSolution";
import RoomTeachingManagement from "./RoomTeachingManagement/RoomTeachingManagement";

const RoomTeaching = ({ room }) => {
    return (
        <div className={"room__content__body__teaching room-teaching"}>
            <div className={"room-teaching__header"}>
                <NavLink className={"room-teaching__header__link"} to={"messages"}>
                    Сообщения
                </NavLink>
                <NavLink className={"room-teaching__header__link"} to={"unverified"}>
                    Непроверенные
                </NavLink>
                <NavLink className={"room-teaching__header__link"} to={"verified"}>
                    Проверенные
                </NavLink>
                <NavLink className={"room-teaching__header__link"} to={"management"}>
                    Управление
                </NavLink>
            </div>
            <div className="room-teaching__body">
                <Routes>
                    <Route path={"chat/:roomTaskID/:roomCustomerID"} element={<RoomTeachingChat />}/>
                    <Route path={"messages"} element={<RoomTeachingMessages/>}/>
                    <Route path={"unverified"} element={<RoomTeachingUnverified />}/>
                    <Route path={"unverified/solution/:solutionID"} element={<RoomTeachingUnverifiedSolution/>}/>
                    <Route path={"verified"} element={<RoomTeachingVerified/>}/>
                    <Route path={"verified/solution/:solutionID"} element={<RoomTeachingVerifiedSolution/>}/>
                    <Route path={"management"} element={<RoomTeachingManagement room={room}/>}/>
                    <Route path={"*"} element={<Navigate to={"messages"}/>}/>
                </Routes>
            </div>
        </div>
    );
};

export default RoomTeaching;