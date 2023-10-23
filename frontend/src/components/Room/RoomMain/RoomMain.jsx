import React, {useEffect, useState} from 'react';
import CreateCommentForm from "../../UI/CreateCommentForm/CreateCommentForm";
import {useParams} from "react-router-dom";
import InfiniteScroll from "../../UI/InfiniteScroll/InfiniteScroll";
import {postsPerPagesCount, maxPostsPerPagesCount} from "../../../constants";

const RoomMain = () => {
    const { roomID } = useParams();
    const [posts, setPosts]  = useState([]);
    const [postsPage, setPostsPage] = useState(0);
    const [isPrev, setIsPrev] = useState(false);

    useEffect(() => {
        void getPosts();
    }, [postsPage]);

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
        params.append('start', String(postsPage * postsPerPagesCount));
        params.append('count', String(postsPerPagesCount));

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
            if (posts.length + postsPerPagesCount > maxPostsPerPagesCount)
                posts.splice(0, postsPerPagesCount);
            setPosts([...posts, ...arr])
        }
        else {
            if (posts.length + postsPerPagesCount > maxPostsPerPagesCount)
                posts.splice(posts.length - postsPerPagesCount, postsPerPagesCount);
            setPosts([...arr, ...posts])
        }
    }

    function onNextPage() {
        setIsPrev(false);
        setPostsPage(postsPage + 1);
    }

    function onPrevPage() {
        if (postsPage >= 1) {
            setIsPrev(true);
            setPostsPage(postsPage - 1);
            return true;
        }
        return false;
    }

    return (
        <div className={"room__content__body__main room-main"}>
            <div className="room-main__last-updates">

            </div>
            <div className="room-main__posts">
                <CreateCommentForm onSendCallback={onSendPost}/>
                <InfiniteScroll onNext={onNextPage} onPrev={onPrevPage}>
                    {posts.map((post) => {
                        return <p key={post.roomCustomerPostID}> {post.text} </p>
                    })}
                </InfiniteScroll>
            </div>
        </div>
    );
};

export default RoomMain;