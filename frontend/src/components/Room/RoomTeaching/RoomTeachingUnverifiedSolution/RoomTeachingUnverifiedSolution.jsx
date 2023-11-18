import React, {useEffect, useState} from 'react';
import TreeView from "../../../UI/TreeView/TreeView";
import {useNavigate, useParams} from "react-router-dom";
import NumberedTextView from "../../../UI/NumberedTextView/NumberedTextView";
import InfiniteScrollPaginator from "../../../UI/InfiniteScrollPaginator/InfiniteScrollPaginator";
import {maxMessagesPerPagesCount, messagesPerPagesCount} from "../../../../constants";
import RoomTeachingChatMessage from "../RoomTeachingChatMessage/RoomTeachingChatMessage";
import CreateRoomTeacherMessageForm from "../CreateRoomTeacherMessageForm/CreateRoomTeacherMessageForm";

const RoomTeachingUnverifiedSolution = () => {
    const { roomID, solutionID } = useParams();
    const [solutionTreeItems, setSolutionTreeItems] = useState([]);
    const [currentFile, setCurrentFile] = useState([]);
    const [messages, setMessages] = useState([]);
    const [createMessageDialogOpened, setCreateMessageDialogOpened] = useState(false);
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

    function switchCreateMessageDialogState() {
        setCreateMessageDialogOpened(!createMessageDialogOpened);
    }

    async function setAccepted() {
        await fetch(`/solution/set-accepted`, {
            method: "post",
            headers: {"Content-Type": "application/json", "Accept": "application/json"},
            body: JSON.stringify(solutionID)
        });
        navigate(`/room/${roomID}/teaching/unverified`);
    }

    async function setDeclined() {
        await fetch(`/solution/set-declined`, {
            method: "post",
            headers: {"Content-Type": "application/json", "Accept": "application/json"},
            body: JSON.stringify(solutionID)
        });
        navigate(`/room/${roomID}/teaching/unverified`);
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
                <InfiniteScrollPaginator params={{"roomSolutionID": solutionID}}
                    endpoint={'/room-customer-message/get-teacher-page-by-solution'} countByPage={messagesPerPagesCount}
                    maxCountByPage={maxMessagesPerPagesCount} data={messages} updateData={setMessages}>
                    {messages.map((message) => {
                        return <RoomTeachingChatMessage message={message} key={message.roomCustomerMessageID}/>
                    })}
                </InfiniteScrollPaginator>
                {createMessageDialogOpened ? <CreateRoomTeacherMessageForm roomSolutionID={solutionID}
                    onAddClick={switchCreateMessageDialogState}/> : null}
                <button className={"unverified-solution__solution-controls__button"} onClick={switchCreateMessageDialogState}>
                    Отправить сообщение
                </button>
                <div className="unverified-solution__solution-controls__wrapper">
                    <button className={"unverified-solution__solution-controls__wrapper__button"} onClick={setDeclined}>
                        Отклонить
                    </button>
                    <button className={"unverified-solution__solution-controls__wrapper__button"} onClick={setAccepted}>
                        Принять
                    </button>
                </div>

            </div>
        </div>
    );
};

export default RoomTeachingUnverifiedSolution;