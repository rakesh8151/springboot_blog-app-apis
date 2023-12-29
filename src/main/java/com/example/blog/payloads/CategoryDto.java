package com.example.blog.payloads;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CategoryDto {
    private  Integer categoryId;

   @NotEmpty(message = "title must not empty !!")
   @Size(min = 4,message = "title length min of 4 characters")
    private  String title;

    @NotEmpty(message = "description must not empty !!")
    @Size(min = 10,message = "description length min of 4 characters")
    private  String categoryDescription;
}
