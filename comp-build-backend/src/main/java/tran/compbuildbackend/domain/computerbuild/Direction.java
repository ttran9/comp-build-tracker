package tran.compbuildbackend.domain.computerbuild;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="direction")
public class Direction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="computer_build_id", updatable = false, nullable = false)
    private ComputerBuild computerBuild;

    @NotNull
    @Lob
    private String description;

    @Column(name="direction_number")
    private int directionNumber;

    public Direction() { }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public ComputerBuild getComputerBuild() {
        return computerBuild;
    }

    public void setComputerBuild(ComputerBuild computerBuild) {
        this.computerBuild = computerBuild;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getDirectionNumber() {
        return directionNumber;
    }

    public void setDirectionNumber(int directionNumber) {
        this.directionNumber = directionNumber;
    }

}