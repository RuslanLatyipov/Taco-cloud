package com.foodorder.tacocloud.repository.dataJpa;

import com.foodorder.tacocloud.model.TacoOrder;
import com.foodorder.tacocloud.model.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface OrderRepository extends CrudRepository<TacoOrder, Long> {
    TacoOrder save(TacoOrder tacoOrder);

    void deleteAllBy();

    List<TacoOrder> findByUserOrderByPlacedAtDesc(User user, Pageable pageable);

}
