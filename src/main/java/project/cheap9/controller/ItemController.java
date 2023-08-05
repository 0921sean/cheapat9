package project.cheap9.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import project.cheap9.domain.Item;
import project.cheap9.service.ItemService;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @GetMapping("/")
    public String list(Model model) {
        List<Item> items = itemService.findItems();
        model.addAttribute("items", items);
        return "home";
    }

    @GetMapping("/items/new")
    public String createForm(Model model) {
        model.addAttribute("form", new ItemForm());
        return "items/createItemForm";
    }

    @PostMapping("/items/new")
    public String create(ItemForm form) {
        Item item = new Item();
        item.setName(form.getName());
        item.setBeforePrice(form.getBeforePrice());
        item.setPrice(form.getPrice());
        item.setDiscountRate((form.getBeforePrice() - form.getPrice()) * 100 / form.getBeforePrice());
        item.setStockQuantity(form.getStockQuantity());

        itemService.saveItem(item);
        return "redirect:/";
    }

    @GetMapping("items/{itemId}/detail")
    public String itemDetailForm(@PathVariable("itemId") Long itemId, Model model) {
        Item item = itemService.findOne(itemId);

        model.addAttribute("item", item);
        return "items/itemDetailForm";
    }

}
