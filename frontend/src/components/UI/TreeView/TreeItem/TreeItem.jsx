import React, {useState} from 'react';
import FoldView from "../../FoldView/FoldView";
import PopUpView from "../../PopUpView/PopUpView";

const TreeItem = ({ item, onClick }) => {
    const [isPopUpViewOpened, setIsPopUpViewOpened] = useState(false);

    async function onItemClick(event) {
        event.stopPropagation();
        onClick(item);
        setIsPopUpViewOpened(!isPopUpViewOpened);
    }

    return (
        <div className={"tree-view__item"} onClick={onItemClick}>
            <p className={"tree-view__item__item"}> { item.name } </p>
            { (typeof(item.nestedItems) !== "undefined") ?
                <div className={"tree-view__item__nested-items"}>
                    <PopUpView state={isPopUpViewOpened}>
                        {item.nestedItems.map((nestedItem, index) => {
                            return <TreeItem item={nestedItem} onClick={onClick} key={`${item.id}${index}`}/>
                        })}
                    </PopUpView>

                </div> : null
            }
        </div>
    );
};

export default TreeItem;