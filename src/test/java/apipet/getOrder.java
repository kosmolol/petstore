package apipet;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;
public class getOrder {
    private static final String BASE_URL = "https://petstore.swagger.io/v2";
    private static final String getORDER = "/store/order/{id}";
    private RequestSpecification getspecOrder;
    private int orderId;

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public getOrder() {
        getspecOrder = given()
                .baseUri(BASE_URL)
                .contentType(ContentType.JSON);
    }

    public  ValidatableResponse getOrder() {

        return given(getspecOrder)
                .log().all()
                .pathParams("id",orderId)
                .when()
                .get(getORDER)
                .then()
                .log().all();
    }

}
