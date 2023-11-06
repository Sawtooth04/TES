import React, {useEffect, useRef, useState} from 'react';
import {useParams} from "react-router-dom";
import RoomMember from "./RoomMember/RoomMember";

const RoomMembers = () => {
    const { roomID } = useParams();
    const [teachers, setTeachers] = useState([]);
    const [members, setMembers] = useState([]);

    useEffect(() => {
        async function getTeachers() {
            let response = await fetch(`/room-customer-role/get/teachers?roomID=${roomID}`, {
                method: "get",
                headers: {
                    "Accept": "application/json"
                }
            });
            setTeachers(await response.json());
        }

        async function getMembers() {
            let response = await fetch(`/room-customer-role/get/members?roomID=${roomID}`, {
                method: "get",
                headers: {
                    "Accept": "application/json"
                }
            });
            setMembers(await response.json());
        }

        void getTeachers();
        void getMembers();
    }, []);

    return (
        <div className={"room__content__body__members room-members"}>
            {(teachers.length > 0) ?
                <div className="room-members__teachers room-members__members">
                    <p className="room-members__teachers__header"> Преподаватели </p>
                    <div className="room-members__members__list">
                        {teachers.map((teacher) => {
                            return <RoomMember member={teacher} key={teacher.customerID}/>
                        })}
                    </div>
                </div> : null
            }
            {(members.length > 0) ?
                <div className="room-members__members">
                    <p className="room-members__members__header"> Другие учащиеся </p>
                    <div className="room-members__members__list">
                        {members.map((member) => {
                            return <RoomMember member={member} key={member.customerID}/>
                        })}
                    </div>
                </div> : null
            }
        </div>
    );
};

export default RoomMembers;