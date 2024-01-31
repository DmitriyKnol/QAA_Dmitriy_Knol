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
import static utils.dataHelper.PetDataHelper.NOT_VALID_PET_ID;
import static utils.dataHelper.PetDataHelper.VALID_PET_ID;
import static utils.dataHelper.ResponseDataHelper.*;

/**
 * Тест сьют метода POST /pet
 */
@Epic("Pet контроллер")
@Feature("Add new pet to store")
public class AddNewPetToStore extends BaseTestPet {

    @Test
    @DisplayName("Add new pet to store. Positive case")
    @Story("Добавление нового питомца, позитивный сценарий")
    public void testAddNewPetToStorePositive() {

        step("Создание тела запроса с валидным ID", () -> {
            request = getPetModel(VALID_PET_ID);
        });

        step("Выполнение запроса POST /pet", () -> {
            responseWrapper = stepsPet.createNewPetToStore(request);
        });

        step("Проверка результата", () -> {
            statusCode = responseWrapper.getStatusCode();
            response = responseWrapper.as(PetModel.class);

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
    @DisplayName("Add new pet to store. Negative case with not valid ID")
    @Story("Добавление нового питомца с не валидным ID, негативный сценарий")
    public void testAddNewPetToStoreNegative() {
        step("Создание тела запроса с не валидным ID", () -> {
            request = getPetModel(NOT_VALID_PET_ID);
        });

        step("Выполнение запроса POST /pet", () -> {
            responseWrapper = stepsPet.createNewPetToStore(request);
        });

        step("Проверка результата", () -> {
            int statusCode = responseWrapper.getStatusCode();
            ErrorResponse error = responseWrapper.as(ErrorResponse.class);

            assertSoftly(
                    softAssertions -> {
                        softAssertions
                                .assertThat(statusCode)
                                .withFailMessage("Status code doesn't match")
                                .isEqualTo(STATUS_CODE_ERROR_500);
                        softAssertions
                                .assertThat(error)
                                .withFailMessage("Error body doesn't match")
                                .isEqualTo(getErrorResponse(STATUS_CODE_ERROR_500, UNKNOWN_TYPE, UNKNOWN_MESSAGE));
                    }
            );
        });
    }
}
