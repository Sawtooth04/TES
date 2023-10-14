import React from 'react';
import {Link} from "react-router-dom";
import PasswordInput from "../UI/PasswordInput/PasswordInput";

const Login = () => {
    return (
        <div className={"login smooth-show-component"}>
            <img className={"login__logo"} src={"/assets/images/logo/logo-396-400.png"} alt={"Logo"}/>
            <div className={"login__header header"}>
                <p className={"header__name header__name_primary"}> Thoth </p>
                <p className={"header__name header__name_secondary"}> Education </p>
                <p className={"header__name header__name_primary"}> System </p>
            </div>
            <div className={"login__logo-underline"}/>
            <input className={"login__login-input login__input"} placeholder={"Логин"} maxLength={12}/>
            <PasswordInput className={"login__password-input"} maxLength={12} placeholder={"Пароль"}/>
            <button className={"login__submit-button"}> Войти </button>
            <Link className={"login__link"} to={"/registration"} > Регистрация </Link>
            <Link className={"login__link"} to={"/"} > Забыли пароль? </Link>
        </div>
    );
};

export default Login;