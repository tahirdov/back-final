package app.container.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public enum OperationStatuses {
    FAIL("fail"),
    SUCCESS("success");

    private final String status;
}
