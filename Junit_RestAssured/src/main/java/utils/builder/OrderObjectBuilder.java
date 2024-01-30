package utils.builder;

import dto.model.OrderModel;

import static utils.dataHelper.OrderDataHelper.*;
import static utils.dataHelper.PetDataHelper.VALID_STATUS;

/**
 * Вспомагательный класс для формирования ожидаемых результатов
 */
public class OrderObjectBuilder {

    /**
     * Метод формирования заказа
     *
     * @param id формируемого заказа
     * @return тело ответа
     */
    public static OrderModel getOrderModel(String id) {
        return OrderModel.builder()
                .id(id)
                .petId(VALID_PET_ID_FOR_STORE)
                .quantity(VALID_QUANTITY)
                .shipDate(VALID_SHIP_DATE)
                .status(VALID_STATUS)
                .complete(VALID_COMPLETE)
                .build();
    }
}
