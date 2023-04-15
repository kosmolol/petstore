package apiPet;

import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import model.OrderDTO;

import static io.restassured.RestAssured.given;

public class postOrder {

    private static final String BASE_URL = "https://petstore.swagger.io/v2";
    private static final String ORDER = "/store/order";
    private RequestSpecification specOrder;

    public postOrder() {
        specOrder = given()
                .baseUri(BASE_URL)
                .contentType(ContentType.JSON);
    }

    public  ValidatableResponse createOrder(OrderDTO OrderDTO) {

        return given(specOrder)
                .log().all()
                .body(OrderDTO)
                .when()
                .post(ORDER)
                .then()
                .log().all();
    }

}
