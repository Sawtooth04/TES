import React from 'react';
import CreateCommentForm from "../../UI/CreateCommentForm/CreateCommentForm";
import {useParams} from "react-router-dom";

const RoomMain = () => {
    const { roomID } = useParams();

    async function onSendPost(text) {
        await fetch("/room-post/add", {
            method: 'post',
            headers: {
                'Content-Type': 'application/json',
                'Accept': 'application/json'
            },
            body: JSON.stringify({
                roomID: roomID,
                text: text
            })
        });
    }

    return (
        <div className={"room__content__body__main room-main"}>
            <div className="room-main__last-updates">

            </div>
            <div className="room-main__posts">
                <CreateCommentForm onSendCallback={onSendPost}/>
            </div>
        </div>
    );
};

export default RoomMain;