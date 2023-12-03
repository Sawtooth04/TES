import React, {useEffect, useState} from 'react';
import {Link, useParams} from "react-router-dom";

const CustomerVerification = () => {
    const { token } = useParams();
    const [isVerified, setIsVerified] = useState(null);

    useEffect(() => {
        async function tryVerify() {
            let response = await fetch(`/customer/verification/verify?token=${token}`, {
                method: "get",
                headers: {Accept: 'application/json'}
            });
            setIsVerified(await response.json());
        }

        void tryVerify();
    }, []);

    return (
        <div className={"customer-verification"}>
            {(isVerified === null) ? <p className={"customer-verification__text"}>
                Идёт верификация почты, подождите пожалуйста...
            </p> :
                (isVerified) ?
                <div className={"customer-verification__successful"}>
                    <p className={"customer-verification__successful__text"}> Верификация прошла успешно. </p>
                    <Link className={"customer-verification__successful__link"} to={"/login"} replace={true}>
                        Теперь вы можете войти в свой аккаунт
                    </Link>
                </div> : <p className={"customer-verification__text"}>
                    Не удалось верифицировать аккаунт. Возможно, токен верификации стал недействительным.
                </p>
            }
        </div>
    );
};

export default CustomerVerification;