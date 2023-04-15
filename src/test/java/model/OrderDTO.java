
package model;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDTO {


    private Boolean complete;

    private Long id;

    private Long petId;

    private Long quantity;

    private String shipDate;

    private String status;

    public Boolean getComplete() {
        return complete;
    }


}
