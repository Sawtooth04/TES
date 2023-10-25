import React from 'react';

const RoomPost = ({ post }) => {
    return (
        <div className={"room-main__posts__room-post room-post"}>
            <div className={"room-post__header"}>
                <p className={"room-post__header__name"}> { post.name } </p>
                <p className={"room-post__header__time"}>
                    { (new Date(post.posted)).toLocaleTimeString("ru") }
                </p>
            </div>
            <p className={"room-post__text"}> { post.text } </p>
        </div>
    );
};

export default RoomPost;