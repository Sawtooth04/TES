import React, {useEffect, useRef, useState} from 'react';
import ContextMenuButton from "../../../UI/ContextMenuButton/ContextMenuButton";
import ContextMenu from "../../../UI/ContextMenu/ContextMenu";

const RoomTaskComment = ({ comment, onDelete }) => {
    const [contextMenuOpened, setContextMenuOpened] = useState(false);

    function switchContextMenuOpened() {
        setContextMenuOpened(!contextMenuOpened);
    }

    async function deleteComment() {
        let response = await fetch("/task-comment/delete", {
            method: "post",
            headers: { "Content-Type": "application/json" },
            body: JSON.stringify(comment)
        })

        if (response.ok) {
            setContextMenuOpened(false);
            onDelete(comment);
        }
    }

    return (
        <div className={"room-task-comments__comments__comment room-task-comment"}>
            { (contextMenuOpened) ? <ContextMenu>
                <button onClick={deleteComment}> Удалить </button>
                <button onClick={switchContextMenuOpened}> Закрыть </button>
            </ContextMenu> : null }
            <div className="room-task-comment__header">
                <p className={"room-task-comment__header__customer-name"}> {comment.customerName} </p>
                <p className={"room-task-comment__header__posted"}> {new Date(comment.posted).toLocaleString("ru")} </p>
                <ContextMenuButton onClick={switchContextMenuOpened}/>
            </div>
            <p className={"room-task-comment__comment"}> {comment.comment} </p>
        </div>
    );
};

export default RoomTaskComment;