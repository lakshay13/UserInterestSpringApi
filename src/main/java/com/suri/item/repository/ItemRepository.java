package com.suri.item.repository;

import java.util.List;

import com.suri.item.model.Item;
import org.springframework.data.repository.CrudRepository;

/**
 * @author lakshay13@gmail.com
 */
public interface ItemRepository extends CrudRepository<Item, Integer> {


    List<Item> findByCostLessThan(Long maxCost);
}
