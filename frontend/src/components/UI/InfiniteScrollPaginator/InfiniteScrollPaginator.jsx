import React, {useEffect, useState} from 'react';
import InfiniteScroll from "../InfiniteScroll/InfiniteScroll";

const InfiniteScrollPaginator = ({ children, params, endpoint, countByPage, maxCountByPage, data, updateData }) => {
    const [isLoading, setIsLoading] = useState(true);
    const [scrolledToTheEnd, setScrolledToTheEnd] = useState(false);
    const [start, setStart] = useState(0);
    const [end, setEnd] = useState(countByPage);
    const [isPrevious, setIsPrevious] = useState(true);

    useEffect(() => {
        void getData();
    }, [start, end]);

    function buildParams() {
        let result = new URLSearchParams();

        for (let key in params)
            result.append(key, params[key]);
        (isPrevious) ? result.append('start', String(start)) :
            result.append('start', String(end - countByPage));
        result.append('count', String(countByPage));
        return result.toString();
    }

    async function getData() {
        setIsLoading(true);
        let response = await fetch(`${endpoint}?${buildParams()}`, {
            method: "get",
            headers: {
                'Content-Type': 'application/json',
                'Accept': 'application/json'
            }
        })
        if (response.ok)
            appendData(await response.json());
    }

    function appendData(arr) {
        if (!isPrevious) {
            if (arr.length !== 0) {
                if (data.length + countByPage > maxCountByPage)
                    data.splice(0, countByPage);
                updateData([...data, ...arr])
            }
            else
                setScrolledToTheEnd(true);
        }
        else {
            if (data.length + countByPage > maxCountByPage)
                data.splice(data.length - countByPage, countByPage);
            updateData([...arr, ...data])
            setScrolledToTheEnd(false);
        }
        setIsLoading(false);
    }

    function onNextPage() {
        if (!isLoading && !scrolledToTheEnd) {
            let newEnd = end + countByPage;

            setIsPrevious(false);
            setEnd(newEnd);
            if (newEnd - start > maxCountByPage && newEnd !== 2 * maxCountByPage)
                setStart(start + countByPage)
        }
    }

    function onPrevPage() {
        if (start >= countByPage && !isLoading) {
            let newStart = start - countByPage;

            setIsPrevious(true);
            setStart(newStart);
            if (end - newStart > maxCountByPage)
                setEnd(end - countByPage);
            return true;
        }
        return false;
    }

    return (
        <InfiniteScroll onNext={onNextPage} onPrev={onPrevPage}>
            { children }
        </InfiniteScroll>
    );
};

export default InfiniteScrollPaginator;