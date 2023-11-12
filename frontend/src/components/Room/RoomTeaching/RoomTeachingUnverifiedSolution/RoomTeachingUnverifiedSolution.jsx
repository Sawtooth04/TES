import React from 'react';
import TreeView from "../../../UI/TreeView/TreeView";

const RoomTeachingUnverifiedSolution = () => {
    return (
        <div className={"room-teaching__body__unverified-solution unverified-solution"}>
            <div className={"unverified-solution__sidebar"}>
                <TreeView items={
                    [
                        {
                            name: "First",
                            nestedItems: [
                                {name: "First_first"},
                                {name: "First_second"}
                            ]
                        },
                        {name: "Second"}
                    ]
                }/>
            </div>
            <div className={"unverified-solution__body"}>

            </div>
            <div className={"unverified-solution__solution-controls"}>

            </div>
        </div>
    );
};

export default RoomTeachingUnverifiedSolution;