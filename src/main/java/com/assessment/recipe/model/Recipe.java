package com.assessment.recipe.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.Map;

@Data
@Entity
@Table(name = "recipes")
public class Recipe {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String title;

    private String cuisine;

    private Float rating;

    @JsonProperty("prep_time")
    @Column(name = "prep_time", nullable = false)
    private Integer prepTime;

    @JsonProperty("cook_time")
    @Column(name = "cook_time", nullable = false)
    private Integer cookTime;

    @JsonProperty("total_time")
    @Column(name = "total_time", nullable = false)
    private Integer totalTime;

    @Column(columnDefinition = "TEXT")
    private String description;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(columnDefinition = "JSON")
    private Map<String, Object> nutrients;

    private String serves;

    @PrePersist
    @PreUpdate
    public void calculateTotalTime() {
        if (this.totalTime == null || this.totalTime == 0) {
            int prep = (this.prepTime != null) ? this.prepTime : 0;
            int cook = (this.cookTime != null) ? this.cookTime : 0;
            this.totalTime = prep + cook;
        }
    }
}
