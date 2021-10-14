package za.co.moitrack.data.model;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by nguni52 on 2017/03/24.
 */
@Data
@NoArgsConstructor
public class ServerResponse {
    private String message;
    private boolean status;
}
