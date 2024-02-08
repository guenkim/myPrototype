package com.guen.program.jpashop.controller;


import com.guen.jwt.security.UserAuthorize;
import com.guen.program.jpashop.model.dto.BookForm;
import com.guen.program.jpashop.model.entity.item.Book;
import com.guen.program.jpashop.model.entity.item.Item;
import com.guen.program.jpashop.service.ItemService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Item API")
@Slf4j
@RestController
@RequestMapping("/api")
@UserAuthorize
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @PostMapping("/items/new")
    public ResponseEntity create(@RequestBody final BookForm form) {

        Book book = new Book();
        book.setName(form.getName());
        book.setPrice(form.getPrice());
        book.setStockQuantity(form.getStockQuantity());
        book.setAuthor(form.getAuthor());
        book.setIsbn(form.getIsbn());

        itemService.saveItem(book);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/items")
    public ResponseEntity list() {
        List<Item> items = itemService.findItems();
        return ResponseEntity.ok().body(items);
    }

    @GetMapping("items/{itemId}/edit")
    public ResponseEntity updateItemForm(@PathVariable("itemId") final Long itemId) {
        Book item = (Book) itemService.findOne(itemId);

        BookForm form = new BookForm();
        form.setId(item.getId());
        form.setName(item.getName());
        form.setPrice(item.getPrice());
        form.setStockQuantity(item.getStockQuantity());
        form.setAuthor(item.getAuthor());
        form.setIsbn(item.getIsbn());
        return ResponseEntity.ok().body(form);
    }

    @PostMapping("items/{itemId}/edit")
    public ResponseEntity updateItem(@PathVariable final Long itemId, @RequestBody final BookForm form) {
        itemService.updateItem(itemId, form.getName(), form.getPrice(), form.getStockQuantity());
        return ResponseEntity.noContent().build();
    }
}





