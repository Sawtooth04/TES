import React, {useEffect, useState} from 'react';
import RoomTaskVariantsForm from "./RoomTaskVariantsForm/RoomTaskVariantsForm";

const RoomTaskTeacherControls = ({ roomID, roomTaskID}) => {
    const [taskVariantsFormOpened, setTaskVariantsFormOpened] = useState(false);
    const [taskStatistic, setTaskStatistic] = useState(null);

    useEffect(() => {
        async function getTaskStatistic() {
            let params = new URLSearchParams();
            params.set("roomID", roomID);
            params.set("roomTaskID", roomTaskID);
            let response = await fetch(`/task/get-statistic?${params.toString()}`, {
                method: "get",
                headers: {"Accept": "application/json"}
            });

            if (response.ok)
                setTaskStatistic(await response.json());
        }

        void getTaskStatistic();
    }, []);

    function switchTaskVariantsFormState() {
        setTaskVariantsFormOpened(!taskVariantsFormOpened);
    }

    return (
        <div className={"room-task__controls__teacher-control"}>
            {taskVariantsFormOpened ? <RoomTaskVariantsForm onClose={switchTaskVariantsFormState} roomID={roomID}
                roomTaskID={roomTaskID}/> : null}
            <div className={"room-task__controls__teacher-control__statistic"}>
                <div className={"room-task__controls__teacher-control__statistic__item"}>
                    <p className={"room-task__controls__teacher-control__statistic__item__paragraph"}> Всего решений: </p>
                    <p className={"room-task__controls__teacher-control__statistic__item__paragraph"}> {taskStatistic?.totalCount} </p>
                </div>
                <div className={"room-task__controls__teacher-control__statistic__item"}>
                    <p className={"room-task__controls__teacher-control__statistic__item__paragraph"}> Протестировано: </p>
                    <p className={"room-task__controls__teacher-control__statistic__item__paragraph"}> {taskStatistic?.testedCount} </p>
                </div>
                <div className={"room-task__controls__teacher-control__statistic__item"}>
                    <p className={"room-task__controls__teacher-control__statistic__item__paragraph"}> Принято: </p>
                    <p className={"room-task__controls__teacher-control__statistic__item__paragraph"}> {taskStatistic?.acceptedCount} </p>
                </div>
            </div>
            <button className={"room-task__controls__teacher-control__button"} onClick={switchTaskVariantsFormState}>
                Варианты
            </button>
        </div>
    );
};

export default RoomTaskTeacherControls;