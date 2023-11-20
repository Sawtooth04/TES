import React, {useEffect, useState} from 'react';
import BodyVariantInput from "./BodyVariantInput/BodyVariantInput";

const BodyVariant = ({ variant }) => {
    const [description, setDescription] = useState(variant.description);

    return (
        <div className={"room-task-variants-form__body__variant"}>
            <p className="room-task-variants-form__body__variant__name"> Вариант {variant.variant} </p>
            <BodyVariantInput name={"Описание:"} value={description} setValue={setDescription}/>
        </div>
    );
};

export default BodyVariant;