import React, {useRef} from 'react';
import PopUpForm from "../PopUpForm/PopUpForm";

const CreateRoomTaskCommentForm = ({ roomTaskID, onCreate }) => {
    const commentInput = useRef(null);

    async function create() {
        await fetch("/task-comment/add", {
            method: 'post',
            headers: {'Content-type': 'application/json'},
            body: JSON.stringify({
                roomTaskID: roomTaskID,
                comment: commentInput.current.value
            })
        });
        onCreate();
    }

    return (
        <PopUpForm header={"Добавить комментарий"}>
            <input placeholder={"Название"} maxLength={155} ref={commentInput}/>
            <button onClick={create}> Отправить </button>
        </PopUpForm>
    );
};

export default CreateRoomTaskCommentForm;