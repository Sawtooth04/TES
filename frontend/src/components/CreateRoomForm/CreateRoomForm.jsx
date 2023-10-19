import React, {useRef} from 'react';

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
        <div className={"add-room-form"}>
            <div className={"add-room-form__form"}>
                <div className={"add-room-form__form__header add-room-form-header"}>
                    <p className={"add-room-form-header__name"}> Создать комнату </p>
                </div>
                <input className={"add-room-form__form__input"} placeholder={"Название комнаты"} maxLength={30} ref={nameInput}/>
                <input className={"add-room-form__form__input"} placeholder={"Предмет"} maxLength={30} ref={subjectInput}/>
                <button className={"add-room-form__form__submit-button"} onClick={create}> Создать </button>
            </div>
        </div>
    );
};

export default CreateRoomForm;