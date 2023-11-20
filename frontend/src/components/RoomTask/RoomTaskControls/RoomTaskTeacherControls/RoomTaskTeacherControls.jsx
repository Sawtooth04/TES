import React, {useState} from 'react';
import RoomTaskVariantsForm from "./RoomTaskVariantsForm/RoomTaskVariantsForm";

const RoomTaskTeacherControls = ({ roomID, roomTaskID}) => {
    const [taskVariantsFormOpened, setTaskVariantsFormOpened] = useState(false);

    function switchTaskVariantsFormState() {
        setTaskVariantsFormOpened(!taskVariantsFormOpened);
    }

    return (
        <div className={"room-task__controls__teacher-control"}>
            {taskVariantsFormOpened ? <RoomTaskVariantsForm onClose={switchTaskVariantsFormState} roomID={roomID}
                roomTaskID={roomTaskID}/> : null}
            <button className={"room-task__controls__teacher-control__button"} onClick={switchTaskVariantsFormState}>
                Варианты
            </button>
        </div>
    );
};

export default RoomTaskTeacherControls;