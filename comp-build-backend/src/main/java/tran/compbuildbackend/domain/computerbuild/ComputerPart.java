package tran.compbuildbackend.domain.computerbuild;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Table(name = "computer_part")
public class ComputerPart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="computer_build_id", nullable = false, updatable = false)
    @JsonIgnore
    private ComputerBuild computerBuild;

    @NotNull
    private String name;

    @Column(columnDefinition = "DECIMAL(8,2)")
    @Min(0)
    @Max(999999)
    private double price;

    @Column(name = "unique_identifier", unique = true)
    private String uniqueIdentifier;

    @NotNull
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate purchaseDate;

    @NotNull
    private String placePurchasedAt;

    @Column(name = "other_note")
    @Lob
    private String otherNotes;

    public ComputerPart() {}

    public ComputerPart(@NotNull String name, @Min(0) @Max(999999) double price, String uniqueIdentifier,
                        @NotNull LocalDate purchaseDate, @NotNull String placePurchasedAt, String otherNotes) {
        this.name = name;
        this.price = price;
        this.uniqueIdentifier = uniqueIdentifier;
        this.purchaseDate = purchaseDate;
        this.placePurchasedAt = placePurchasedAt;
        this.otherNotes = otherNotes;
    }

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

    public LocalDate getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(LocalDate purchaseDate) {
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

    public String getUniqueIdentifier() {
        return uniqueIdentifier;
    }

    public void setUniqueIdentifier(String uniqueIdentifier) {
        this.uniqueIdentifier = uniqueIdentifier;
    }

    @Override
    public String toString() {
        return "ComputerPart{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", uniqueIdentifier='" + uniqueIdentifier + '\'' +
                ", placePurchasedAt='" + placePurchasedAt + '\'' +
                ", otherNotes='" + otherNotes + '\'' +
                '}';
    }
}
