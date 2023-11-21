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

    function onVariantAdd() {
        let lastVariant = variants.reduce((prev, curr) => (prev.variant <= curr.variant) ? curr : prev);

        setVariants([...variants, {
            "description": "",
            "roomTaskID": roomTaskID,
            "variant": lastVariant.variant + 1,
            "isNew": true
        }]);
    }

    function saveVariant(variant, body) {
        let newVariants = [...variants];
        newVariants[variants.findIndex((item) => item.variant === variant.variant)] = variant;
        setVariants(newVariants);
    }

    function deleteVariant(variant) {
        let newVariants = [...variants];
        newVariants.splice(variants.findIndex((item) => item.variant === variant.variant), 1);
        setVariants(newVariants);
        setCurrentVariant(null);
    }

    return (
        <div className={"room-task-variants-form"}>
            <div className={"room-task-variants-form__sidebar"}>
                <img className="room-task-variants-form__sidebar__header"
                     src={"/assets/images/icons/add-task.png"} alt={"Add"} onClick={onVariantAdd}/>
                {variants.map((variant) => {
                    return <SidebarVariant variant={variant} key={variant.variant} onClick={setCurrentVariant}/>
                })}
            </div>
            <div className={"room-task-variants-form__body"}>
                { (currentVariant != null) ? <BodyVariant variant={currentVariant} saveVariant={saveVariant}
                    deleteVariant={deleteVariant}/> : null}
            </div>
        </div>
    );
};

export default RoomTaskVariantsForm;