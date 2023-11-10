import React from 'react';
import {Navigate, NavLink, Route, Routes} from "react-router-dom";
import RoomTeachingUnverified from "./RoomTeachingUnverified/RoomTeachingUnverified";
import RoomTeachingVerified from "./RoomTeachingVerified/RoomTeachingVerified";

const RoomTeaching = ({ roomID }) => {
    return (
        <div className={"room__content__body__teaching room-teaching"}>
            <div className={"room-teaching__header"}>
                <NavLink className={"room-teaching__header__link"} to={"unverified"}>
                    Непроверенные
                </NavLink>
                <NavLink className={"room-teaching__header__link"} to={"verified"}>
                    Проверенные
                </NavLink>
            </div>
            <div className="room-teaching__body">
                <Routes>
                    <Route path={"unverified"} element={<RoomTeachingUnverified roomID={roomID}/>}/>
                    <Route path={"verified"} element={<RoomTeachingVerified/>}/>
                    <Route path={"*"} element={<Navigate to={"unverified"}/>}/>
                </Routes>
            </div>
        </div>
    );
};

export default RoomTeaching;