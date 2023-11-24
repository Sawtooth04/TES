import React, {useEffect, useState} from 'react';
import {useNavigate, useParams} from "react-router-dom";
import TreeView from "../../../UI/TreeView/TreeView";
import NumberedTextView from "../../../UI/NumberedTextView/NumberedTextView";
import CancelButton from "../../../UI/CancelButton/CancelButton";

const RoomTeachingVerifiedSolution = () => {
    const { roomID, solutionID } = useParams();
    const [solutionTreeItems, setSolutionTreeItems] = useState([]);
    const [currentFile, setCurrentFile] = useState([]);
    const navigate = useNavigate();

    useEffect(() => {
        async function getFirstSolutionTreeLayer() {
            let items = await getSolutionTreeLevel("/");
            setParents(items, "/");
            setSolutionTreeItems(items);
        }

        void getFirstSolutionTreeLayer();
    }, []);

    async function getSolutionTreeLevel(path) {
        let params = new URLSearchParams();
        params.set("roomSolutionID", solutionID);
        params.set("relativePath", path);
        let response = await fetch(`/solution/get-solution-tree-level?${params.toString()}`, {
            method: "get",
            headers: {"Accept": "application/json"}
        });

        return await response.json();
    }

    async function getSolutionTreeFile(path) {
        let params = new URLSearchParams();
        params.set("roomSolutionID", solutionID);
        params.set("relativePath", path);
        let response = await fetch(`/solution/get-solution-tree-file?${params.toString()}`, {
            method: "get",
            headers: {"Accept": "application/json"}
        });

        return await response.json();
    }

    function setParents(items, parent) {
        items.forEach((item) => {
            item.parent = parent;
            item.id = `${item.parent}${item.name}`;
        });
    }

    async function onItemClick(item) {
        if (!item.isDirectory)
            setCurrentFile(await getSolutionTreeFile(`${item.parent}${item.name}`));
        else if (typeof(item.nestedItems) === "undefined") {
            let items = await getSolutionTreeLevel(`${item.parent}${item.name}/`);
            setParents(items, `${item.parent}${item.name}/`)
            item.nestedItems = items;
            setSolutionTreeItems([...solutionTreeItems]);
        }
    }

    function onExit() {
        navigate(`/room/${roomID}/teaching/verified`);
    }

    return (
        <div className={"room-teaching__body__verified-solution verified-solution"}>
            <div className={"verified-solution__sidebar"}>
                <TreeView items={solutionTreeItems} onClick={onItemClick}/>
            </div>
            <div className={"verified-solution__body"}>
                <NumberedTextView text={currentFile}/>
                <CancelButton onClick={onExit}/>
            </div>
        </div>
    );
};

export default RoomTeachingVerifiedSolution;