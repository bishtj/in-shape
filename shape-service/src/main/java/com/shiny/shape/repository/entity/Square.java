package com.shiny.shape.repository.entity;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Square")
public class Square {
   @Id
   @GeneratedValue
   @Column(name = "SHAPE_ID")
   private Long id;

   @Column(name= "TYPE")
   private String type;

   @Column(name= "NAME", unique = true)
   private String name;

   @Column(name= "BOTTOM_LEFT_POINT_X")
   private int bottomLeftPointX;

   @Column(name= "BOTTOM_LEFT_POINT_Y")
   private int bottomLeftPointY;

   @Column(name= "WIDTH")
   private int width;
}
