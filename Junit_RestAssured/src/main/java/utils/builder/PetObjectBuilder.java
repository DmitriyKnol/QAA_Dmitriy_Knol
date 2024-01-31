package utils.builder;

import dto.model.PetModel;

import java.util.List;

import static utils.dataHelper.PetDataHelper.*;

/**
 * Вспомагательный класс для формирования ожидаемых результатов
 */
public class PetObjectBuilder {
    /**
     * Метод для формирования тела запроса создания питомца
     *
     * @param id идентификатор питомца
     * @return тело запроса
     */
    public static PetModel getPetModel(String id) {
        return PetModel.builder()
                .id(id)
                .category(PetModel.CategoryAndTagsItem.builder()
                        .id(VALID_CATEGORY_ID)
                        .name(getRandomCategoryName())
                        .build())
                .name(getRandomPetName())
                .photoUrls(List.of(getRandomUrl()))
                .tags(List.of(PetModel.CategoryAndTagsItem.builder()
                        .id(VALID_TAG_ID)
                        .name(VALID_TAG_NAME)
                        .build()))
                .status(VALID_STATUS)
                .build();
    }
}
