package com.foodorder.tacocloud.repository;

import com.foodorder.tacocloud.model.Taco;
import com.foodorder.tacocloud.model.TacoOrder;

public interface OrderRepository {

    TacoOrder save(TacoOrder tacoOrder);
}
