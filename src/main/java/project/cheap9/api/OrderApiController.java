package project.cheap9.api;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import project.cheap9.domain.Item;
import project.cheap9.domain.Order;
import project.cheap9.domain.OrderStatus;
import project.cheap9.repository.OrderRepository;
import project.cheap9.service.ItemService;
import project.cheap9.service.OrderService;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
public class OrderApiController {

    private final OrderService orderService;
    private final OrderRepository orderRepository;
    private final ItemService itemService;

    /**
     * 주문 생성
     */
    @PostMapping("/api/orders")
    public CreateOrderResponse saveOrder(@RequestBody @Valid CreateOrderRequest request) {
        Item item = itemService.findOne(request.getItemId());

        Order order = new Order(); // orderPrice, (count), (status, orderDate) 값 없음
        order.setItem(item);
        order.setCount(request.getCount());
        order.setName(request.getName());
        order.setNumber(request.getNumber());
        order.setZipcode(request.getZipcode());
        order.setDongho(request.getDongho());
        order.setPw(request.getPw());
        Order.setBase(item, order);

        Long id = orderService.saveOrder(order);
        return new CreateOrderResponse(id);
    }

    /**
     * 주문 수정
     */
    @PutMapping("/api/orders/{id}")
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
        List<Order> orders = orderRepository.findAllByNumber(request.getNumber(), request.getPw());

        List<OrderDto> result = orders.stream()
                .map(o -> new OrderDto(o))
                .collect(Collectors.toList());
        return result;
    }

    /**
     * 전체 주문 조회
     */
    @GetMapping("/api/orders")
    public List<OrderDto> orders() {
        List<Order> orders = orderRepository.findAllWith();
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
    @Data
    static class CreateOrderResponse {
        private Long id;

        public CreateOrderResponse(Long id) {
            this.id = id;
        }
    }

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
        private String dongho;
//        private String pw;
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
            dongho = order.getDongho();
//            pw = order.getPw();
            orderDate = order.getOrderDate();
            orderStatus = order.getStatus();
        }
    }
}
