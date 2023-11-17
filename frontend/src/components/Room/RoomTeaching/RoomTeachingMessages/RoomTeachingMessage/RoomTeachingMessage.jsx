import React from 'react';
import {useNavigate, useParams} from "react-router-dom";

const RoomTeachingMessage = ({ message }) => {
    const { roomID } = useParams();
    const navigate = useNavigate();

    function onClick() {
        navigate(`/room/${roomID}/teaching/chat/${message.roomTaskID}/${message.roomCustomerID}`);
    }

    return (
        <div className={`room__teaching__body__messages__message${message.isRead ? "" : " message_unread"}`} onClick={onClick}>
            <p className={"room__teaching__body__messages__message__name"}> {message.name} </p>
        </div>
    );
};

export default RoomTeachingMessage;