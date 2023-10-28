import React, {useEffect, useState} from 'react';
import CreateCommentForm from "../../UI/CreateCommentForm/CreateCommentForm";
import {useParams} from "react-router-dom";
import {postsPerPagesCount, maxPostsPerPagesCount} from "../../../constants";
import RoomPost from "./RoomPost/RoomPost";
import RoomLastTask from "../RoomLastTask/RoomLastTask";
import InfiniteScrollPaginator from "../../UI/InfiniteScrollPaginator/InfiniteScrollPaginator";

const RoomMain = () => {
    const { roomID } = useParams();
    const [posts, setPosts]  = useState([]);
    const [latestTasks, setLatestTasks]  = useState([]);

    useEffect(() => {
        void getLatestTasks();
    }, [])

    async function onSendPost(text) {
        await fetch("/room-post/add", {
            method: 'post',
            headers: {
                'Content-Type': 'application/json',
                'Accept': 'application/json'
            },
            body: JSON.stringify({
                roomID: roomID,
                text: text
            })
        });
    }

    async function getLatestTasks() {
        let response = await fetch(`/task/get-latest?roomID=${roomID}`, {
            method: "get",
            headers: {
                'Accept': 'application/json'
            }
        })
        if (response.ok)
            setLatestTasks(await response.json());
    }

    return (
        <div className={"room__content__body__main room-main"}>
            <div className="room-main__last-updates">
                {latestTasks.map((task) => {
                    return <RoomLastTask task={task} key={task.roomTaskID}/>
                })}
            </div>
            <div className="room-main__posts">
                <CreateCommentForm onSendCallback={onSendPost}/>
                <InfiniteScrollPaginator roomID={roomID} endpoint={'/room-post/get'} countByPage={postsPerPagesCount}
                    maxCountByPage={maxPostsPerPagesCount} data={posts} updateData={setPosts}>
                    {posts.map((post) => {
                        return <RoomPost post={post} key={post.roomCustomerPostID}/>
                    })}
                </InfiniteScrollPaginator>
            </div>
        </div>
    );
};

export default RoomMain;