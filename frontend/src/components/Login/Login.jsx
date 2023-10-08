import React from 'react';

const Login = () => {
    return (
        <div className={"login"}>
            <img className={"login__logo"} src={"/assets/images/logo/logo-396-400.png"} alt={"Logo"}/>
            <input className={"login__password-input login__input"}/>
            <input className={"login__login-input login__input"}/>
        </div>
    );
};

export default Login;