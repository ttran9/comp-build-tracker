package tran.compbuildbackend.domain.computerbuild;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "build_note")
public class BuildNote extends AbstractNote {

    public BuildNote() {
        super();
    }


}
