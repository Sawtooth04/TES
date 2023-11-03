import React, {useEffect, useRef} from 'react';

const PopUpForm = ({ header, children }) => {

    return (
        <div className={"pop-up-form"}>
            <div className={"pop-up-form__form"}>
                <div className={"pop-up-form__form__header"}>
                    <p className={"pop-up-form__form__header__name"}> { header } </p>
                </div>
                { children }
            </div>
        </div>
    );
};

export default PopUpForm;