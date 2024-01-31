package dto.model;

import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Builder
@EqualsAndHashCode(exclude = {"id"})
public class OrderModel {
    private final String id;
    private final int petId;
    private final int quantity;
    private final String shipDate;
    private final String status;
    private final boolean complete;

}
