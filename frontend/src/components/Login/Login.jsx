import React, {useRef} from 'react';
import {Link, useNavigate} from "react-router-dom";
import PasswordInput from "../UI/PasswordInput/PasswordInput";

const Login = () => {
    const loginInput = useRef(null);
    const passwordInput = useRef(null);
    const navigate = useNavigate();

    async function login() {
        if (loginInput.current != null && passwordInput.current != null) {
            let formData = new FormData(), response;
            formData.append("username", loginInput.current.value);
            formData.append("password", passwordInput.current.value);
            response = await fetch("/login", {
                method: "post",
                body: formData
            })
            if (response.ok)
                navigate("/");
        }
    }

    return (
        <div className={"login smooth-show-component"}>
            <img className={"login__logo"} src={"/assets/images/logo/logo-396-400.png"} alt={"Logo"}/>
            <div className={"login__header login-header"}>
                <p className={"login-header__name login-header__name_primary"}> Thoth </p>
                <p className={"login-header__name login-header__name_secondary"}> Education </p>
                <p className={"login-header__name login-header__name_primary"}> System </p>
            </div>
            <div className={"login__logo-underline"}/>
            <input className={"login__login-input login__input"} placeholder={"Логин"} maxLength={12} ref={loginInput}/>
            <PasswordInput className={"login__password-input"} maxLength={12} placeholder={"Пароль"} inputRef={passwordInput}/>
            <button className={"login__submit-button"} onClick={login}> Войти </button>
            <Link className={"login__link"} to={"/registration"} > Регистрация </Link>
            <Link className={"login__link"} to={"/"} > Забыли пароль? </Link>
        </div>
    );
};

export default Login;