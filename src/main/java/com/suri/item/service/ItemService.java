package com.suri.item.service;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.suri.item.model.Item;
import org.springframework.stereotype.Component;

/**
 * @author lakshay13@gmail.com
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class ItemService {
    List<Item> itemList;
}
