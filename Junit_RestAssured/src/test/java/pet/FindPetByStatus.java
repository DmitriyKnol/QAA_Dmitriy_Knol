package pet;

import baseTest.BaseTestPet;
import dto.model.PetModel;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import utils.ResponseWrapper;

import java.util.List;

import static io.qameta.allure.Allure.step;
import static org.assertj.core.api.SoftAssertions.assertSoftly;
import static utils.dataHelper.PetDataHelper.VALID_STATUS;
import static utils.dataHelper.PetDataHelper.getRandomNotValidStatus;
import static utils.dataHelper.ResponseDataHelper.STATUS_CODE_OK;

/**
 * Тест сьют метода GET /pet/findByStatus
 */
@Epic("Pet контроллер")
@Feature("Find pet by status")
public class FindPetByStatus extends BaseTestPet {

    @Test
    @DisplayName("Find pets by status. Positive case")
    @Story("Поиск питомцев по статусу, позитивный сценарий")
    public void testFindPetsByStatusPositive() {
        step("Выполнение запроса GET /pet", () -> {
            responseWrapper = stepsPet.getPetByStatus(VALID_STATUS);
            statusCode = responseWrapper.getStatusCode();
        });

        List<PetModel> response = responseWrapper.asList(PetModel[].class);
        assertSoftly(
                softAssertions -> {
                    softAssertions
                            .assertThat(statusCode)
                            .withFailMessage("Status code doesn't match")
                            .isEqualTo(STATUS_CODE_OK);
                    softAssertions
                            .assertThat(response)
                            .withFailMessage("Response body is empty")
                            .isNotEmpty();
                }
        );
    }

    @Test
    @DisplayName("Find pets by status. Negative case, if status is not existing")
    @Story("Поиск питомцев с несуществующим статусом, негативный сценарий")
    public void testFindPetsByStatusIfStatusNullNegative() {
        ResponseWrapper responseWrapper = stepsPet.getPetByStatus(getRandomNotValidStatus());

        int statusCode = responseWrapper.getStatusCode();
        List<PetModel> response = responseWrapper.asList(PetModel[].class);

        assertSoftly(
                softAssertions -> {
                    softAssertions
                            .assertThat(statusCode)
                            .withFailMessage("Status code doesn't match")
                            .isEqualTo(STATUS_CODE_OK);
                    softAssertions
                            .assertThat(response)
                            .withFailMessage("Response body is not empty")
                            .isEmpty();
                }
        );
    }
}
