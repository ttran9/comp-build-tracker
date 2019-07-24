package tran.compbuildbackend.domain.computerbuild;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Date;

@Entity
@Table(name = "computer_part")
public class ComputerPart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="computer_build_id", nullable = false, updatable = false)
    private ComputerBuild computerBuild;

    @NotNull
    private String name;

    @Column(columnDefinition = "DECIMAL(8,2)")
    @Min(0)
    @Max(999999)
    private double price;

    @NotNull
    private Date purchaseDate;

    private String placePurchasedAt;

    @Column(name = "other_note")
    @NotNull
    @Lob
    private String otherNotes;

    public ComputerPart() {}

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Date getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(Date purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public String getPlacePurchasedAt() {
        return placePurchasedAt;
    }

    public void setPlacePurchasedAt(String placePurchasedAt) {
        this.placePurchasedAt = placePurchasedAt;
    }

    public String getOtherNotes() {
        return otherNotes;
    }

    public void setOtherNotes(String otherNotes) {
        this.otherNotes = otherNotes;
    }
}