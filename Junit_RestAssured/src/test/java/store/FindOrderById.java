package store;

import baseTest.BaseTestOrder;
import dto.model.OrderModel;
import dto.response.ErrorResponse;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static io.qameta.allure.Allure.step;
import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static utils.dataHelper.OrderDataHelper.*;
import static utils.builder.ErrorObjectBuilder.getErrorResponse;
import static utils.builder.OrderObjectBuilder.getOrderModel;
import static utils.dataHelper.ResponseDataHelper.*;

/**
 * Тест сьют метода GET /store/order
 */
@Epic("Store контроллер")
@Feature("Find order by id")
public class FindOrderById extends BaseTestOrder {
    @Test
    @DisplayName("Find order by id. Positive case")
    @Story("Поиск заказа по Id. Позитивный сценарий")
    public void testFindOrderByIdPositive() {

        step("Создание тела запроса с валидным ID", () -> {
            request = getOrderModel(VALID_STORE_ID);
        });

        step("Выполнение запроса POST /store/order", () -> {
            responseWrapper = stepsOrder.createNewOrder(request);
        });
        step("Выполнение запроса GET /store/order", () -> {
            response = responseWrapper.as(OrderModel.class);
            responseWrapper = stepsOrder.findOrderById(response.getId());
        });
        step("Проверка результата", () -> {
            response = responseWrapper.as(OrderModel.class);
            statusCode = responseWrapper.getStatusCode();
            assertSoftly(
                    softAssertions -> {
                        softAssertions
                                .assertThat(statusCode)
                                .withFailMessage("Status code doesn't match")
                                .isEqualTo(STATUS_CODE_OK);
                        softAssertions
                                .assertThat(response)
                                .withFailMessage("Response body doesn't match")
                                .isEqualTo(request);
                    }
            );
        });
    }

    @Test
    @DisplayName("Find order by id. Order not found")
    @Story("Поиск заказа по Id. Заказ не найден")
    public void testFindOrderByIdNegative() {

        step("Выполнение запроса GET /store/order", () -> {
            responseWrapper = stepsOrder.findOrderById(VALID_STORE_ID);
        });
        step("Проверка результата", () -> {
            statusCode = responseWrapper.getStatusCode();
            ErrorResponse error = responseWrapper.as(ErrorResponse.class);
            assertSoftly(
                    softAssertions -> {
                        softAssertions
                                .assertThat(statusCode)
                                .withFailMessage("Status code doesn't match")
                                .isEqualTo(STATUS_CODE_ERROR_404);
                        softAssertions
                                .assertThat(error)
                                .withFailMessage("Response body doesn't match")
                                .isEqualTo(getErrorResponse(NOT_FOUND_CODE, ERROR_TYPE, ORDER_NOT_FOUND_MESSAGE));
                    }
            );
        });
    }
}
