package lk.udu.ijse.demo.dto.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ItemTM {
    private String itemID;
    private String name;
    private Integer qty;
    private Double price;
}
