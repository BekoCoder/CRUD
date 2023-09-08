package uz.pdp.website.service;

import uz.pdp.website.entity.UserEntity;

import java.util.List;
import java.util.UUID;

/**
 *
 * @param <T> type of entity
 * @param <R>type of request
 */
public interface BaseService<T, R> {
    T create(R r);
    T getbyId(UUID id);

    void deletebyId(UUID id);
    void update(R r);
    List<UserEntity> getAllUsers();


}
