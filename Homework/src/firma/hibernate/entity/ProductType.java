package firma.hibernate.entity;

import javax.persistence.*;

@Entity
@Table(name = "PRODUCT_TYPE")
public class ProductType {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "TYPE_CODE")
    private String typeCode;

    @Column(name = "TYPE_NAME")
    private String typeName;
}
