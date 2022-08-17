package com.foodorder.tacocloud.service;

import com.foodorder.tacocloud.model.Ingredient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class RestTemplateService {

    private final RestTemplate restTemplate;

    public RestTemplateService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }


    public Ingredient ingredientById (String ingredientId){
        return restTemplate.getForObject("http://localhost:8080/ingredients/{id}",Ingredient.class, ingredientId);
    }

//    public Ingredient ingredientById(String ingredientId){
//        Map<String, String>urlVariables = new HashMap<>();
//        urlVariables.put("id", ingredientId );
//        return restTemplate.getForObject("http://localhost:8080/ingredients/{id}",Ingredient.class, urlVariables);
//    }

//    public Ingredient ingredientById (String ingredientId){
//        ResponseEntity<Ingredient>responseEntity = restTemplate.getForEntity("http://localhost:8080/ingredients/{id}",Ingredient.class, ingredientId);
//        log.info("Fetched time: {}",
//                responseEntity.getHeaders().getDate());
//        return responseEntity.getBody();
//    }

    public  void updateIngredient( Ingredient ingredient){
        restTemplate.put("http://localhost:8080/ingredients/{id}", ingredient, ingredient.getId());
    }

    public void deleteIngredient ( Ingredient ingredient){
        restTemplate.delete("http://localhost:8080/ingredients/{id}", ingredient.getId());
    }

    public Ingredient createIngredient(Ingredient ingredient) {
        return restTemplate.postForObject("http://localhost:8080/ingredients",
                ingredient, Ingredient.class);
    }

//    public Ingredient createIngredient(Ingredient ingredient) {
//        ResponseEntity<Ingredient> responseEntity =
//                restTemplate.postForEntity("http://localhost:8080/ingredients",
//                        ingredient,
//                        Ingredient.class);
//        log.info("New resource created at {}",
//                responseEntity.getHeaders().getLocation());
//        return responseEntity.getBody();
//    }
}
