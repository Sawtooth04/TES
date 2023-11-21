import React, {useEffect, useState} from 'react';
import BodyVariantInput from "./BodyVariantInput/BodyVariantInput";
import BodyVariantTableInput from "./BodyVariantTableInput/BodyVariantTableInput";
import {useParams} from "react-router-dom";

const BodyVariant = ({ variant, saveVariant, deleteVariant }) => {
    const { roomID, roomTaskID } = useParams();
    const [description, setDescription] = useState(variant.description);
    const [body, setBody] = useState([]);
    const [currentTest, setCurrentTest] = useState(null);
    const [currentTestKey, setCurrentTestKey] = useState(null);
    const [currentTestInput, setCurrentTestInput] = useState([]);
    const [currentTestOutput, setCurrentTestOutput] = useState([]);

    useEffect(() => {
        if (!variant.isNew)
            void getVariantBody();
    }, []);

    useEffect(() => {
        setCurrentTestInput(currentTest?.input);
        setCurrentTestOutput(currentTest?.expected);
    }, [currentTest])

    useEffect(() => {
        setDescription(variant.description);
        if (!variant.isNew)
            void getVariantBody();
        else {
            setBody([]);
            setCurrentTest(null);
        }
    }, [variant]);

    async function getVariantBody() {
        let params = new URLSearchParams();
        params.set("roomID", roomID);
        params.set("roomTaskID", roomTaskID);
        params.set("variant", variant.variant);
        let response = await fetch(`/task-variant/get-body?${params.toString()}`, {
            method: "get",
            headers: { "Accept": "application/json" }
        })

        if (response.ok)
            setBody(await response.json());
    }

    function onTestAdd() {
        setBody([...body, {input: [], expected: []}]);
    }

    function onSave() {
        variant.description = description;
        body[currentTestKey] = {"input": currentTestInput, "expected": currentTestOutput};
        setBody([...body]);
        saveVariant(variant, body);
    }

    function onDelete() {
        deleteVariant(variant);
    }

    return (
        <div className={"room-task-variants-form__body__variant"}>
            <p className="room-task-variants-form__body__variant__name"> Вариант {variant.variant} </p>
            <div className={"room-task-variants-form__body__variant__controls"}>
                <button className={"room-task-variants-form__body__variant__controls__button"} onClick={onSave}>
                    Сохранить
                </button>
                <button className={"room-task-variants-form__body__variant__controls__button"} onClick={onDelete}>
                    Удалить
                </button>
            </div>
            <BodyVariantInput name={"Описание:"} value={description} setValue={setDescription}/>
            <div className="room-task-variants-form__body__variant__values">
                <div className="room-task-variants-form__body__variant__values__sidebar">
                    <img className="room-task-variants-form__body__variant__values__sidebar__header"
                         src={"/assets/images/icons/add-task.png"} alt={"Add"} onClick={onTestAdd}/>
                    {body.map((test, key) => {
                        if (currentTestKey === key)
                            return <p className={"room-task-variants-form__body__variant__values__sidebar__item item_current"} key={key}
                                onClick={() => {setCurrentTest(test); setCurrentTestKey(key);}}>
                                Тест {key + 1}
                            </p>
                        else
                            return <p className={"room-task-variants-form__body__variant__values__sidebar__item"} key={key}
                                      onClick={() => {setCurrentTest(test); setCurrentTestKey(key);}}>
                                Тест {key + 1}
                            </p>
                    })}
                </div>
                { (currentTest !== null) ?
                    <div className="room-task-variants-form__body__variant__values__body">
                        <BodyVariantTableInput header={"Входные данные"} items={currentTestInput} setItems={setCurrentTestInput}/>
                        <BodyVariantTableInput header={"Выходные данные"} items={currentTestOutput} setItems={setCurrentTestOutput}/>
                    </div> : null
                }
            </div>
        </div>
    );
};

export default BodyVariant;