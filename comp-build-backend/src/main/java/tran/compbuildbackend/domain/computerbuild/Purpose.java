package tran.compbuildbackend.domain.computerbuild;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "purpose")
public class Purpose extends AbstractNote {

    public Purpose() {
        super();
    }
}
