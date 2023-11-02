package project.cheap9.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import project.cheap9.domain.Order;
import project.cheap9.domain.OrderStatus;
import project.cheap9.dto.order.request.CreateOrderRequest;
import project.cheap9.dto.order.request.GetOrderRequest;
import project.cheap9.dto.order.request.UpdateOrderRequest;
import project.cheap9.dto.order.response.OrderDto;
import project.cheap9.dto.order.response.UpdateOrderResponse;
import project.cheap9.exception.NotEnoughStockException;
import project.cheap9.service.OrderService;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class OrderApiController {

    private final OrderService orderService;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/healthCheck")
    public String healthCheck() {
        return "200 OK";
    }

    /**
     * 주문 생성
     */
    @PostMapping("/api/orders")
    public OrderDto saveOrder(@RequestBody @Valid CreateOrderRequest request) {
        Order order = new Order();
        order.setCount(request.getCount());
        order.setName(request.getName());
        order.setNumber(request.getNumber());
        order.setZipcode(request.getZipcode());
        order.setAddress(request.getAddress());
        order.setDongho(request.getDongho());
        order.setPw(passwordEncoder.encode(request.getPw()));
        order.setStatus(OrderStatus.WAITING);
        order.setOrderDate(LocalDateTime.now());
        Long id = orderService.createOrderAndModifyStock(request.getItemId(), order);
        return new OrderDto(order);
    }

    /* 주문 생성 예외 처리 */
    @ExceptionHandler(NotEnoughStockException.class)
    public ResponseEntity<String> orderException(NotEnoughStockException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }

    /**
     * 주문 수정
     */
    @PatchMapping("/api/admin/orders/{id}")
    public UpdateOrderResponse updateOrder(
            @PathVariable("id") Long id,
            @RequestBody @Valid UpdateOrderRequest request) {
        orderService.update(id, request.getStatus());
        Order findOrder = orderService.findOne(id);
        return new UpdateOrderResponse(findOrder.getId());
    }


    /**
     * 개별 주문 조회
     */
    @PostMapping("/api/orders/detail")
    public List<OrderDto> getOrderByNumber(@RequestBody @Valid GetOrderRequest request) {
        List<Order> orders = orderService.findOrdersByNumber(request.getNumber());
        List<Order> matchingOrders = new ArrayList<>();

        for (Order order : orders) {
            if (passwordEncoder.matches(request.getPw(), order.getPw())) {
                matchingOrders.add(order);
            }
        }

        List<OrderDto> result = matchingOrders.stream()
                .map(o -> new OrderDto(o))
                .collect(Collectors.toList());
        return result;
    }

    /**
     * 전체 주문 조회
     */
    @GetMapping("/api/admin/orders")
    public List<OrderDto> orders() {
        List<Order> orders = orderService.findOrders();
        List<OrderDto> result = orders.stream()
                .map(o -> new OrderDto(o))
                .collect(Collectors.toList());
        return result;
    }

}
