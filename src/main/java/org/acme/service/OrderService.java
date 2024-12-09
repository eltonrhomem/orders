package org.acme.service;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.NotFoundException;
import org.acme.client.CustomerClient;
import org.acme.client.ProductClient;
import org.acme.dto.CustomerDTO;
import org.acme.dto.OrderDTO;
import org.acme.entity.OrderEntity;
import org.acme.repository.OrderRepository;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class OrderService {
    @Inject
    @RestClient
    CustomerClient customerClient;

    @Inject
    @RestClient
    private ProductClient productClient;

    @Inject
    private OrderRepository orderRepository;

    public List<OrderDTO> getAllOrders() {
        List<OrderDTO> orders = new ArrayList<>();
        orderRepository.findAll().stream().forEach(item -> {
            orders.add(mapEntityToDTO(item));
        });
        return orders;
    }



    public void saveNewOrder(OrderDTO orderDTO) {
        CustomerDTO customerDTO = customerClient.getCustomerById(orderDTO.getCustomerId());


        if(
            customerDTO.getName().equals(orderDTO.getCustomerName())
            && productClient.getProductById(orderDTO.getProductId()) != null
        ) {
            orderRepository.persist(mapDTOToEntity(orderDTO));
        } else {
            throw new NotFoundException();
        }
    }

    private OrderDTO mapEntityToDTO(OrderEntity orderEntity) {
        OrderDTO orderDTO = new OrderDTO();

        orderDTO.setOrderValue(orderEntity.getOrderValue());
        orderDTO.setCustomerId(orderEntity.getCustomerId());
        orderDTO.setCustomerName(orderEntity.getCustomerName());
        orderDTO.setProductId(orderEntity.getProductId());

        return orderDTO;
    }

    private OrderEntity mapDTOToEntity(OrderDTO orderDTO) {
        OrderEntity orderEntity = new OrderEntity();

        orderEntity.setOrderValue(orderDTO.getOrderValue());
        orderEntity.setCustomerName(orderDTO.getCustomerName());
        orderEntity.setProductId(orderDTO.getProductId());
        orderEntity.setCustomerId(orderDTO.getCustomerId());

        return orderEntity;

    }
}
