import React, {useState} from 'react';
import CreateCommentForm from "../../../UI/CreateCommentForm/CreateCommentForm";
import {maxMessagesPerPagesCount, messagesPerPagesCount,} from "../../../../constants";
import InfiniteScrollPaginator from "../../../UI/InfiniteScrollPaginator/InfiniteScrollPaginator";
import {useParams} from "react-router-dom";
import RoomTeachingChatMessage from "./RoomTeachingChatMessage/RoomTeachingChatMessage";

const RoomTeachingChat = () => {
    const { roomID, roomTaskID, roomCustomerID } = useParams();
    const [messages, setMessages] = useState([]);

    async function onAdd(text) {
        await fetch("/room-customer-message/add-teacher", {
            method: "post",
            headers: {"Content-Type": "application/json", "Accept": "application/json"},
            body: JSON.stringify({
                "roomID": roomID,
                "roomTaskID": roomTaskID,
                "text": text,
                "recipient": roomCustomerID
            })
        });
    }

    return (
        <div className={"room__teaching__body__chat"}>
            <div className="room__teaching__body__chat__messages">
                <InfiniteScrollPaginator params={{"roomCustomerID": roomCustomerID, "roomTaskID": roomTaskID}}
                    endpoint={'/room-customer-message/get-teacher-page'} countByPage={messagesPerPagesCount}
                    maxCountByPage={maxMessagesPerPagesCount} data={messages} updateData={setMessages}>
                    {messages.map((message) => {
                        return <RoomTeachingChatMessage message={message} key={message.roomCustomerMessageID}/>
                    })}
                </InfiniteScrollPaginator>
            </div>
            <div className="room__teaching__body__chat__controls">
                <CreateCommentForm onSendCallback={onAdd} placeholder={"Отправьте сообщение"}/>
            </div>
        </div>
    );
};

export default RoomTeachingChat;