//package com.foodorder.tacocloud.repository.jdbcTemplate;
//
//import com.foodorder.tacocloud.model.IngredienRef;
//import com.foodorder.tacocloud.model.Ingredient;
//import com.foodorder.tacocloud.model.Taco;
//import com.foodorder.tacocloud.model.TacoOrder;
//import org.springframework.asm.Type;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.jdbc.core.JdbcTemplate;
//import org.springframework.jdbc.core.PreparedStatementCreator;
//import org.springframework.jdbc.core.PreparedStatementCreatorFactory;
//import org.springframework.jdbc.support.GeneratedKeyHolder;
//import org.springframework.stereotype.Repository;
//
//import java.sql.Types;
//import java.util.Arrays;
//import java.util.Date;
//import java.util.List;
//import java.util.concurrent.atomic.AtomicInteger;
//
//@Repository
//public class JdbcOrderRepository implements OrderRepository {
//
//    private final JdbcTemplate jdbcTemplate;
//
//    @Autowired
//    public JdbcOrderRepository(JdbcTemplate jdbcTemplate) {
//        this.jdbcTemplate = jdbcTemplate;
//    }
//
//    @Override
//    public TacoOrder save(TacoOrder tacoOrder) {
//        PreparedStatementCreatorFactory preparedStatementCreatorFactory = new PreparedStatementCreatorFactory(
//                "insert into Taco_Order "
//                        + "(delivery_name, delivery_street, delivery_city, "
//                        + "delivery_state, delivery_zip, cc_number, "
//                        + "cc_expiration, cc_cvv, placed_at) "
//                        + "values (?,?,?,?,?,?,?,?,?)",
//                Types.VARCHAR, Types.VARCHAR, Types.VARCHAR,
//                Types.VARCHAR, Types.VARCHAR, Types.VARCHAR,
//                Types.VARCHAR, Types.VARCHAR, Types.TIMESTAMP);
//
//        preparedStatementCreatorFactory.setReturnGeneratedKeys(true);
//        tacoOrder.setPlacedAt(new Date());
//        PreparedStatementCreator preparedStatementCreator = preparedStatementCreatorFactory.newPreparedStatementCreator(
//                Arrays.asList(
//                        tacoOrder.getDeliveryName(),
//                        tacoOrder.getDeliveryStreet(),
//                        tacoOrder.getDeliveryCity(),
//                        tacoOrder.getDeliveryState(),
//                        tacoOrder.getDeliveryZip(),
//                        tacoOrder.getCcNumber(),
//                        tacoOrder.getCcExpiration(),
//                        tacoOrder.getCcCVV(),
//                        tacoOrder.getPlacedAt())
//        );
//        GeneratedKeyHolder generatedKeyHolder = new GeneratedKeyHolder();
//        jdbcTemplate.update(preparedStatementCreator, generatedKeyHolder);
//        long id = generatedKeyHolder.getKey().longValue();
//
//        tacoOrder.setId(id);
//
//        List<Taco> tacosList = tacoOrder.getTacos();
//        AtomicInteger i = new AtomicInteger();
//        tacosList.forEach(t -> saveTaco(t.getId(), i.getAndIncrement(), t));
//        return tacoOrder;
//
//    }
//
//    private long saveTaco(Long orderId, int orderKey, Taco taco) {
//        taco.setReatedAt(new Date());
//        PreparedStatementCreatorFactory preparedStatementCreatorFactory = new PreparedStatementCreatorFactory(
//                "insert into Taco "
//                        + "(name, created_at, taco_order, taco_order_key) "
//                        + "values (?, ?, ?, ?)",
//                Types.VARCHAR, Types.TIMESTAMP, Type.LONG, Type.LONG
//        );
//        PreparedStatementCreator preparedStatementCreator = preparedStatementCreatorFactory.newPreparedStatementCreator(
//                Arrays.asList(
//                        taco.getName(),
//                        taco.getReatedAt(),
//                        orderId,
//                        orderKey));
//        GeneratedKeyHolder generatedKeyHolder = new GeneratedKeyHolder();
//
//        jdbcTemplate.update(preparedStatementCreator, generatedKeyHolder);
//        long tacoId = generatedKeyHolder.getKey().longValue();
//        taco.setId(tacoId);
//        saveIngredientRefs(tacoId, taco.getIngredients());
//        return tacoId;
//    }
//
//    private void saveIngredientRefs(long tacoId, List<Ingredient> ingredienRefs) {
//        AtomicInteger i = new AtomicInteger();
//        ingredienRefs.forEach(ingredient -> {
//            jdbcTemplate.update(
//                    "insert into Ingredient_Ref (ingredient, taco, taco_key) "
//                            + "values (?, ?, ?)",
//                    ingredient.getIngredient(), tacoId, i.getAndIncrement());
//        });
//    }
//}
