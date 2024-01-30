package steps;

import dto.model.PetModel;
import io.restassured.specification.RequestSpecification;
import utils.ResponseWrapper;

import static io.restassured.RestAssured.given;

/**
 * Класс с реализацией всех Rest шагов
 */
public class StepsPet {

    /**
     * Экземпляр спецификации RestAssured
     */
    private final RequestSpecification requestSpecification;

    /**
     * Часть URL для запросов /pet
     */
    private static final String PET_PATH = "pet";

    /**
     * Часть URL для запросов /pet/findByStatus
     */
    private static final String FIND_BY_STATUS_PATH = "pet/findByStatus";

    /**
     * Параметр status, GET запроса findByStatus
     */
    private static final String STATUS_PARAMETER = "status";


    /**
     * Конструктор для создания экземпляра класса
     *
     * @param requestSpecification спецификация RestAssured
     */
    public StepsPet(RequestSpecification requestSpecification) {
        this.requestSpecification = requestSpecification;
    }

    /**
     * Метод создания питомца
     *
     * @param request тело запроса
     * @return оболочка для работы с ответом
     */
    public ResponseWrapper createNewPetToStore(PetModel request) {
        return new ResponseWrapper(given(requestSpecification)
                .when()
                .body(request)
                .post(PET_PATH)
                .andReturn());
    }

    /**
     * Метод для поиска питомца по id
     *
     * @param id питомца
     * @return оболочка для работы с ответом
     */
    public ResponseWrapper findPetById(String id) {
        return new ResponseWrapper(given(requestSpecification)
                .when()
                .get(PET_PATH + "/" + id)
                .andReturn());
    }

    /**
     * Метод для удаления питомца по id
     *
     * @param id питомца
     * @return оболочка для работы с ответом
     */
    public ResponseWrapper deletePetById(String id) {
        return new ResponseWrapper(given(requestSpecification)
                .when()
                .delete(PET_PATH + "/" + id)
                .andReturn());
    }

    /**
     * Метод поиска питомцев по статусу
     *
     * @param status статус
     * @return оболочка для работы с ответом
     */
    public ResponseWrapper getPetByStatus(String status) {
        return new ResponseWrapper(given(requestSpecification)
                .when()
                .param(STATUS_PARAMETER, status)
                .get(FIND_BY_STATUS_PATH)
                .andReturn());
    }
}

