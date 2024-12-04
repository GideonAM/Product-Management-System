package com.amalitech.product_management_system.orderItem;

import com.amalitech.product_management_system.exception.OperationNotPermitted;
import com.amalitech.product_management_system.order.Order;
import com.amalitech.product_management_system.product.Product;
import com.amalitech.product_management_system.product.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderItemService {

    private final OrderItemRepository orderItemRepository;
    private final ProductRepository productRepository;

    public void saveOrderItems(List<OrderItemDto> orderItemDtoList, Order order) {
        List<String> productIds = orderItemDtoList.stream().map(OrderItemDto::productId).toList();
        List<Product> products = productRepository.findAllById(productIds);

        if (products.size() != productIds.size())
            throw new OperationNotPermitted("Failed to place order. Some of the purchased products do not exist");

        products.sort(Comparator.comparing(Product::getId));
        orderItemDtoList.sort(Comparator.comparing(OrderItemDto::productId));

        /* get a product to be purchased and decreases it available
            stock by the quantity being purchased */
        for (int i = 0; i < products.size(); i++) {
            OrderItemDto item = orderItemDtoList.get(i);
            Product product = products.get(i);

            if (item.quantity() > product.getQuantityInStock())
                throw new OperationNotPermitted("Order failed, some of the selected items are out of stock");

            product.setQuantityInStock(product.getQuantityInStock() - item.quantity());
            productRepository.save(product);

            OrderItem orderItem = OrderItem.builder()
                    .productId(item.productId())
                    .productPrice(item.productPrice())
                    ._order(order)
                    .productQuantity(item.quantity())
                    .build();

            orderItemRepository.save(orderItem);
        }
    }

}
