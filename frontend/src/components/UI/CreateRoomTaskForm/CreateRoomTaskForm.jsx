import React, {useRef} from 'react';
import PopUpForm from "../PopUpForm/PopUpForm";
import {useParams} from "react-router-dom";

const CreateRoomTaskForm = ({ onCreate }) => {
    const { roomID } = useParams();
    const nameInput = useRef(null);
    const descriptionInput = useRef(null);
    const lastTermInput = useRef(null);

    async function create() {
        await fetch("/task/add", {
            method: 'post',
            headers: {'Content-type': 'application/json'},
            body: JSON.stringify({
                roomID: roomID,
                name: nameInput.current.value,
                description: descriptionInput.current.value,
                lastTerm: lastTermInput.current.value
            })
        });
        onCreate();
    }

    return (
        <PopUpForm header={"Добавить задание"}>
            <input placeholder={"Название"} maxLength={75} ref={nameInput}/>
            <input placeholder={"Описание"} maxLength={500} ref={descriptionInput}/>
            <input placeholder={"Срок сдачи"} type={"datetime-local"} ref={lastTermInput}/>
            <button onClick={create}> Добавить </button>
        </PopUpForm>
    );
};

export default CreateRoomTaskForm;