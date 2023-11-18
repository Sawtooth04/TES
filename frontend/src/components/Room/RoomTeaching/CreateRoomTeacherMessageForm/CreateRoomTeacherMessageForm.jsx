import React, {useRef} from 'react';
import PopUpForm from "../../../UI/PopUpForm/PopUpForm";

const CreateRoomTeacherMessageForm = ({ roomSolutionID, onAddClick }) => {
    const textInput = useRef(null);

    async function onAdd() {
        await fetch("/room-customer-message/add-teacher-by-room-solution", {
            method: "post",
            headers: {"Content-Type": "application/json", "Accept": "application/json"},
            body: JSON.stringify({
                "roomSolutionID": roomSolutionID,
                "text": textInput.current.value
            })
        });
        onAddClick();
    }

    return (
        <PopUpForm header={"Отправить сообщение"}>
            <input placeholder={"Введите сообщение"} maxLength={30} ref={textInput}/>
            <button onClick={onAdd}> Отправить </button>
        </PopUpForm>
    );
};

export default CreateRoomTeacherMessageForm;