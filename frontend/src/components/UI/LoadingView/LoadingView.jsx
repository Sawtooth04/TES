import React from 'react';

const LoadingView = () => {
    return (
        <div className={"loading-view"}>
            <div className={"loading-view__base"}>
                <div className={"loading-view__base__background"}>
                    <div className={"loading-view__base__background__center"}/>
                    <div className={"loading-view__base__background__spinner"}/>
                </div>
            </div>
        </div>
    );
};

export default LoadingView;