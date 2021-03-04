package Application.entity;

import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Donation {
  public static final int ONE_TIME = 1;
  public static final int WEEKLY = 2;
  public static final int MONTHLY = 3;
  public static final int QUARTERLY = 4;
  public static final int ANNUALLY = 5;
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;
  private double donationAmount;
  private int frequency;
  private Date dateTime;
  private String campaign;
  private String donationTo;
  private double donationTaxable;
  private String methodOfGiving;
}
