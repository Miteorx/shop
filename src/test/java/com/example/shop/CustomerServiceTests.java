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
import com.example.shop.srevice.CustomerServiceImpl;
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
public class CustomerServiceTests {

  @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private CustomerServiceImpl customerService;


  @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
  @Autowired
  private ObjectMapper objectMapper;

  @Test
  public void getAllOrders() throws Exception {
    when(customerService.readAllCustomers()).thenReturn(List.of(new Customer(1L, "Artem", null)));

    mockMvc.perform(get("/customer/getAll"))
        .andExpect(status().isOk())
        .andReturn();
  }

  @Test
  public void getOrderById() throws Exception {
    when(customerService.findCustomerById(1)).thenReturn(new Customer(1L, "Artem", null));

    mockMvc.perform(get("/customer/1"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.name", Matchers.equalTo("Artem")))
        .andReturn();
  }

  @Test
  public void createOrder() throws Exception {

    String name = "Artem";


    when(customerService.saveCustomer(any())).thenReturn(new Customer(1L, "Artem", null));

    String body =
        """
              {
                "name":"%s"
              }               
            """
            .formatted(name);

    MvcResult mvcResult = mockMvc.perform(post("/customer/save")
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
    when(customerService.deleteCustomer(1)).thenReturn(true);

    MvcResult mvcResult = mockMvc.perform(post("/customer/delete/1")
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
    when(customerService.deleteCustomer(1)).thenReturn(true);
    MvcResult mvcResult = mockMvc.perform(post("/customer/delete/2")
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
