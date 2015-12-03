package co.superclark.entity;

import org.hibernate.annotations.*;
import org.hibernate.ogm.datastore.document.options.AssociationStorage;
import org.hibernate.ogm.datastore.document.options.AssociationStorageType;

import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;

/**
 * @Author clark
 * @Date 30-Nov-2015
 */
@Entity
@AssociationStorage(AssociationStorageType.ASSOCIATION_DOCUMENT)
public class Dog {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;

    @Version
    @Generated(value = GenerationTime.ALWAYS)
    @Column(name = "_rev")
    private String revision;
    private String name;
    @OneToOne(cascade = CascadeType.PERSIST)
    private Breed breed;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRevision() {
        return revision;
    }

    public void setRevision(String revision) {
        this.revision = revision;
    }

    public Breed getBreed() {
        return breed;
    }

    public void setBreed(Breed breed) {
        this.breed = breed;
    }
}
