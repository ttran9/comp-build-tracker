package tran.compbuildbackend.domain.computerbuild;

import com.fasterxml.jackson.annotation.JsonIgnore;
import tran.compbuildbackend.domain.user.ApplicationUser;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Entity
@Table(name="computer_build")
public class ComputerBuild {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min=2, max=20)
    @Column(updatable = false)
    private String name;

    @NotBlank
    @Column(name="build_description")
    private String buildDescription;

    @ManyToOne(fetch = FetchType.EAGER)
    @JsonIgnore
    private ApplicationUser user;

    @Column(name="created_at")
    private Date createdAt;

    @Column(name="updated_at")
    private Date updatedAt;

    @Column(name="directions_count")
    private int numberOfDirections = 0;

    // a list of directions to complete this build.
    @OneToMany(cascade = CascadeType.REFRESH, orphanRemoval = true, mappedBy = "computerBuild", fetch = FetchType.LAZY)
    private List<Direction> directions = new LinkedList<>();

    // a list of notes for when the computer was being built.
    @OneToMany(cascade = CascadeType.REFRESH, orphanRemoval = true, mappedBy = "computerBuild", fetch = FetchType.LAZY)
    private List<BuildNote> buildNotes = new LinkedList<>();

    // a list of notes when attempting to overclock this computer build.
    @OneToMany(cascade = CascadeType.REFRESH, orphanRemoval = true, mappedBy = "computerBuild", fetch = FetchType.LAZY)
    private List<OverclockingNote> overclockingNotes = new LinkedList<>();

    // a list of computer parts.
    @OneToMany(cascade = CascadeType.REFRESH, orphanRemoval = true, mappedBy = "computerBuild", fetch = FetchType.LAZY)
    private List<ComputerPart> computerParts = new LinkedList<>();

    // a list of items that discusses the purpose of this build.
    @OneToMany(cascade = CascadeType.REFRESH, orphanRemoval = true, mappedBy = "computerBuild", fetch = FetchType.LAZY)
    private List<Purpose> purposeList = new LinkedList<>();

    @Column(name="build_identifier", unique = true)
    private String buildIdentifier;

    @PrePersist
    protected void onCreate() {
        this.createdAt = new Date();
    }

    @PostPersist
    protected void onUpdate() {
        this.updatedAt = new Date();
    }

    public ComputerBuild() { }



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ApplicationUser getUser() {
        return user;
    }

    public void setUser(ApplicationUser user) {
        this.user = user;
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

    public int getNumberOfDirections() {
        return numberOfDirections;
    }

    public void setNumberOfDirections(int numberOfDirections) {
        this.numberOfDirections = numberOfDirections;
    }

    public List<Direction> getDirections() {
        return directions;
    }

    public void setDirections(List<Direction> directions) {
        this.directions = directions;
    }

    public List<BuildNote> getBuildNotes() {
        return buildNotes;
    }

    public void setBuildNotes(List<BuildNote> buildNotes) {
        this.buildNotes = buildNotes;
    }

    public List<OverclockingNote> getOverclockingNotes() {
        return overclockingNotes;
    }

    public void setOverclockingNotes(List<OverclockingNote> overclockingNotes) {
        this.overclockingNotes = overclockingNotes;
    }

    public List<ComputerPart> getComputerParts() {
        return computerParts;
    }

    public void setComputerParts(List<ComputerPart> computerParts) {
        this.computerParts = computerParts;
    }

    public List<Purpose> getPurposeList() {
        return purposeList;
    }

    public void setPurposeList(List<Purpose> purposeList) {
        this.purposeList = purposeList;
    }

    public String getBuildIdentifier() {
        return buildIdentifier;
    }

    public void setBuildIdentifier(String buildIdentifier) {
        this.buildIdentifier = buildIdentifier;
    }

    public String getBuildDescription() {
        return buildDescription;
    }

    public void setBuildDescription(String buildDescription) {
        this.buildDescription = buildDescription;
    }
}
