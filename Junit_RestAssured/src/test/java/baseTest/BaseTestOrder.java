package baseTest;

import config.BaseConfig;
import dto.model.OrderModel;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.aeonbits.owner.ConfigFactory;
import steps.StepsOrder;
import utils.ResponseWrapper;

/**
 * Базовый тестовый класс с общими настройками для тестирования заказов
 */
public class BaseTestOrder {

    /**
     * Поле для хранения statusCode
     */
    protected int statusCode;

    /**
     * Поле для хранения id, для удаления заказа
     */
    protected String deleteId;

    /**
     * Экземпляр интерфейса с конфигурацией
     */
    private final BaseConfig config = ConfigFactory.create(BaseConfig.class, System.getenv());

    /**
     * Экземпляр класса с REST шагами
     */
    protected final StepsOrder stepsOrder = new StepsOrder(getRequestSpecification());

    /**
     * Экземпляр класса с телом запроса заказа
     */
    protected OrderModel request;

    /**
     * Экземпляр класса с телом ответа заказа
     */
    protected OrderModel response;
    /**
     * Экземпляр класса с оболочкой ответа
     */
    protected ResponseWrapper responseWrapper;

    /**
     * Метод для получения спецификации RestAssured
     * <p>
     * baseUrl - базовый URL
     * contentType - параметр в header со значением JSON
     * accept - параметр в header со значением JSON
     * filter - создает фильтр для allure
     * log - логирование всех деталей
     *
     * @return спецификация
     */
    protected RequestSpecification getRequestSpecification() {
        return new RequestSpecBuilder()
                .setBaseUri(config.baseUrl())
                .setContentType(ContentType.JSON)
                .setAccept(ContentType.JSON)
                .addFilter(new AllureRestAssured())
                .log(LogDetail.ALL)
                .build();
    }
}