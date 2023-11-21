import React, {useRef} from 'react';

const BodyVariantTableInput = ({ items, header, setItems }) => {
    const input = useRef(null);

    function onChange(key) {
        let newItems = [...items];
        newItems[key] = input.current.value;
        setItems(newItems);
    }

    function onAdd() {
        setItems([...items, ""]);
    }

    return (
        <div className={"body-variant-table-input"}>
            <div className={"body-variant-table-input__header"}>
                <p className={"body-variant-table-input__header__name"}> {header} </p>
            </div>
            <div className="body-variant-table-input__body">
                <img className="body-variant-table-input__body__header" src={"/assets/images/icons/add-task.png"} alt={"Add"}
                     onClick={onAdd}/>
                <div className="body-variant-table-input__body__content">
                    {items?.map((item, key) => {
                        return <input className="body-variant-table-input__body__content__item" value={item} key={key}
                            onChange={() => {onChange(key)}} ref={input}/>
                    })}
                </div>
            </div>
        </div>
    );
};

export default BodyVariantTableInput;