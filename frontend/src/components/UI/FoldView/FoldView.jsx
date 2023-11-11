import React, {useEffect, useRef, useState} from 'react';

const FoldView = ({ header, state, children }) => {
    const [isOpened, setIsOpened] = useState(false);
    const content = useRef();

    useEffect(() => {
        if (typeof(state) != "undefined")
            setIsOpened(state);
    }, [state]);

    function switchIsOpened() {
        setIsOpened(!isOpened);
    }

    function onClick() {
        if (typeof(state) === "undefined")
            switchIsOpened();
    }

    return (
        <div className={"fold-view"}>
            <p className={"fold-view__header"} onClick={onClick}> {header} </p>
            <div className={"fold-view__content"} ref={content} style={{maxHeight: (isOpened) ? `${content.current.scrollHeight}px` : "0"}}>
                {children}
            </div>
        </div>
    );
};

export default FoldView;