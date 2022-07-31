package com.foodorder.tacocloud.repository.dataJpa;

import com.foodorder.tacocloud.model.TacoOrder;
import org.springframework.data.repository.CrudRepository;

public interface OrderRepository extends CrudRepository<TacoOrder, Long> {
    TacoOrder save(TacoOrder tacoOrder);

}
