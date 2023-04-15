
package model;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderDTO {


    private Boolean complete;

    private int id;

    private int petId;

    private int quantity;

    private String shipDate;

    private String status;

    public Boolean getComplete() {
        return complete;
    }


}
