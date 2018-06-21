package org.gratitude.main.interfaces;

public interface ResponseInterface<T> {
    void onResponseLoaded(T object);
    void onResponseFailed();
}
