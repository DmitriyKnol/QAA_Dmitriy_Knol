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
import static utils.builder.ErrorObjectBuilder.getErrorResponse;
import static utils.dataHelper.OrderDataHelper.*;
import static utils.builder.OrderObjectBuilder.getOrderModel;
import static utils.dataHelper.ResponseDataHelper.*;

/**
 * Тест сьют метода POST /store/order
 */
@Epic("Store контроллер")
@Feature("Add new order to store")
public class NewPlaceAnOrder extends BaseTestOrder {

    @Test
    @DisplayName("Add new order to store. Positive case")
    @Story("Добавление нового заказа, позитивный сценарий")
    public void testNewPlaceAnOrderPositive() {

        step("Создание тела запроса с валидным ID", () -> {
            request = getOrderModel(VALID_STORE_ID);
        });

        step("Выполнение запроса POST /store/order", () -> {
            responseWrapper = stepsOrder.createNewOrder(request);
        });

        step("Проверка результата", () -> {
            statusCode = responseWrapper.getStatusCode();
            response = responseWrapper.as(OrderModel.class);
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
    @DisplayName("Add new order to store. Negative case, bad id")
    @Story("Добавление нового заказа, негативный сценарий, некорректный id")
    public void testNewPlaceAnOrderNegative() {
        step("Создание тела запроса с невалидным ID", () -> {
            request = getOrderModel(INVALID_STORE_ID);
        });

        step("Выполнение запроса POST /store/order", () -> {
            responseWrapper = stepsOrder.createNewOrder(request);
        });
        step("Проверка результата", () -> {
            ErrorResponse error = responseWrapper.as(ErrorResponse.class);
            statusCode = responseWrapper.getStatusCode();
            assertSoftly(
                    softAssertions -> {
                        softAssertions
                                .assertThat(statusCode)
                                .withFailMessage("Status code doesn't match")
                                .isEqualTo(STATUS_CODE_ERROR_500);
                        softAssertions
                                .assertThat(error)
                                .withFailMessage("Response body doesn't match")
                                .isEqualTo(getErrorResponse(STATUS_CODE_ERROR_500, UNKNOWN_TYPE, UNKNOWN_MESSAGE));
                    }
            );
        });
    }

}
