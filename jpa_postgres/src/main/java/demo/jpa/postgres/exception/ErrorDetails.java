package demo.jpa.postgres.exception;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ErrorDetails {
    public Date date;
    public String message;
    public String details;
}
