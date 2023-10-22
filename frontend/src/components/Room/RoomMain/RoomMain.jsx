import React from 'react';
import CreateCommentForm from "../../UI/CreateCommentForm/CreateCommentForm";

const RoomMain = () => {
    async function onSendPost(text) {

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