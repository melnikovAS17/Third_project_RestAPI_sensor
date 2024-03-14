package ru.melnikov.RestAPI.RestAPI_server.utils.errors.enums;

//Enum был создан для указания его в нужном методе (добавления или изменения, и тп)
//и дальнейшей работы ExceptionHandler, он должен отлавливать исключение
//конкретного метода
public enum ExMethodSignature {
    ADD_SENSOR,EDIT_SENSOR,ADD_MEASUREMENT
}
