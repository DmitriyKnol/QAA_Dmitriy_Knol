package utils.builder;

import dto.response.ErrorResponse;

/**
 * Вспомагательный класс для формирования ожидаемых результатов
 */

public class ErrorObjectBuilder {

    /**
     * Метод формирования ожидаемого результата ошибки
     *
     * @return тело ошибки
     */
    public static ErrorResponse getErrorResponse(Integer code, String type, String message) {
        return ErrorResponse.builder()
                .code(code)
                .type(type)
                .message(message)
                .build();
    }

}