import React from 'react';
import TreeItem from "./TreeItem/TreeItem";

const TreeView = ({ items, onClick }) => {
    return (
        <div className={"tree-view"}>
            {items.map((item, index) => {
                return <TreeItem item={item} onClick={onClick} key={`${item.id}${index}`}/>
            })}
        </div>
    );
};

export default TreeView;