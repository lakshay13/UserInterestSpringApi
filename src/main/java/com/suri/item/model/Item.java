package com.suri.item.model;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author lakshay13@gmail.com
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Item {

    @Id
    private Integer productId;
    private String productName;
    private String productType;
    private String availabilityStatus;
    private Long cost;
}
