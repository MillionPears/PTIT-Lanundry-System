package com.laundry.promotion_service.entity;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


import java.io.Serializable;

@Embeddable
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class PromotionUsageId implements Serializable {

    private Long promotionId;


    private Long rankId;


    private Long customerId;
}
