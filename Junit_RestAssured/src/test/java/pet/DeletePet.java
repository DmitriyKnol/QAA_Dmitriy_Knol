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
 * Тест сьют метода DELETE /pet
 */
@Epic("Pet контроллер")
@Feature("Delete pet by id")
public class DeletePet extends BaseTestPet {

    @Test
    @DisplayName("Delete pet by id. Positive case")
    @Story("Удаление питомца по Id. Позитивный сценарий")
    public void testDeletePetPositive() {

        step("Создание тела запроса с валидным ID", () -> {
            request = getPetModel(VALID_PET_ID);
        });

        step("Выполнение запроса POST /pet", () -> {
            responseWrapper = stepsPet.createNewPetToStore(request);
            response = responseWrapper.as(PetModel.class);
            deleteId = response.getId();
        });

        step("Выполнение запроса DELETE /pet", () -> {
            responseWrapper = stepsPet.deletePetById(deleteId);
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
    @DisplayName("Delete pet by id. Order not found")
    @Story("Удаление питомца по Id. Заказ не найден")
    public void testDeletePetNegative() {
        step("Выполнение запроса DELETE /pet", () -> {
            responseWrapper = stepsPet.deletePetById(VALID_PET_ID);
        });
        step("Проверка результата", () -> {
            statusCode = responseWrapper.getStatusCode();
            assertSoftly(
                    softAssertions -> {
                        softAssertions
                                .assertThat(statusCode)
                                .withFailMessage("Status code doesn't match")
                                .isEqualTo(STATUS_CODE_ERROR_404);
                    }
            );
        });
    }
}
