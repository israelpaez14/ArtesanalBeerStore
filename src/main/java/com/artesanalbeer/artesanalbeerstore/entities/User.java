package com.artesanalbeer.artesanalbeerstore.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "_users")
public class User {
  @Id private UUID id;
  private String email;
  private String firstName;
  private String lastName;
}
