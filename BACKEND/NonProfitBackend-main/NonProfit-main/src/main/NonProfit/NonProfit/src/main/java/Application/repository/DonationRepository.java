package Application.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import Application.entity.Donation;

public interface DonationRepository extends JpaRepository<Donation, Integer> {


}
