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
 * Тест сьют метода DELETE /store/order
 */
@Epic("Store контроллер")
@Feature("Delete order by id")
public class DeleteOrder extends BaseTestOrder {

    @Test
    @DisplayName("Delete order by id. Positive case")
    @Story("Удаление заказа по Id. Позитивный сценарий")
    public void testDeleteOrderPositive() {

        step("Создание тела запроса с валидным ID", () -> {
            request = getOrderModel(VALID_STORE_ID);
        });

        step("Выполнение запроса POST /store/order", () -> {
            responseWrapper = stepsOrder.createNewOrder(request);
            response = responseWrapper.as(OrderModel.class);
            deleteId = response.getId();
        });

        step("Выполнение запроса DELETE /store/order", () -> {
            responseWrapper = stepsOrder.deleteOrderById(deleteId);
        });

        step("Проверка результата", () -> {
            ErrorResponse response = responseWrapper.as(ErrorResponse.class);
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
                                .isEqualTo(getErrorResponse(STATUS_CODE_OK, UNKNOWN_TYPE, deleteId));
                    }
            );
        });
    }

    @Test
    @DisplayName("Delete order by id. Order not found")
    @Story("Удаление заказа по Id. Заказ не найден")
    public void testDeleteOrderNegative() {
        step("Выполнение запроса DELETE /store/order", () -> {
            responseWrapper = stepsOrder.deleteOrderById(VALID_STORE_ID);
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
                                .isEqualTo(getErrorResponse(STATUS_CODE_ERROR_404, UNKNOWN_TYPE, DELETED_ORDER_NOT_FOUND_MESSAGE));
                    }
            );
        });
    }
}
