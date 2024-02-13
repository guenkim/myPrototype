package com.guen.program.jpashop.model.dto.request;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

import java.util.List;

@Getter
public class OrderDto {

    private Long memberId;
    private List<ItemOrder> itemList;

    @JsonCreator
    public OrderDto(@JsonProperty("memberId") final Long memberId, @JsonProperty("itemList") List<ItemOrder> itemList) {
        this.memberId = memberId;
        this.itemList = itemList;
    }

    @Getter
    public static class ItemOrder{
        private Long itemId;

        private int count;
        @JsonCreator
        public ItemOrder(@JsonProperty("itemId") final Long itemId,@JsonProperty("count") final int count) {
            this.itemId = itemId;
            this.count = count;
        }
    }
}
