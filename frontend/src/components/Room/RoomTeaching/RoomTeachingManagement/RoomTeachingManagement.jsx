import React, {useEffect, useState} from 'react';
import RoomTeachingManagementInput from "./RoomTeachingManagementInput/RoomTeachingManagementInput";
import CancelButton from "../../../UI/CancelButton/CancelButton";
import AddRoomTeacherDialog from "./AddRoomTeacherDialog/AddRoomTeacherDialog";

const RoomTeachingManagement = ({ room }) => {
    const [name, setName] = useState("");
    const [description, setDescription] = useState("");
    const [teachers, setTeachers] = useState([]);
    const [members, setMembers] = useState([]);
    const [addRoomTeacherDialogOpened, setAddRoomTeacherDialogOpened] = useState(false);

    useEffect(() => {
        if (room) {
            setName(room.name);
            setDescription(room.description);
            void getTeachers();
            void getMembers();
        }
    }, [room]);

    async function getTeachers() {
        let response = await fetch(`/room-customer-role/get/teachers?roomID=${room.roomID}`, {
            method: "get",
            headers: {
                "Accept": "application/json"
            }
        });
        setTeachers(await response.json());
    }

    async function getMembers() {
        let response = await fetch(`/room-customer-role/get/members?roomID=${room.roomID}`, {
            method: "get",
            headers: {
                "Accept": "application/json"
            }
        });
        setMembers(await response.json());
    }

    async function deleteTeacher(teacher) {
        let params = new URLSearchParams();
        params.set("roomID", room.roomID);
        params.set("customerID", teacher.customerID)
        await fetch(`/room-customer-role/set/member?${params.toString()}`, {
            method: "get"
        });
        await getTeachers();
    }

    async function addTeacher(member) {
        let params = new URLSearchParams();
        params.set("roomID", room.roomID);
        params.set("customerID", member.customerID)
        await fetch(`/room-customer-role/set/teacher?${params.toString()}`, {
            method: "get"
        });
        await getTeachers();
        await getMembers();
    }

    function switchAddRoomTeacherDialogOpened() {
        setAddRoomTeacherDialogOpened(!addRoomTeacherDialogOpened);
    }

    return (
        <div className={"room__teaching__body__management"}>
            {addRoomTeacherDialogOpened ? <AddRoomTeacherDialog members={members} onClose={switchAddRoomTeacherDialogOpened}
                onAdd={addTeacher}/> : null}
            <div className={"room__teaching__body__management__main"}>
                <p className={"room__teaching__body__management__main__header"}> Общие сведения </p>
                <RoomTeachingManagementInput value={name} onChange={setName} header={"Название комнаты"}/>
                <RoomTeachingManagementInput value={description} onChange={setDescription} header={"Описание"}/>
            </div>
            <div className={"room__teaching__body__management__teachers"}>
                <p className={"room__teaching__body__management__teachers__header"}> Преподаватели </p>
                <div className={"room__teaching__body__management__teachers__teachers"}>
                    {teachers.map((teacher) => {
                        if (room.ownerID !== teacher.customerID)
                            return <div className={"room__teaching__body__management__teachers__teachers__teacher"} key={teacher.customerID}>
                                <p className={"room__teaching__body__management__teachers__teacher__name"}> {teacher.name} </p>
                                <CancelButton onClick={async () => {await deleteTeacher(teacher)}}/>
                            </div>
                        else
                            return null;
                    })}
                </div>
                <button className={"room__teaching__body__management__teachers__button"} onClick={switchAddRoomTeacherDialogOpened}>
                    Добавить преподавателя
                </button>
            </div>
        </div>
    );
};

export default RoomTeachingManagement;