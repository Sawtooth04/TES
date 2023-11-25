import React from 'react';

const AddRoomTeacherDialog = ({ members, onAdd, onClose }) => {
    return (
        <div className={"add-room-teacher-dialog"}>
            <div className={"add-room-teacher-dialog__form"}>
                <div className={"add-room-teacher-dialog__form__members"}>
                    {members.map((member) => {
                        return <div className={"add-room-teacher-dialog__form__members__member"} key={member.customerID}>
                            <p className={"add-room-teacher-dialog__form__members__member__name"}> {member.name} </p>
                            <button className={"add-room-teacher-dialog__form__members__member__button"} onClick={() => {onAdd(member)}}>
                                Добавить
                            </button>
                        </div>
                    })}
                </div>
                <button className={"add-room-teacher-dialog__form__close"} onClick={onClose}>
                    Закрыть
                </button>
            </div>
        </div>
    );
};

export default AddRoomTeacherDialog;