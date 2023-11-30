import React, {useState} from 'react';
import {maxNotificationsPerPagesCount, notificationsPerPagesCount} from "../../constants";
import InfiniteScrollPaginator from "../UI/InfiniteScrollPaginator/InfiniteScrollPaginator";
import CancelButton from "../UI/CancelButton/CancelButton";

const Notifications = ({ hidden }) => {
    const [notifications, setNotifications] = useState([]);

    async function setIsRead(notification) {
        await fetch("/customer-notification/set-is-read", {
            method: "post",
            headers: {"Content-Type": "application/json"},
            body: JSON.stringify({ "customerNotificationID": notification.customerNotificationID })
        });
    }

    async function setIsReadAllCallback() {
        notifications.forEach((notification) => {
            void setIsRead(notification);
        })
        setNotifications([]);
    }

    async function setIsReadCallback(notification) {
        await setIsRead(notification);
        notifications.splice(notifications.indexOf(notification), 1);
        setNotifications([...notifications]);
    }

    return (
        <div className={`notifications notifications_${hidden ? 'hidden' : 'opened'}`}>
            {(notifications.length === 0) ?
                <p className={"notifications__text"}> Уведомления отсутствуют </p> : null
            }
            <InfiniteScrollPaginator params={{}} endpoint={'/customer-notification/get-page'} data={notifications}
                countByPage={notificationsPerPagesCount} maxCountByPage={maxNotificationsPerPagesCount} updateData={setNotifications}>
                {notifications.map((notification) => {
                    return <div className={"notifications__notification"} key={notification.customerNotificationID}>
                        <p className={"notifications__notification__header"}> {notification.header} </p>
                        <p className={"notifications__notification__text"}> {notification.text} </p>
                        <CancelButton onClick={async () => {await setIsReadCallback(notification)}}/>
                    </div>
                })}
            </InfiniteScrollPaginator>
            <button className={"notifications__button"} onClick={setIsReadAllCallback}>
                Прочитано
            </button>
        </div>
    );
};

export default Notifications;