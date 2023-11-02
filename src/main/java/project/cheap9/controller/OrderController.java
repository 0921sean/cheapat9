package project.cheap9.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import project.cheap9.domain.Order;
import project.cheap9.domain.OrderStatus;
import project.cheap9.service.ItemService;
import project.cheap9.domain.Item;
import project.cheap9.service.OrderService;

import java.time.LocalDateTime;

@Controller
@RequiredArgsConstructor
public class OrderController {

    private final ItemService itemService;
    private final OrderService orderService;

    @GetMapping("/items/{itemId}/order")
    public String createForm(@PathVariable("itemId") Long itemId, Model model) {

        model.addAttribute("form", new OrderForm());
        return "orders/createOrderForm";
    }

    @PostMapping("/items/{itemId}/order")
    public String create(@PathVariable("itemId") Long itemId, @ModelAttribute("form") OrderForm form) {

        Item item = itemService.findOne(itemId);

        Order order = new Order();
        order.setItem(item);
        order.setCount(form.getCount());
        order.setName(form.getName());
        order.setNumber(form.getNumber());
        order.setZipcode(form.getZipcode());
        order.setAddress(form.getAddress());
        order.setDongho(form.getDongho());
        order.setPw(form.getPw());
        order.setStatus(OrderStatus.WAITING);
        order.setOrderDate(LocalDateTime.now());
        order.setOrderPrice(item.getPrice() * order.getCount());
        itemService.updateStock(item, order.getCount());

        orderService.saveOrder(order);
        return "redirect:/";
    }

}