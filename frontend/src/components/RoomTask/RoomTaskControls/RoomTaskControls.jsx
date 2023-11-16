import React, {useState} from 'react';
import RoomTaskControlsSolution from "./RoomTaskControlsSolution/RoomTaskControlsSolution";
import CreateCommentForm from "../../UI/CreateCommentForm/CreateCommentForm";
import {maxMessagesPerPagesCount, messagesPerPagesCount} from "../../../constants";
import InfiniteScrollPaginator from "../../UI/InfiniteScrollPaginator/InfiniteScrollPaginator";
import RoomTaskMessage from "./RoomTaskMessage/RoomTaskMessage";

const RoomTaskControls = ({ roomID, roomTaskID }) => {
    const [messages, setMessages] = useState([]);

    async function onSend(text) {
        await fetch("/room-customer-message/add-member", {
            method: "post",
            headers: {"Content-Type": "application/json", "Accept": "application/json"},
            body: JSON.stringify({
                "roomID": roomID,
                "roomTaskID": roomTaskID,
                "text": text
            })
        });
    }

    return (
        <div className={"room-task__controls"}>
            <RoomTaskControlsSolution roomID={roomID} roomTaskID={roomTaskID}/>
            <div className={"room-task__controls__solution-messages"}>
                <CreateCommentForm placeholder={"Оставьте сообщение"} onSendCallback={onSend}/>
                <InfiniteScrollPaginator param={roomTaskID} paramName={"roomTaskID"} data={messages}
                    updateData={setMessages} countByPage={messagesPerPagesCount} endpoint={"/room-customer-message/get-page"}
                    maxCountByPage={maxMessagesPerPagesCount}>
                    {messages.map((message) => {
                        return <RoomTaskMessage message={message} key={message.roomCustomerMessageID}/>
                    })}
                </InfiniteScrollPaginator>
            </div>
        </div>
    );
};

export default RoomTaskControls;