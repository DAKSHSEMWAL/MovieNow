package com.varistor.assignment.utils;

public interface CallBacks {
    void callbackObserver(Object obj);
    public interface playerCallBack {
        void onItemClickOnItem(Integer albumId);
        void onPlayingEnd();
    }
}