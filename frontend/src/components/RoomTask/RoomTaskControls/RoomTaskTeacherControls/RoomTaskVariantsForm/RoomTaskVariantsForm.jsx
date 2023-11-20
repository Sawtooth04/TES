import React, {useEffect, useState} from 'react';
import SidebarVariant from "./SidebarVariant/SidebarVariant";
import BodyVariant from "./BodyVariant/BodyVariant";

const RoomTaskVariantsForm = ({ roomID, roomTaskID, onCancel }) => {
    const [variants, setVariants] = useState([]);
    const [currentVariant, setCurrentVariant] = useState(null);

    useEffect(() => {
        void getVariants();
    }, []);

    async function getVariants() {
        let params = new URLSearchParams();
        params.set("roomID", roomID);
        params.set("roomTaskID", roomTaskID);
        let response = await fetch(`/task-variant/get-all?${params.toString()}`, {
            method: "get",
            headers: {"Accept": "application/json"}
        });

        if (response.ok)
            setVariants(await response.json());
    }

    return (
        <div className={"room-task-variants-form"}>
            <div className={"room-task-variants-form__sidebar"}>
                {variants.map((variant) => {
                    return <SidebarVariant variant={variant} key={variant.roomTaskVariantID} onClick={setCurrentVariant}/>
                })}
            </div>
            <div className={"room-task-variants-form__body"}>
                { (currentVariant != null) ? <BodyVariant variant={currentVariant}/> : null}
            </div>
        </div>
    );
};

export default RoomTaskVariantsForm;