import React, {useState} from 'react';

const PasswordInput = ({className, maxLength, placeholder}) => {
    const [isHidden, setIsHidden] = useState(true)

    function switchState() {
        setIsHidden(!isHidden);
    }

    return (
        <div className={`${className} password-input`}>
            <input className={"password-input__input"} placeholder={placeholder} type={isHidden ? "password" : "text"}
                maxLength={maxLength}/>
            {isHidden ?
                <button className={"password-input__button"} onClick={switchState}>
                    <img src={"/assets/images/icons/show-password.png"} alt={"Show"}/>
                </button> :
                <button className={"password-input__button"} onClick={switchState}>
                    <img src={"/assets/images/icons/hide-password.png"} alt={"Hide"}/>
                </button>
            }
        </div>
    );
};

export default PasswordInput;