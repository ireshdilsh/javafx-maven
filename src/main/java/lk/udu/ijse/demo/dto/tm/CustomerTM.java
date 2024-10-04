package lk.udu.ijse.demo.dto.tm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CustomerTM {
    private String custID;
    private String custName;
    private String custNIC;
    private String custEmail;
    private String custPhone;
}
