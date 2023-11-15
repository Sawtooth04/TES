import React, {useEffect, useState} from 'react';
import TreeView from "../../../UI/TreeView/TreeView";
import {useParams} from "react-router-dom";
import NumberedTextView from "../../../UI/NumberedTextView/NumberedTextView";

const RoomTeachingUnverifiedSolution = () => {
    const { solutionID } = useParams();
    const [solutionTreeItems, setSolutionTreeItems] = useState([]);
    const [currentFile, setCurrentFile] = useState([]);

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

    return (
        <div className={"room-teaching__body__unverified-solution unverified-solution"}>
            <div className={"unverified-solution__sidebar"}>
                <TreeView items={solutionTreeItems} onClick={onItemClick}/>
            </div>
            <div className={"unverified-solution__body"}>
                <NumberedTextView text={currentFile}/>
            </div>
            <div className={"unverified-solution__solution-controls"}>

            </div>
        </div>
    );
};

export default RoomTeachingUnverifiedSolution;