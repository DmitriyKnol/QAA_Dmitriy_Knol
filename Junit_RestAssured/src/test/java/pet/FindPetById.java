package pet;

import baseTest.BaseTestPet;
import dto.model.PetModel;
import dto.response.ErrorResponse;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.qameta.allure.Allure.step;
import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static utils.builder.ErrorObjectBuilder.getErrorResponse;
import static utils.builder.PetObjectBuilder.getPetModel;
import static utils.dataHelper.PetDataHelper.VALID_PET_ID;
import static utils.dataHelper.ResponseDataHelper.*;

/**
 * Тест сьют метода GET /pet
 */
@Epic("Pet контроллер")
@Feature("Find pet by id")
public class FindPetById extends BaseTestPet {
    @Test
    @DisplayName("Find pet by id. Positive case")
    @Story("Поиск итомца по Id. Позитивный сценарий")
    public void testFindPetByIdPositive() {

        step("Создание тела запроса с валидным ID", () -> {
            request = getPetModel(VALID_PET_ID);
        });

        step("Выполнение запроса POST /pet", () -> {
            responseWrapper = stepsPet.createNewPetToStore(request);
        });

        step("Выполнение запроса GET /pet", () -> {
            response = responseWrapper.as(PetModel.class);
            responseWrapper = stepsPet.findPetById(response.getId());
        });

        step("Проверка результата", () -> {
            response = responseWrapper.as(PetModel.class);
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
    public void testFindPetByIdNegative() {
        step("Выполнение запроса GET /pet", () -> {
            responseWrapper = stepsPet.findPetById(VALID_PET_ID);
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
                                .isEqualTo(getErrorResponse(NOT_FOUND_CODE, ERROR_TYPE, PET_NOT_FOUND_MESSAGE));
                    }
            );
        });
    }
}

