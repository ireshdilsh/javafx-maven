package lk.udu.ijse.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ItemDto {
    private String itemID;
    private String name;
    private Integer qty;
    private Double price;
}
