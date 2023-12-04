import React from 'react';
import {useParams} from "react-router-dom";

const RegistrationVerification = () => {
    const { name } = useParams();

    async function sendNewMessage() {
        await fetch(`/customer/verification/send-new-message?name=${name}`, { method: "get" });
    }

    return (
        <div className={"registration-verification"}>
            <p className={"registration-verification__text"}>
                Регистрация успешно завершена. На вашу почту было отправлено письмо для подтверждения регистрации.
            </p>
            <p className={"registration-verification__new-link-button"} onClick={sendNewMessage}>
                Нажмите, если вы не получили письмо.
            </p>
        </div>
    );
};

export default RegistrationVerification;