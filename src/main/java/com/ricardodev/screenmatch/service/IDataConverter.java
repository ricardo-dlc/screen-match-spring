package com.ricardodev.screenmatch.service;

public interface IDataConverter {
    <T> T getData(String json, Class<T> className);
}
