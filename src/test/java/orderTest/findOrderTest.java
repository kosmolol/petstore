package orderTest;

import apiPet.getOrder;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.ValidatableResponse;
import model.ResponseOrderDTO;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import model.OrderDTO;
import  apiPet.getOrder;

import static org.hamcrest.Matchers.lessThan;

public class findOrderTest {
    @Test
    @DisplayName("Получение заказа по orderId")
    public void getOrder(){
        getOrder GetOrder =  new getOrder();
        GetOrder.setOrderId(5);
        ValidatableResponse response = GetOrder.getOrder()
                .statusCode(HttpStatus.SC_OK)
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

    @Test
    @DisplayName("Поиск несуществующего заказа")
    public void getNotcreatedOrder(){
        getOrder GetOrder =  new getOrder();
        GetOrder.setOrderId(11);
        ValidatableResponse response = GetOrder.getOrder()
                .statusCode(HttpStatus.SC_NOT_FOUND)
                .time(lessThan(2000l));  //Проверка что запрос отработает менее чем за 2секунды
    }

}
