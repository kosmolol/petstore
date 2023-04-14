package specification;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.apache.http.HttpStatus;

import static io.restassured.http.ContentType.JSON;
import static java.util.concurrent.TimeUnit.SECONDS;
import static org.hamcrest.Matchers.lessThanOrEqualTo;

import io.restassured.specification.RequestSpecification;

public class Specifications {
    public static RequestSpecification requestSpecification(){
        return new RequestSpecBuilder()
                .setBaseUri("https://petstore.swagger.io/v2")//---> Cтартовая URL
                .setRelaxedHTTPSValidation()//---> Отключение проверки сертификата
                .setContentType(JSON)//---> Установка Content Type
                .setAccept(JSON)//---> Установка Accept
                .build();
    }

    public static ResponseSpecification responseSpecificationScOk() {
        return new ResponseSpecBuilder()

                .log(LogDetail.ALL)//---> Уровень логирования
                .expectContentType(JSON)//---> Ожидаемый Content Type
                .expectStatusCode(HttpStatus.SC_OK)//---> Ожидаемый Status Code
                .expectResponseTime(lessThanOrEqualTo(2L), SECONDS)//---> Ожидаемое время ответа максимум 2 секунды
                .build();
    }
}
