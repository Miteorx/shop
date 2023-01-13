package com.example.shop;


import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.example.shop.model.Customer;
import com.example.shop.model.Order;
import com.example.shop.srevice.OrderServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.UnsupportedEncodingException;
import java.util.List;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

@SpringBootTest(
    webEnvironment = WebEnvironment.MOCK,
    classes = ShopApplication.class)
@AutoConfigureMockMvc
public class OrderServiceTests {

  @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private OrderServiceImpl orderService;


  @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
  @Autowired
  private ObjectMapper objectMapper;

  @Test
  public void getAllOrders() throws Exception {
    when(orderService.readAllOrders()).thenReturn(List.of(new Order(1L, "123",
        "Artem", new Customer(1L, "Artem", null))));

    mockMvc.perform(get("/order/getAll"))
        .andExpect(status().isOk())
        .andReturn();
  }

  @Test
  public void getOrderById() throws Exception {
    when(orderService.findOrderById(1)).thenReturn(new Order(1L, "123",
        "Artem", new Customer(1L, "Artem", null)));

    mockMvc.perform(get("/order/1"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.orderNumber", Matchers.equalTo("123")))
        .andExpect(jsonPath("$.orderName", Matchers.equalTo("Artem")))
        .andReturn();
  }

  @Test
  public void createOrder() throws Exception {

    String orderNumber = "15155";
    String orderName = "Sausages";
    String customerId = "1";

    when(orderService.saveOrder(any())).thenReturn(new Order(1L, orderNumber,
        orderName, new Customer(1L, "Artem", null)));

    String body =
        """
              {
                "number":"%s",
                "orderName":"%s",
                "customerId":"%s"
              }               
            """
            .formatted(orderNumber, orderName, customerId);

    MvcResult mvcResult = mockMvc.perform(post("/order/save")
            .contentType(MediaType.APPLICATION_JSON)
            .content(body)
        ).andExpect(status().isCreated())
        .andReturn();
    System.out.println();
    System.out.println(mvcResult.getResponse().getContentAsString());
    System.out.println();


  }

  @Test
  public void deleteOrder() throws Exception {
    when(orderService.deleteOrder(1)).thenReturn(true);

    MvcResult mvcResult = mockMvc.perform(post("/order/delete/1")
            .contentType(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isOk())
        .andReturn();

    System.out.println();
    System.out.println(mvcResult.getResponse().getContentAsString());
    System.out.println();

    boolean actual = Boolean.parseBoolean(mvcResult.getResponse().getContentAsString());
    assertTrue(actual);
  }

  @Test
  public void deleteNonexistentOrder() throws Exception {
    when(orderService.deleteOrder(1)).thenReturn(true);
    MvcResult mvcResult = mockMvc.perform(post("/order/delete/2")
            .contentType(MediaType.APPLICATION_JSON))
        .andExpect(status().isOk())
        .andReturn();

    System.out.println();
    System.out.println(mvcResult.getResponse().getContentAsString());
    System.out.println();

    boolean actual = Boolean.parseBoolean(mvcResult.getResponse().getContentAsString());

    assertFalse(actual);

  }

  private <T> T parseResponse(MvcResult mvcResult, Class<T> c) {
    try {
      return objectMapper.readValue(mvcResult.getResponse().getContentAsString(), c);
    } catch (JsonProcessingException | UnsupportedEncodingException e) {
      throw new RuntimeException("Error parsing json", e);
    }
  }

}
