package Application.entity;

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
public class DonationManagement {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;
  private int donationId;
  private double giftMatchAmount;
  private double donationTipping;
  private double donorIncentiveReceived;
  private double BoardMemberParticipationRate;
}
