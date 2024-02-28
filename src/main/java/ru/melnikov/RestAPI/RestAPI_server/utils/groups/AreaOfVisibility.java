package ru.melnikov.RestAPI.RestAPI_server.utils.groups;

public class AreaOfVisibility {

    //Будут видны все поля DTO кроме тех, которые помечены Private
    public interface Public{}

    //Если поле сгрупировано по этому интерфейсу то в контроллере методы с групой Private
    //Отображают все поля тк Private наследует Public
    //с Public только те которые помечены Public (т е все кроме Private)
    public interface Private extends Public{}
}
