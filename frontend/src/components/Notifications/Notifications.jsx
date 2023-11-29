import React, {useState} from 'react';
import {maxNotificationsPerPagesCount, notificationsPerPagesCount} from "../../constants";
import InfiniteScrollPaginator from "../UI/InfiniteScrollPaginator/InfiniteScrollPaginator";

const Notifications = () => {
    const [notifications, setNotifications] = useState([]);

    return (
        <div className={"notifications"}>
            <InfiniteScrollPaginator params={{}} endpoint={'/customer-notification/get-page'} data={notifications}
                countByPage={notificationsPerPagesCount} maxCountByPage={maxNotificationsPerPagesCount} updateData={setNotifications}>
                {notifications.map((notification) => {
                    return <div className={"notifications__notification"}>
                        <p className={"notifications__notification__header"}> {notification.header} </p>
                        <p className={"notifications__notification__text"}> {notification.text} </p>
                    </div>
                })}
            </InfiniteScrollPaginator>
        </div>
    );
};

export default Notifications;