import React from 'react';

const RoomMember = ({ member }) => {
    return (
        <div className={"room-members__members__list__member"}>
            <p className={"room-members__members__member__name"}> {member.name} </p>
        </div>
    );
};

export default RoomMember;