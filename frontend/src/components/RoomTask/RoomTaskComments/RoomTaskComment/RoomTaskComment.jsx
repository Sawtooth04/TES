import React from 'react';
import ContextMenuButton from "../../../UI/ContextMenuButton/ContextMenuButton";

const RoomTaskComment = ({ comment }) => {
    return (
        <div className={"room-task-comments__comments__comment room-task-comment"}>
            <div className="room-task-comment__header">
                <p className={"room-task-comment__header__customer-name"}> {comment.customerName} </p>
                <p className={"room-task-comment__header__posted"}> {new Date(comment.posted).toLocaleString("ru")} </p>
                <ContextMenuButton/>
            </div>
            <p className={"room-task-comment__comment"}> {comment.comment} </p>
        </div>
    );
};

export default RoomTaskComment;