import React, {useEffect, useRef, useState} from 'react';
import RoomTeachingManagementInput from "./RoomTeachingManagementInput/RoomTeachingManagementInput";
import CancelButton from "../../../UI/CancelButton/CancelButton";
import AddRoomTeacherDialog from "./AddRoomTeacherDialog/AddRoomTeacherDialog";

const RoomTeachingManagement = ({ room }) => {
    const [name, setName] = useState("");
    const [description, setDescription] = useState("");
    const [teachers, setTeachers] = useState([]);
    const [members, setMembers] = useState([]);
    const [addRoomTeacherDialogOpened, setAddRoomTeacherDialogOpened] = useState(false);
    const fileInput = useRef();

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

    async function saveRoom() {
        await fetch("/room/update", {
            method: "post",
            headers: {"Content-Type": "application/json", "Accept": "application/json"},
            body: JSON.stringify({
                "roomID": room.roomID,
                "name": name,
                "description": description
            })
        })
    }

    async function getRoomJoinToken() {
        let response = await fetch(`/room/get-room-token?roomID=${room.roomID}`, {
            method: "get",
            headers: {"Accept": "application/json"}
        });
        await navigator.clipboard.writeText(await response.text());
    }

    function setBackground() {
        fileInput.current.click();
    }

    async function onBackgroundChange() {
        let data = new FormData();
        data.append("roomID", room.roomID);
        data.append("file", fileInput.current.files[0]);
        let response = await fetch("/room/upload/background", {
            method: "post",
            body: data
        })
    }

    return (
        <div className={"room__teaching__body__management"}>
            {addRoomTeacherDialogOpened ? <AddRoomTeacherDialog members={members} onClose={switchAddRoomTeacherDialogOpened}
                onAdd={addTeacher}/> : null}
            <input type={"file"} accept="image/jpeg" ref={fileInput} onChange={onBackgroundChange}/>
            <div className={"room__teaching__body__management__main"}>
                <div className="room__teaching__body__management__main__header">
                    <p className={"room__teaching__body__management__main__header__header"}> Общие </p>
                    <div className="room__teaching__body__management__main__header__controls">
                        <img src={"/assets/images/icons/link.png"} alt={"Link"} onClick={getRoomJoinToken}/>
                        <img src={"/assets/images/icons/edit.png"} alt={"Edit background"} onClick={setBackground}/>
                    </div>
                </div>
                <RoomTeachingManagementInput value={name} onChange={setName} header={"Название комнаты"}/>
                <RoomTeachingManagementInput value={description} onChange={setDescription} header={"Описание"}/>
                <button className={"room__teaching__body__management__main__button"} onClick={saveRoom}>
                    Сохранить
                </button>
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