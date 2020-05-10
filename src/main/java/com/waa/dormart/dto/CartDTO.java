package com.waa.dormart.dto;

import com.waa.dormart.models.Item;

import java.util.ArrayList;
import java.util.List;

public class CartDTO {
    private List<Item> items;

    public CartDTO() {
        this.items = new ArrayList<>();
    }

    public List<Item> getItems() {
        return items;
    }

    public void addItem(Item item) {
        items.add(item);
    }

    public void removeItem(int index) {
        if (index < items.size()) {
            items.remove(index);
        }
    }
}
