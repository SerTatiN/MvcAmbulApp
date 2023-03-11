package top.org.mvcambulapp.model.dao;

import java.util.List;
import java.util.Optional;

public interface IDaoBase <E>{
    List<E> listAll();
    Optional<E> getById(Integer id);      // получить объект по id
    E save(E item);  //сохранить
    E update(E item); // обновление
    void delete(Integer id); //удалить объект
}
