package project.cheap9.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import project.cheap9.domain.Order;
import project.cheap9.service.ItemService;
import project.cheap9.domain.Item;
import project.cheap9.service.OrderService;

import java.util.List;

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
        order.setDongho(form.getDongho());
        order.setPw(form.getPw());
        Order.setBase(item, order);

        orderService.saveOrder(order);
        return "redirect:/";
    }

    @GetMapping("/orders")
    public String list(Model model) {
        List<Order> orders = orderService.findOrders(number, pw);
        model.addAttribute("orders", orders);
        return "orders/orderList";
    }

}