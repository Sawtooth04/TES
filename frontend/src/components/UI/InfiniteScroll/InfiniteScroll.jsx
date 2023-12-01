import React, {useEffect, useRef, useState} from 'react';

const InfiniteScroll = ({ onPrev, onNext, children }) => {
    const wrapper = useRef(null);

    useEffect(() => {
        if (children.length > 1) {
            let observer = new IntersectionObserver(onIntersect, {
                root: document.querySelector('.infinite-scroll'),
                threshold: 0.5
            })

            if (wrapper.current.firstChild !== null && wrapper.current.lastChild !== null) {
                observer.observe(wrapper.current.firstChild);
                observer.observe(wrapper.current.lastChild);
            }
            return () => { observer.disconnect() }
        }
    });

    function onIntersect(entries, observer) {
        if (Math.round(entries[0].intersectionRatio) === 1) {
            if (entries[0].target === wrapper.current.lastChild)
                onNext();
            else
                onPrev();
        }
    }

    return (
        <div className={"infinite-scroll"} ref={wrapper}>
            {children}
        </div>
    );
};

export default InfiniteScroll;