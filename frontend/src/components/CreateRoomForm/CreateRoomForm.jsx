import React, {useRef} from 'react';
import PopUpForm from "../UI/PopUpForm/PopUpForm";

const CreateRoomForm = ({ onCreate }) => {
    const nameInput = useRef(null);
    const subjectInput = useRef(null);

    async function create() {
        let response = await fetch("/rooms/create", {
            method: 'post',
            headers: {'Content-type': 'application/json'},
            body: JSON.stringify({roomID: -1, name: nameInput.current.value})
        });
        onCreate();
    }

    return (
        <PopUpForm header={"Создать комнату"}>
            <input placeholder={"Название комнаты"} maxLength={30} ref={nameInput}/>
            <input placeholder={"Предмет"} maxLength={30} ref={subjectInput}/>
            <button onClick={create}> Создать </button>
        </PopUpForm>
    );
};

export default CreateRoomForm;