package Application.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import Application.entity.Organization;

public interface OrganizationRepository extends JpaRepository<Organization, Integer> {
  @Query(value = "SELECT * FROM Organization WHERE superUserId = ?1", nativeQuery = true)
  Organization findByUserId(int superUserId);


  @Query(value = "SELECT * FROM Organization WHERE name = ?1", nativeQuery = true)
  Organization findByName(String name);
}
