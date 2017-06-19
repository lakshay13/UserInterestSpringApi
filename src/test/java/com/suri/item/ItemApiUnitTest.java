package com.suri.item;

import java.util.List;

import com.suri.item.controller.ItemsController;
import com.suri.item.model.Item;
import com.suri.item.repository.ItemRepository;
import com.suri.item.service.ItemService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyLong;
import static utils.ItemsHelper.getItemList;

/**
 * @author lakshay13@gmail.com
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ItemApiUnitTest {

    @MockBean
    private ItemRepository itemRepository;

    @MockBean
    private ItemService itemService;

    @Autowired
    private ItemsController itemsController;
    private List<Item> itemList;

    @Test
    public void testAddItems() {

        Item item = new Item(1, "TennisRacquet", "Sports", "Av", 2500L);
        // given
        given(itemRepository.save(any(Item.class))).willReturn(item);

        // when
        ResponseEntity<Item> itemAdded = itemsController.addItemDetails(item.getProductId(), item.getProductName(),
                                                      item.getProductType(), item.getAvailabilityStatus(),
                                                      item.getCost());
        // then
        assertEquals(HttpStatus.OK, itemAdded.getStatusCode());
        assertEquals(item, itemAdded.getBody());
    }

    @Test
    public void testGetListOfItems() {

        List<Item> itemList = getItemList();
        // given
        given(itemService.getItemList()).willReturn(itemList);

        // when
        ResponseEntity<List<Item>> itemListObtained = itemsController.getListOfItems();

        // then
        assertEquals(HttpStatus.OK, itemListObtained.getStatusCode());
        assertEquals(itemList, itemListObtained.getBody());
    }

    @Test
    public void testRemoveItems() {

        Item item = new Item(1, "TennisRacquet", "Sports", "Av", 2500L);

        // given
        given(itemRepository.findOne(anyInt())).willReturn(item);

        // when
        ResponseEntity<Item> itemRemoved = itemsController.removeItemById(1);

        // then
        assertEquals(HttpStatus.OK, itemRemoved.getStatusCode());
        assertEquals(item, itemRemoved.getBody());
    }

    @Test
    public void testGetItemsByUpperLimit() {

        List<Item> itemList = getItemList();

        itemList.remove(2);

        // given
        given(itemRepository.findByCostLessThan(anyLong())).willReturn(itemList);

        // when
        ResponseEntity<List<Item>> itemsFound = itemsController.getItemsByUpperLimit(3100L);

        // then
        assertEquals(HttpStatus.OK, itemsFound.getStatusCode());
        assertEquals(itemList, itemsFound.getBody());
    }
}
