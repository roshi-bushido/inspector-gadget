package error

import dto.api.ErrorDTO

/**
 * Created by IntelliJ IDEA.
 *
 */
public enum ErrorCode {
    E00("E00", "json-syntax-error"),
    E01("E01", "null-param"),
    E02("E02", "object-not-found"),
    E03("E03", "invalid-id"),

    E100("", "");

    public String code
    public String message

    public ErrorCode(String code, String message) {
        this.code = code
        this.message = message
    }

    public ErrorDTO toDTO() {
        return new ErrorDTO(code: this.code, message: this.message);
    }

}
