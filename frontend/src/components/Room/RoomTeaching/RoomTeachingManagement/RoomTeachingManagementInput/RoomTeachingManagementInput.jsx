import React, {useRef} from 'react';

const RoomTeachingManagementInput = ({ header, onChange, value }) => {
    const input = useRef();

    function onChangeCallback() {
        onChange(input.current.value);
    }

    return (
        <div className={"room-teaching-management-input"}>
            <p className={"room-teaching-management-input__header"}> { header } </p>
            <input className={"room-teaching-management-input__input"} onChange={onChangeCallback} value={value} ref={input}/>
        </div>
    );
};

export default RoomTeachingManagementInput;