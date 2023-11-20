import React from 'react';

const SidebarVariant = ({ variant, onClick }) => {

    function onVariantClick() {
        onClick(variant);
    }

    return (
        <div className={"room-task-variants-form__sidebar__variant"} onClick={onVariantClick}>
            <p className="room-task-variants-form__sidebar__variant__name"> Вариант {variant.variant} </p>
        </div>
    );
};

export default SidebarVariant;