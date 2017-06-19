package com.suri.item;

import java.util.ArrayList;
import java.util.List;

import com.suri.item.model.Item;
import com.suri.item.repository.ItemRepository;
import com.suri.item.service.ItemService;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.mockito.Matchers;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyLong;
import static utils.ItemsHelper.getItemList;

/**
 * @author lakshay13@gmail.com
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class ItemApiRestAssuredTest {

    @MockBean
    private ItemRepository itemRepository;

    @MockBean
    private ItemService itemService;

    @LocalServerPort
    int port;

    @BeforeClass
    public static void setup() {

       // RestAssured.authentication
    }

    @Test
    public void testAddItems() {

        // given
        Item item = new Item(1, "TennisRacquet", "Sports", "Av", 2500L);
        BDDMockito.given(itemRepository.save(Matchers.any(Item.class))).willReturn(item);

        Response response =
            given()
                .contentType(ContentType.JSON)
                .port(port)
                .queryParam("id", item.getProductId())
                .queryParam("name", item.getProductName())
                .queryParam("type", item.getProductType())
                .queryParam("status", item.getAvailabilityStatus())
                .queryParam("cost", item.getCost()).
            when()
                .post("/items/add");
        // then
        assertEquals(HttpStatus.SC_OK, response.getStatusCode());
        String returnedValue = "{\"productId\":1,\"productName\":\"TennisRacquet\",\"productType\":\"Sports\",\"availabilityStatus\":\"Av\",\"cost\":2500}";
        assertEquals(returnedValue, response.getBody().asString());
    }


    @Test
    public void testGetListOfItems() {

        // given
        List<Item> itemList = getItemList();
        BDDMockito.given(itemService.getItemList()).willReturn(itemList);

        Response response =
                given()
                       .contentType(ContentType.JSON)
                       .port(port).
                when()
                       .get("items/");


        // then
        assertEquals(HttpStatus.SC_OK, response.getStatusCode());
        String returnedValue = "[{\"productId\":1,\"productName\":\"TennisRacquet\",\"productType\":\"Sports\",\"availabilityStatus\":\"Av\",\"cost\":2500},{\"productId\":2,\"productName\":\"GymShorts\",\"productType\":\"Sports\",\"availabilityStatus\":\"Av\",\"cost\":3000},{\"productId\":3,\"productName\":\"GymShoes\",\"productType\":\"Sports\",\"availabilityStatus\":\"NotAv\",\"cost\":3500}]";
        assertEquals(returnedValue, response.getBody().asString());
    }

    @Test
    public void testRemoveItems() {

        // given
        Item item = new Item(1, "TennisRacquet", "Sports", "Av", 2500L);
        BDDMockito.given(itemRepository.findOne(anyInt())).willReturn(item);

        Response response =
                given()
                        .contentType(ContentType.JSON)
                        .port(port).
                when()
                        .delete("/items/remove/{id}", 1);

        // then
        assertEquals(HttpStatus.SC_OK, response.getStatusCode());
        String returnedValue = "{\"productId\":1,\"productName\":\"TennisRacquet\",\"productType\":\"Sports\",\"availabilityStatus\":\"Av\",\"cost\":2500}";
        assertEquals(returnedValue, response.getBody().asString());
    }

    @Test
    public void testGetItemsByUpperLimit() {

        // given
        Item item = new Item(1, "TennisRacquet", "Sports", "Av", 2500L);
        List<Item> itemList = new ArrayList<Item>();
        itemList.add(item);
        BDDMockito.given(itemRepository.findByCostLessThan(anyLong())).willReturn(itemList);

        Response response =
                given()
                        .contentType(ContentType.JSON)
                        .port(port).
                when()
                        .get("/items/request/{maxCost}", 2500);

        // then
        assertEquals(HttpStatus.SC_OK, response.getStatusCode());
        String returnedValue = "[{\"productId\":1,\"productName\":\"TennisRacquet\",\"productType\":\"Sports\",\"availabilityStatus\":\"Av\",\"cost\":2500}]";
        assertEquals(returnedValue, response.getBody().asString());
    }

}
