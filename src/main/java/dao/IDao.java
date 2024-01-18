package dao;

import java.util.List;

public interface IDao<T> {
    T create(T object);
    List<T> read();
    T read(String id);
//    boolean update(T object);
    boolean delete(String id);
    void close();
}
