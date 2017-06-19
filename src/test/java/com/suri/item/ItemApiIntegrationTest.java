package com.suri.item;

import java.util.ArrayList;
import java.util.List;

import com.suri.item.controller.ItemsController;
import com.suri.item.model.Item;
import com.suri.item.repository.ItemRepository;
import com.suri.item.service.ItemService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyLong;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static utils.ItemsHelper.getItemList;

/**
 * @author lakshay13@gmail.com
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ItemApiIntegrationTest {

    @MockBean
    private ItemRepository itemRepository;

    @MockBean
    private ItemService itemService;

    @InjectMocks
    private ItemsController itemsController;

    private MockMvc mockMvc;

    @Before
    public void setup() {

        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(itemsController).build();
    }

    @Test
    public void testAddItems() throws Exception {

        Item item = new Item(1, "TennisRacquet", "Sports", "Av", 2500L);

        //given
        given(itemRepository.save(any(Item.class))).willReturn(item);

        //when
        MvcResult result = mockMvc.perform(
                post("/items/add")
                        .param("id", item.getProductId().toString())
                        .param("name", item.getProductName().toString())
                        .param("type", item.getProductType().toString())
                        .param("status", item.getAvailabilityStatus().toString())
                        .param("cost", item.getCost().toString()))
                .andExpect(status().isOk())
                .andReturn();

        //then
        assertEquals(200, result.getResponse().getStatus());
        String returnedValue = "{\"productId\":1,\"productName\":\"TennisRacquet\",\"productType\":\"Sports\",\"availabilityStatus\":\"Av\",\"cost\":2500}";
        assertEquals(returnedValue, result.getResponse().getContentAsString());
    }

    @Test
    public void testGetListOfItems() throws Exception {

        // given
        List<Item> itemList = getItemList();
        given(itemService.getItemList()).willReturn(itemList);

        // when
        MvcResult mvcResult = mockMvc.perform(get("/items"))
                                     .andExpect(status().isOk())
                                     .andReturn();

        // TODO USE gson istead of String
       // Gson gson = new Gson();
       // String jsonValue = gson.toJson(itemList);

        // then
        String resultExpected = "[{\"productId\":1,\"productName\":\"TennisRacquet\",\"productType\":\"Sports\",\"availabilityStatus\":\"Av\",\"cost\":2500},{\"productId\":2,\"productName\":\"GymShorts\",\"productType\":\"Sports\",\"availabilityStatus\":\"Av\",\"cost\":3000},{\"productId\":3,\"productName\":\"GymShoes\",\"productType\":\"Sports\",\"availabilityStatus\":\"NotAv\",\"cost\":3500}]";
        assertEquals(200, mvcResult.getResponse().getStatus());
        assertEquals(resultExpected, mvcResult.getResponse().getContentAsString());
    }

    @Test
    public void testRemoveItems() throws Exception {

        // given
        Item item = new Item(1, "TennisRacquet", "Sports", "Av", 2500L);
        given(itemRepository.findOne(anyInt())).willReturn(item);

        // when
        MvcResult mvcResult = mockMvc.perform(
                 delete("/items/remove/{id}", 1))
                .andExpect(status().isOk())
                .andReturn();

        // then
        assertEquals(200, mvcResult.getResponse().getStatus());
        String returnedValue = "{\"productId\":1,\"productName\":\"TennisRacquet\",\"productType\":\"Sports\",\"availabilityStatus\":\"Av\",\"cost\":2500}";
        assertEquals(returnedValue, mvcResult.getResponse().getContentAsString());
    }

    @Test
    public void testGetItemsByUpperLimit() throws Exception {

        // given
        Item item = new Item(1, "TennisRacquet", "Sports", "Av", 2500L);
        List<Item> itemList = new ArrayList<Item>();
        itemList.add(item);
        given(itemRepository.findByCostLessThan(anyLong())).willReturn(itemList);

        // when
        MvcResult mvcResult = mockMvc.perform(get("/items/request/{maxCost}", 2500))
                .andExpect(status().isOk())
                .andReturn();

        // then
        assertEquals(200, mvcResult.getResponse().getStatus());
        String returnedValue = "[{\"productId\":1,\"productName\":\"TennisRacquet\",\"productType\":\"Sports\",\"availabilityStatus\":\"Av\",\"cost\":2500}]";
        assertEquals(returnedValue, mvcResult.getResponse().getContentAsString());
    }


}
