import React, {useEffect, useState} from 'react';
import CreateCommentForm from "../../UI/CreateCommentForm/CreateCommentForm";
import {useParams} from "react-router-dom";
import InfiniteScroll from "../../UI/InfiniteScroll/InfiniteScroll";
import {postsPerPagesCount, maxPostsPerPagesCount} from "../../../constants";
import RoomPost from "./RoomPost/RoomPost";
import RoomLastTask from "./RoomLastTask/RoomLastTask";

const RoomMain = () => {
    const { roomID } = useParams();
    const [isLoading, setIsLoading] = useState(true);
    const [scrolledToTheEnd, setScrolledToTheEnd] = useState(false);
    const [posts, setPosts]  = useState([]);
    const [postsStart, setPostsStart] = useState(0);
    const [postsEnd, setPostsEnd] = useState(10);
    const [isPrev, setIsPrev] = useState(true);
    const [latestTasks, setLatestTasks]  = useState([]);

    useEffect(() => {
        void getLatestTasks();
    }, [])

    useEffect(() => {
        void getPosts();
    }, [postsStart, postsEnd]);

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

    async function getPosts() {
        let params = new URLSearchParams();
        params.append('roomID', roomID);
        (isPrev) ? params.append('start', String(postsStart)) :
            params.append('start', String(postsEnd - postsPerPagesCount));
        params.append('count', String(postsPerPagesCount));
        setIsLoading(true);

        let response = await fetch(`/room-post/get?${params.toString()}`, {
            method: "get",
            headers: {
                'Content-Type': 'application/json',
                'Accept': 'application/json'
            }
        })
        if (response.ok)
            appendPosts(await response.json());
    }

    function appendPosts(arr) {
        if (!isPrev) {
            if (arr.length !== 0) {
                if (posts.length + postsPerPagesCount > maxPostsPerPagesCount)
                    posts.splice(0, postsPerPagesCount);
                setPosts([...posts, ...arr])
            }
            else
                setScrolledToTheEnd(true);
        }
        else {
            if (posts.length + postsPerPagesCount > maxPostsPerPagesCount)
                posts.splice(posts.length - postsPerPagesCount, postsPerPagesCount);
            setPosts([...arr, ...posts])
            setScrolledToTheEnd(false);
        }
        setIsLoading(false);
    }

    function onNextPage() {
        if (!isLoading && !scrolledToTheEnd) {
            let newEnd = postsEnd + postsPerPagesCount;

            setIsPrev(false);
            setPostsEnd(newEnd);
            if (newEnd - postsStart > maxPostsPerPagesCount && newEnd !== 2 * maxPostsPerPagesCount)
                setPostsStart(postsStart + postsPerPagesCount)
        }
    }

    function onPrevPage() {
        if (postsStart >= postsPerPagesCount && !isLoading) {
            let newStart = postsStart - postsPerPagesCount;

            setIsPrev(true);
            setPostsStart(newStart);
            if (postsEnd - newStart > maxPostsPerPagesCount)
                setPostsEnd(postsEnd - postsPerPagesCount * (postsEnd - newStart) / maxPostsPerPagesCount);
            return true;
        }
        return false;
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
                <InfiniteScroll onNext={onNextPage} onPrev={onPrevPage}>
                    {posts.map((post) => {
                        return <RoomPost post={post} key={post.roomCustomerPostID}/>
                    })}
                </InfiniteScroll>
            </div>
        </div>
    );
};

export default RoomMain;