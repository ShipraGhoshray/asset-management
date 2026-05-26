package com.selflearning.client;

import com.selflearning.dto.OrderStatusResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "order-service", url = "${order.service.url}")
public interface OrderServiceClient {

    @GetMapping("/api/orders/{orderId}/status")
    OrderStatusResponse getOrderStatus(@PathVariable("orderId") String orderId);
}