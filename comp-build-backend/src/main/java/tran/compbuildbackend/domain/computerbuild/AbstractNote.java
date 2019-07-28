package tran.compbuildbackend.domain.computerbuild;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@MappedSuperclass
public abstract class AbstractNote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @ManyToOne
    @JoinColumn(name = "computer_build_id", nullable = false, updatable = false)
    @JsonIgnore
    protected ComputerBuild computerBuild;

    @Min(1)
    @Max(3)
    protected int priority;

    @NotNull
    @Lob
    protected String description;

    @Column(name = "created_at")
    protected LocalDate createdAt;

    @Column(name = "updated_at")
    protected LocalDate updatedAt;

    @PrePersist
    public void onCreate() {
        this.createdAt = LocalDate.now();
    }

    @PostPersist
    public void onUpdate() {
        this.updatedAt = LocalDate.now();
    }

    public AbstractNote() { }

    public AbstractNote(@Min(1) @Max(3) int priority, @NotNull String description, LocalDate createdAt,
                        LocalDate updatedAt, ComputerBuild computerBuild) {
        this.priority = priority;
        this.description = description;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDate createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDate egetUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDate updatedAt) {
        this.updatedAt = updatedAt;
    }

    public ComputerBuild getComputerBuild() {
        return computerBuild;
    }

    public void setComputerBuild(ComputerBuild computerBuild) {
        this.computerBuild = computerBuild;
    }

    public LocalDate getUpdatedAt() {
        return updatedAt;
    }
}
