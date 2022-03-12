package app.container.dto.response;

import app.container.enums.OperationStatuses;
import app.container.enums.OperationTypes;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResponseDto {
    private UUID user_id;
    private OperationTypes operation_type;
    private OperationStatuses operation_status;
}
