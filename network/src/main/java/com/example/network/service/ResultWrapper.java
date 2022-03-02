package com.example.network.service;

public class ResultWrapper<T> {

    public class Success<T> extends ResultWrapper<T>{
        public Success(T value) {
        }
    }
    public class Failure extends ResultWrapper<String>{
        public Failure(String value) {
        }
    }
    public class GenericError extends ResultWrapper<Object>{
        public GenericError(String message) {
        }
    }

}
