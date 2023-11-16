import React, {useEffect, useState} from 'react';
import RoomTeachingMessage from "./RoomTeachingMessage/RoomTeachingMessage";

const RoomTeachingMessages = ({ roomID }) => {
    const [messagesMeta, setMessagesMeta] = useState([]);

    useEffect(() => {
        async function getMessagesMeta() {
            let response = await fetch(`/room-customer-message/get-messages-meta?roomID=${roomID}`, {
                method: "get",
                headers: {"Accept": "application/json"}
            });

            setMessagesMeta(await response.json());
        }

        void getMessagesMeta();
    }, []);

    return (
        <div className={"room__teaching__body__messages"}>
            {messagesMeta.map((messageMeta) => {
                return <RoomTeachingMessage message={messageMeta} key={messageMeta.name}/>
            })}
        </div>
    );
};

export default RoomTeachingMessages;