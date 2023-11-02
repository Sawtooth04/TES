import React, {useState} from 'react';
import {taskCommentsPerPagesCount, maxTaskCommentsPerPagesCount} from "../../../constants";
import InfiniteScrollPaginator from "../../UI/InfiniteScrollPaginator/InfiniteScrollPaginator";
import RoomTaskComment from "./RoomTaskComment/RoomTaskComment";
import CreateRoomTaskCommentForm from "../../UI/CreateRoomTaskCommentForm/CreateRoomTaskCommentForm";

const RoomTaskComments = ({ roomTaskID }) => {
    const [comments, setComments] = useState([]);
    const [createTaskCommentDialogOpened, setCreateTaskCommentDialogOpened] = useState(false);

    function switchCreateTaskCommentDialogState() {
        setCreateTaskCommentDialogOpened(!createTaskCommentDialogOpened);
    }

    return (
        <div className={"room-task__content__comments room-task-comments"}>
            {createTaskCommentDialogOpened ? <CreateRoomTaskCommentForm roomTaskID={roomTaskID}
                onCreate={switchCreateTaskCommentDialogState}/> : null}
            <div className={"room-task-comments__header"}>
                <button className={"room-task-comments__header__add-comment-button"} onClick={switchCreateTaskCommentDialogState}>
                    <img src={"/assets/images/icons/add-task-comment.png"} alt={"Add comment"}/>
                    <p> Добавить комментарий </p>
                </button>
                <p className={"room-task-comments__header__comments"}> Комментарии </p>
            </div>
            <div className="room-task-comments__comments">
                <InfiniteScrollPaginator param={roomTaskID} paramName={"roomTaskID"} data={comments}
                    updateData={setComments} countByPage={taskCommentsPerPagesCount} endpoint={"/task-comment/get-page"}
                    maxCountByPage={maxTaskCommentsPerPagesCount}>
                    {comments.map((comment) => {
                        return <RoomTaskComment comment={comment} key={comment.roomTaskCommentID}/>
                    })}
                </InfiniteScrollPaginator>
            </div>
        </div>
    );
};

export default RoomTaskComments;