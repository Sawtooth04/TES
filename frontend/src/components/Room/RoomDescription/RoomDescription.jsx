import React from 'react';

const RoomDescription = ({ room }) => {
    return (
        <div className={"room__content__body__description room-description"}>
            {(room != null) ? <p className={"room-description__description"}> {room.description} </p> : null}
        </div>
    );
};

export default RoomDescription;