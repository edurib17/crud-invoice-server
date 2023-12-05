package com.invoice.invoice.domain;import com.fasterxml.jackson.annotation.JsonIgnoreProperties;import jakarta.persistence.*;import jakarta.validation.constraints.NotNull;import lombok.AllArgsConstructor;import lombok.Getter;import lombok.NoArgsConstructor;import lombok.Setter;import org.hibernate.annotations.GenericGenerator;import org.hibernate.annotations.Type;import java.math.BigDecimal;import java.util.UUID;@Entity@Table(name = "invoice_item")@Getter@Setter@AllArgsConstructor@NoArgsConstructorpublic class InvoiceItem {    @Id    @GeneratedValue(generator = "UUID", strategy = GenerationType.IDENTITY)    @GenericGenerator(            name = "UUID",            strategy = "org.hibernate.id.UUIDGenerator"    )    private UUID id;    @NotNull    private String name;    private Float quantity;    private BigDecimal unitPrice;    @ManyToOne    @JoinColumn(name ="invoice_id")    @JsonIgnoreProperties(value = "items", allowSetters = true)    private Invoice invoice;    public BigDecimal getUnitTotal(){        if (quantity != null && quantity > 0 && unitPrice != null && unitPrice.compareTo(BigDecimal.ZERO) > 0) {            return BigDecimal.valueOf(quantity).multiply(unitPrice);        } else {            return BigDecimal.ZERO;        }    }}