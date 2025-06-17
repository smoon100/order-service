package com.codedecode.order.controller;

import com.codedecode.order.dto.FoodItemDTO;
import com.codedecode.order.dto.OrderDTO;
import com.codedecode.order.dto.OrderDTOFromFE;
import com.codedecode.order.dto.RestaurantDTO;
import com.codedecode.order.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class OrderControllerTest {
    @Mock
    private OrderService orderService;

    @InjectMocks
    private OrderController orderController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void saveOrder_shouldReturnCreatedStatus() {
        // Arrange
        OrderDTOFromFE orderDetails = new OrderDTOFromFE();
        orderDetails.setFoodItemsList( Arrays.asList(
                new FoodItemDTO( 1, "Item 1", "Description 1", true, 10.0, 1, 1 ),
                new FoodItemDTO( 1, "Item 1", "Description 1", true, 10.0, 1, 1 )
                )
        );
        orderDetails.setUserId(1);
        orderDetails.setRestaurant(new RestaurantDTO(1, "Restaurant 1", "Address 1", "City 1", "Description 1"));

        OrderDTO orderSavedInDB = new OrderDTO();
        orderSavedInDB.setOrderId(1);
        orderSavedInDB.setFoodItemsList(
                Arrays.asList(
                        new FoodItemDTO( 1, "Item 1", "Description 1", true, 10.0, 1, 1 ),
                        new FoodItemDTO( 1, "Item 1", "Description 1", true, 10.0, 1, 1 )
                )
        );
        orderSavedInDB.setRestaurantDTO(new RestaurantDTO(1, "Restaurant 1", "Address 1", "City 1", "Description 1"));

        when(orderService.saveOrderInDB(orderDetails)).thenReturn(orderSavedInDB);

        // Act
        ResponseEntity<OrderDTO> response = orderController.saveOrder(orderDetails);

        // Assert
        verify(orderService, times(1)).saveOrderInDB(orderDetails);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(orderSavedInDB, response.getBody());
    }
    }
