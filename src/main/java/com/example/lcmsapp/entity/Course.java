package com.example.lcmsapp.entity;

import com.example.lcmsapp.entity.template.AbsNameEntity;
import lombok.*;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Getter
@Setter
@ToString
public class Course extends AbsNameEntity {
    @ManyToOne
    private Filial filial;

    private Double price;

    private Integer modules;

    @ElementCollection
    private List<String> moduleNames;
}
