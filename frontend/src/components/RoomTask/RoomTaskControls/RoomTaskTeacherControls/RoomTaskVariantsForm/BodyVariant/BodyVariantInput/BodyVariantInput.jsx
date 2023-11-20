import React, {useRef} from 'react';

const BodyVariantInput = ({ name, value, setValue }) => {
    const inputRef = useRef();

    function onChange() {
        setValue(inputRef.current.value);
    }

    return (
        <div className={"room-task-variants-form__body__variant__input"}>
            <p className={"room-task-variants-form__body__variant__input__name"}> {name} </p>
            <input className={"room-task-variants-form__body__variant__input__input"} onChange={onChange} ref={inputRef} value={value}/>
        </div>
    );
};

export default BodyVariantInput;