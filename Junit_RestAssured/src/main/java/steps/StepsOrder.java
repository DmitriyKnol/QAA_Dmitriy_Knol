package steps;

import dto.model.OrderModel;
import io.restassured.specification.RequestSpecification;
import utils.ResponseWrapper;

import static io.restassured.RestAssured.given;

/**
 * Класс с реализацией всех Rest шагов
 */
public class StepsOrder {

    /**
     * Экземпляр спецификации RestAssured
     */
    private final RequestSpecification requestSpecification;

    /**
     * Часть URL для запросов /store/order
     */
    private static final String STORE_ORDER_PATH = "store/order";

    /**
     * Конструктор для создания экземпляра класса
     *
     * @param requestSpecification спецификация RestAssured
     */
    public StepsOrder(RequestSpecification requestSpecification) {
        this.requestSpecification = requestSpecification;
    }

    /**
     * Метод создания заказа
     *
     * @param request тело запроса
     * @return оболочка для работы с ответом
     */
    public ResponseWrapper createNewOrder(OrderModel request) {
        return new ResponseWrapper(given(requestSpecification)
                .when()
                .body(request)
                .post(STORE_ORDER_PATH)
                .andReturn());
    }

    /**
     * Метод для поиска заказа по id
     *
     * @param id заказа
     * @return оболочка для работы с ответом
     */
    public ResponseWrapper findOrderById(String id) {
        return new ResponseWrapper(given(requestSpecification)
                .when()
                .get(STORE_ORDER_PATH + "/" + id)
                .andReturn());
    }

    /**
     * Метод для удаления заказа по id
     *
     * @param id тело запроса, для получения id заказа
     * @return оболочка для работы с ответом
     */
    public ResponseWrapper deleteOrderById(String id) {
        return new ResponseWrapper(given(requestSpecification)
                .when()
                .delete(STORE_ORDER_PATH + "/" + id)
                .andReturn());
    }

}

