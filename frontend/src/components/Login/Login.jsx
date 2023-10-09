import React from 'react';
import {Link} from "react-router-dom";

const Login = () => {
    return (
        <div className={"login"}>
            <img className={"login__logo"} src={"/assets/images/logo/logo-396-400.png"} alt={"Logo"}/>
            <div className={"login__header header"}>
                <p className={"header__name header__name_primary"}> Thoth </p>
                <p className={"header__name header__name_secondary"}> Education </p>
                <p className={"header__name header__name_primary"}> System </p>
            </div>
            <div className={"login__logo-underline"}/>
            <input className={"login__password-input login__input"} placeholder={"Логин"}/>
            <input className={"login__login-input login__input"} placeholder={"Пароль"}/>
            <button className={"login__submit-button"}> Войти </button>
            <Link className={"login__link"} to={"/"}> Регистрация </Link>
            <Link className={"login__link"} to={"/"}> Забыли пароль? </Link>
        </div>
    );
};

export default Login;