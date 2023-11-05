import React, {useRef} from 'react';
import PopUpForm from "../../../../UI/PopUpForm/PopUpForm";

const ChooseLanguageDialog = ({ setLanguage, languages, onChoose }) => {
    const select = useRef();

    return (
        <PopUpForm header={"Выбрать язык"}>
            <select ref={select}>
                { languages.map((language) => {
                    return <option key={language}> { language } </option>
                }) }
            </select>
            <button onClick={() => {setLanguage(select.current.value); onChoose();}}> Подтвердить </button>
        </PopUpForm>
    );
};

export default ChooseLanguageDialog;