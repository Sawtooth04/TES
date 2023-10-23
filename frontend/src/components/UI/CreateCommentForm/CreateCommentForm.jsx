import React, {useRef} from 'react';

const CreateCommentForm = ({ onSendCallback }) => {
    const input = useRef(null);

    function onSend() {
        onSendCallback(input.current.value);
    }

    return (
        <div className={"create-comment-form"}>
            <input className={"create-comment-form__input"} placeholder={"Поделитесь с участниками курса..."} ref={input}/>
            <div className={"create-comment-form__button"} onClick={onSend}>
                <img src={"/assets/images/icons/send.png"} alt={"Send"}/>
            </div>
        </div>
    );
};

export default CreateCommentForm;