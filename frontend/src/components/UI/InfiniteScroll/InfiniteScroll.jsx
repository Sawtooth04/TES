import React, {useEffect, useRef, useState} from 'react';

const InfiniteScroll = ({ onPrev, onNext, children }) => {
    const [intersectionObserver, setIntersectionObserver] = useState(null);
    const wrapper = useRef(null);

    useEffect(() => {
        setIntersectionObserver(new IntersectionObserver(onIntersect, {
            root: document.querySelector('.infinite-scroll'),
            threshold: 1
        }))
    }, []);

    useEffect(() => {
        if (children.length > 1 && intersectionObserver != null)
            observe();
    }, [intersectionObserver]);

    useEffect(() => {
        observe();
    }, [needToUpdateTargets]);

    function observe() {
        intersectionObserver.observe(wrapper.current.firstChild);
        intersectionObserver.observe(wrapper.current.lastChild);
    }

    function onIntersect(entries, observer) {
        if (entries[0].intersectionRatio === 1) {
            if (entries[0].target === wrapper.current.lastChild) {
                onNext();
                observer.unobserve(wrapper.current.lastChild)
            }
            else if (onPrev())
                observer.unobserve(wrapper.current.firstChild);
        }
    }

    return (
        <div className={"infinite-scroll"} ref={wrapper}>
            {children}
        </div>
    );
};

export default InfiniteScroll;