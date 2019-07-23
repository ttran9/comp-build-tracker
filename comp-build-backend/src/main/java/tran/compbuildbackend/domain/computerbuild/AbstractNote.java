package tran.compbuildbackend.domain.computerbuild;

import tran.compbuildbackend.domain.computerbuild.ComputerBuild;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.Date;

@MappedSuperclass
public abstract class AbstractNote {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @ManyToOne
    @JoinColumn(name = "computer_build_id", nullable = false, updatable = false)
    private ComputerBuild computerBuild;

    @Min(1)
    @Max(3)
    protected int priority;

    @NotNull
    @Lob
    protected String description;

    @Column(name = "created_at")
    protected Date createdAt;

    @Column(name = "updated_at")
    protected Date updatedAt;

    @PrePersist
    public void onCreate() {
        this.createdAt = new Date();
    }

    @PostPersist
    public void onUpdate() {
        this.updatedAt = new Date();
    }

    public AbstractNote() { }

    public AbstractNote(@Min(1) @Max(3) int priority, @NotNull String description, Date createdAt,
                        Date updatedAt, ComputerBuild computerBuild) {
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

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }
}
