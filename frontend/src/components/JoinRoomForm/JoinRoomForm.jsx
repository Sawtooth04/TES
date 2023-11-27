import React, {useRef, useState} from 'react';
import PopUpForm from "../UI/PopUpForm/PopUpForm";
import {useNavigate} from "react-router-dom";

const JoinRoomForm = () => {
    const [hasError, setHasError] = useState(false);
    const tokenInput = useRef();
    const navigate = useNavigate();

    async function join() {
        let response = await fetch(`/room/join?token=${tokenInput.current.value}`, {
            method: 'get',
            headers: {'Accept': 'application/json'},
        });
        if (response.ok && (await response.json()))
            navigate("/");
        else
            setHasError(true);
    }

    function onClose() {
        navigate(-1);
    }

    return (
        <PopUpForm header={"Присоединиться к комнате"}>
            <input placeholder={"Код"} ref={tokenInput}/>
            {hasError ?
                <p className={"pop-up-form__form__message"}>
                    Введен недействительный код. Возможно, истёк срок его действия.
                </p> : null
            }
            <div className={"pop-up-form__form__wrapper"}>
                <button className={"pop-up-form__form__wrapper__button"} onClick={join}> Присоединиться </button>
                <button className={"pop-up-form__form__wrapper__button"} onClick={onClose}> Закрыть </button>
            </div>

        </PopUpForm>
    );
};

export default JoinRoomForm;