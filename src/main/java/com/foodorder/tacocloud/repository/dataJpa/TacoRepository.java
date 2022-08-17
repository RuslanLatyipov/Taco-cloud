package com.foodorder.tacocloud.repository.dataJpa;

import com.foodorder.tacocloud.model.Taco;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface TacoRepository extends CrudRepository<Taco, Long> {
    Iterable<Taco> findAll(PageRequest pageRequest);
    Optional<Taco> findById(Long id);
}
