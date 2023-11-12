import React from 'react';
import TreeItem from "./TreeItem/TreeItem";

const TreeView = ({ items }) => {
    return (
        <div className={"tree-view"}>
            {items.map((item) => {
                return <TreeItem item={item}/>
            })}
        </div>
    );
};

export default TreeView;