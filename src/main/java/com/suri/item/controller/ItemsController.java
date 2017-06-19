package com.suri.item.controller;

import java.util.List;

import com.suri.item.model.Item;
import com.suri.item.repository.ItemRepository;
import com.suri.item.service.ItemService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lakshay13@gmail.com
 */
@RestController
public class ItemsController {

    public static final Logger logger = org.slf4j.LoggerFactory.getLogger(ItemsController.class);

    @Autowired
    private ItemService itemService;

    @Autowired
    private ItemRepository itemRepository;

    @RequestMapping(value="/items", method = RequestMethod.GET)
    public ResponseEntity<List<Item>> getListOfItems() {

        List<Item> itemList = itemService.getItemList();

        if (itemList.size() == 0) {
            logger.error("There are no items available");
            return new ResponseEntity<List<Item>>(itemList, HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<List<Item>>(itemList, HttpStatus.OK);
    }

    @RequestMapping(value = "/items/add", method = RequestMethod.POST)
    public ResponseEntity<Item> addItemDetails(@RequestParam(value="id") Integer productId,
                                     @RequestParam(value="name", required = false) String productName,
                                     @RequestParam(value="type", required = false) String productType,
                                     @RequestParam(value="status", required = false) String availabilityStatus,
                                     @RequestParam(value="cost") long cost) {

        Item item = null;
        if (productId == null && cost < 0 )  {
            String errorMsg = "Product Id cannot be null and Cost can not be negative";
            logger.error(errorMsg);
            return new ResponseEntity<Item>(item, HttpStatus.BAD_REQUEST);
        }

        item = new Item(productId, productName, productType, availabilityStatus, cost);
        Item itemSaved = itemRepository.save(item);

        return new ResponseEntity<Item>(itemSaved, HttpStatus.OK);
    }

    @RequestMapping(value = "/items/remove/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Item> removeItemById(@PathVariable("id") Integer productId) {

        Item item = itemRepository.findOne(productId);

        if (item == null) {
            logger.error("Item corresponding to the product id {} does not exist", productId);
            return new ResponseEntity<Item>(item, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<Item>(item, HttpStatus.OK);
    }

    @RequestMapping(value = "/items/request/{maxCost}", method = RequestMethod.GET)
    public ResponseEntity<List<Item>> getItemsByUpperLimit(@PathVariable("maxCost") Long maxCost) {

        List<Item> itemList = itemRepository.findByCostLessThan(maxCost);

        if (itemList == null) {
            logger.error("There are no items available");
            return new ResponseEntity<List<Item>>(itemList, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<List<Item>>(itemList, HttpStatus.OK);
    }
}
