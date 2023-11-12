import React, {useState} from 'react';
import FoldView from "../../FoldView/FoldView";

const TreeItem = ({ item }) => {
    const [isFoldViewOpened, setIsFoldViewOpened] = useState(false);

    async function onItemClick(event) {
        event.stopPropagation();
        setIsFoldViewOpened(!isFoldViewOpened);
    }

    return (
        <div className={"tree-view__item"} onClick={onItemClick}>
            <p className={"tree-view__item__item"}> { item.name } </p>
            { (typeof(item.nestedItems) !== "undefined") ?
                <div className={"tree-view__item__nested-items"}>
                    <FoldView header={""} state={isFoldViewOpened}>
                        {item.nestedItems.map((nestedItem) => {
                            return <TreeItem item={nestedItem}/>
                        })}
                    </FoldView>
                </div> : null
            }
        </div>
    );
};

export default TreeItem;