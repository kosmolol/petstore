package ordertest;

import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.ValidatableResponse;
import model.ResponseOrderDTO;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import model.OrderDTO;
import apipet.postOrder;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.lessThan;


public class CreateOrderTest {

    /*
    Проверка создания Заказа . Метод POST /store/order с максимальным набором полей
    Проверка кода ответа
    Проверка валидации по схеме JSON
    Провекра параметров в ответе
     Проверка что запрос отработал менее чем за 2сек

     */
    @Test
    @DisplayName("Проверка создания нового заказа с максимальным набором полей")
    public void createOrder(){
        postOrder PostOrder =  new postOrder();

        OrderDTO order = OrderDTO.builder()
                .id(5)
                .petId(1)
                .quantity(1)
                .shipDate("2023-04-13")
                .status("placed")
                .complete(true)
                .build();

        ValidatableResponse response = PostOrder.createOrder(order)
                .statusCode(HttpStatus.SC_OK) //Проверка Кода ответа 200
                .body(JsonSchemaValidator.matchesJsonSchemaInClasspath("orderResponse.json")) //Проверка Валидации по Схеме
                .time(lessThan(2000l));  //Проверка что запрос отработает менее чем за 2секунды

        ResponseOrderDTO orderResponse = response.extract().body().as(ResponseOrderDTO.class);

        Assertions.assertAll(
                ()-> Assertions.assertEquals(5,orderResponse.getId(), "Incorrect ID"), //Проверка ID в запросе
                ()-> Assertions.assertEquals(1,orderResponse.getPetId(),"Incorrect petID"), //Проверка petID в запросе
                ()-> Assertions.assertEquals(1,orderResponse.getQuantity(),"Incorrect Quantity"), //Проверка Quantity в запросе
                ()-> Assertions.assertEquals("2023-04-13T00:00:00.000+0000",orderResponse.getShipDate(), "Incorrect ShipDate"), //Проверка ShipDate в запросе
                ()-> Assertions.assertEquals("placed",orderResponse.getStatus(), "Incorrect Status"), //Проверка Status в запросе
                ()-> Assertions.assertEquals(true , orderResponse.isComplete(), "Incorrect Complete") //Проверка Complete в запросе
        );

    }
/*
Проверка ошибки при создании заказа с некорректной датой .
Метод POST /store/order дата не в формате даты
Проверка кода ошибки 500
Проверка боди ответа с ошибкой
 Проверка что запрос отработал менее чем за 2сек

 */
    @Test
    @DisplayName("Проверка создания нового заказа с некорреткной датой")
    public void createOrderWhitIncorrectDate(){
        postOrder PostOrder =  new postOrder();

        OrderDTO order = OrderDTO.builder()
                .id(2)
                .petId(1)
                .quantity(1)
                .shipDate("rrr")
                .status("placed")
                .complete(true)
                .build();
        ValidatableResponse response = PostOrder.createOrder(order)
                .statusCode(HttpStatus.SC_INTERNAL_SERVER_ERROR) //Проверка Кода ответа 200
                .body("code",equalTo(500))
                .body("message", equalTo("something bad happened"))
                .time(lessThan(2000l));  //Проверка что запрос отработает менее чем за 2секунды
    }
/*
   Проверка создания Заказа . Метод POST /store/order с минимальным набором полей
    Проверка кода ответа
    Провекра параметров в ответе
     Проверка что запрос отработал менее чем за 2сек
 */
    @Test
    @DisplayName("Проверка создания нового заказа с минимальным набором параметров")
    public void createOrderWhithMinParametr(){
        postOrder PostOrder =  new postOrder();
        OrderDTO order = OrderDTO.builder()
                .petId(2)
                .quantity(2)
                .build();
        ValidatableResponse response = PostOrder.createOrder(order)
                .statusCode(HttpStatus.SC_OK) //Проверка Кода ответа 200
                .time(lessThan(2000l));  //Проверка что запрос отработает менее чем за 2секунды
        ResponseOrderDTO orderResponse = response.extract().body().as(ResponseOrderDTO.class);

        Assertions.assertAll(
                ()-> Assertions.assertEquals(2,orderResponse.getPetId(),"Incorrect petID"), //Проверка petID в запросе
                ()-> Assertions.assertEquals(2,orderResponse.getQuantity(),"Incorrect Quantity"), //Проверка Quantity в запросе
                ()-> Assertions.assertNotNull(orderResponse.getId(),"OrderID NULL"),
                ()-> Assertions.assertEquals(false , orderResponse.isComplete(), "Incorrect Complete") //Проверка Complete в запросе
        );
    }

}
