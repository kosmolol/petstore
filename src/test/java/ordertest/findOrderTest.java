package ordertest;

import apipet.getOrder;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.ValidatableResponse;
import model.ResponseOrderDTO;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.lessThan;

public class findOrderTest {
    /*
    Тест на проверку ранее созданного заказа по номеру заказа
    Метод Метод GET /store/order/{orderid}
    Проверяется код ответа
    Боди ответа валидируется по json Схеме
    Проверка значений полей в боди ответа
    Проверка что запрос отработал менее чем за 2сек
     */

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
/*
    Метод Метод GET /store/order/{orderid}
    Проверка поиска несуществующего заказа
    Проверяется код ответа 404
    Проверка значений полей в боди ответа c ошибкой code,type,message
     Проверка что запрос отработал менее чем за 2сек
 */
    @Test
    @DisplayName("Поиск несуществующего заказа")
    public void getNotcreatedOrder(){
        getOrder GetOrder =  new getOrder();
        GetOrder.setOrderId(111);
        ValidatableResponse response = GetOrder.getOrder()
                .statusCode(HttpStatus.SC_NOT_FOUND)
                .body("code",equalTo(404))
                .body("type",equalTo("error"))
                .body("message", equalTo("Order not found"))
                .time(lessThan(2000l));  //Проверка что запрос отработает менее чем за 2секунды
    }


}
