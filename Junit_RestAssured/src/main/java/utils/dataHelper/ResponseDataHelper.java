package utils.dataHelper;

/**
 * Вспомогательный класс для генерации данных о кодах ответа
 * и о возможных сообщениях в ошибках
 */
public class ResponseDataHelper {

    /**
     * Статус код успешного результата запроса
     */
    public static final int STATUS_CODE_OK = 200;

    /**
     * Статус код ошибки 404
     */
    public static final int STATUS_CODE_ERROR_404 = 404;

    /**
     * Статус код ошибки 500
     */
    public static final int STATUS_CODE_ERROR_500 = 500;

    /**
     * Код ошибки, если заказ или питомец не найден
     */
    public static final int NOT_FOUND_CODE = 1;

    /**
     * Тип ошибки, если заказ или питомец не найден
     */
    public static final String ERROR_TYPE = "error";

    /**
     * Сообщение ошибки, если заказ или питомец не найден
     */
    public static final String ORDER_NOT_FOUND_MESSAGE = "Order not found";
    /**
     * Сообщение ошибки, если заказ не найден при попытке удаления
     */
    public static final String DELETED_ORDER_NOT_FOUND_MESSAGE = "Order Not Found";

    /**
     * Тип неизвестной ошибки
     */
    public static final String UNKNOWN_TYPE = "unknown";

    /**
     * Сообщение неизвестной ошибки
     */
    public static final String UNKNOWN_MESSAGE = "something bad happened";

    /**
     * Сообщение ошибки, если питомец не найден
     */
    public static final String PET_NOT_FOUND_MESSAGE = "Pet not found";
}
