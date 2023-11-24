import React, {useEffect, useState} from 'react';
import RoomTeachingManagementInput from "./RoomTeachingManagementInput/RoomTeachingManagementInput";

const RoomTeachingManagement = ({ room }) => {
    const [name, setName] = useState("");

    useEffect(() => {
        if (room) {
            setName(room.name);
        }
    }, [room]);

    return (
        <div className={"room__teaching__body__management"}>
            <div className={"room__teaching__body__management__main"}>
                <p className={"room__teaching__body__management__main__header"}> Общие сведения </p>
                <RoomTeachingManagementInput value={name} onChange={setName} header={"Название комнаты"}/>
            </div>
        </div>
    );
};

export default RoomTeachingManagement;