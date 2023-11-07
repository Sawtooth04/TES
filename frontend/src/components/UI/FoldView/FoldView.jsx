import React, {useRef, useState} from 'react';

const FoldView = ({ header, children }) => {
    const [isOpened, setIsOpened] = useState(false);
    const content = useRef();

    return (
        <div className={"fold-view"}>
            <p className={"fold-view__header"} onClick={() => {setIsOpened(!isOpened)}}> {header} </p>
            <div className={"fold-view__content"} ref={content} style={{maxHeight: (isOpened) ? `${content.current.scrollHeight}px` : "0"}}>
                {children}
            </div>
        </div>
    );
};

export default FoldView;