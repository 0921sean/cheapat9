package project.cheap9.api;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import project.cheap9.domain.Item;
import project.cheap9.domain.Order;
import project.cheap9.domain.OrderStatus;
import project.cheap9.exception.NotEnoughStockException;
import project.cheap9.service.ItemService;
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
    private final ItemService itemService;
    private final PasswordEncoder passwordEncoder;

    /**
     * 주문 생성
     */
    @PostMapping("/api/orders")
    public OrderDto saveOrder(@RequestBody @Valid CreateOrderRequest request) {
        Item item = itemService.findOne(request.getItemId());

        Order order = new Order(); // orderPrice, (count), (status, orderDate) 값 없음
        order.setItem(item);
        order.setCount(request.getCount());
        order.setName(request.getName());
        order.setNumber(request.getNumber());
        order.setZipcode(request.getZipcode());
        order.setAddress(request.getAddress());
        order.setDongho(request.getDongho());
        order.setPw(passwordEncoder.encode(request.getPw()));
        Order.setBase(item, order);

        Long id = orderService.saveOrder(order);
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

//   + 필요한 재료들
    /**
     * 입력값
     */
    @Data
    static class CreateOrderRequest {
        private Long itemId;
        private int count;
        private String name;
        private String number;
        private String zipcode;
        private String address;
        private String dongho;
        private String pw;
    }

    @Data
    static class GetOrderRequest {
        private String number;
        private String pw;
    }

    @Data
    static class UpdateOrderRequest {
        private OrderStatus status;
    }

    /**
     * 출력값
     */
//    @Data
//    static class CreateOrderResponse {
//        private Long id;
//
//        public CreateOrderResponse(Long id) {
//            this.id = id;
//        }
//    }

    @Data
    static class UpdateOrderResponse {
        private Long id;

        public UpdateOrderResponse(Long id) {
            this.id = id;
        }
    }

    @Data
    static class OrderDto { // 출력될 요소들
        private Long orderId;
        private Item item;
        private int orderPrice;
        private int count;
        private String name;
        private String number;
        private String zipcode;
        private String address;
        private String dongho;
        private LocalDateTime orderDate;
        private OrderStatus orderStatus;

        public OrderDto(Order order) {
            orderId = order.getId();
            item = order.getItem();
            orderPrice = order.getOrderPrice();
            count = order.getCount();
            name = order.getName();
            number = order.getNumber();
            zipcode = order.getZipcode();
            address = order.getAddress();
            dongho = order.getDongho();
            orderDate = order.getOrderDate();
            orderStatus = order.getStatus();
        }
    }
}
