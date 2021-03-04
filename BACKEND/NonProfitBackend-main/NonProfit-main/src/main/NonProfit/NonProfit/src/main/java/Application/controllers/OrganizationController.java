package Application.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import Application.entity.Organization;
import Application.repository.OrganizationRepository;

public class OrganizationController {
  @Autowired
  private OrganizationRepository organizationRepository;

  @RequestMapping(value = "createOrg", method = RequestMethod.POST)
  public ResponseEntity<?> createOrg(@RequestParam String name, @RequestParam int superUserId) {

    if (organizationRepository.findByName(name) != null) {

      return new ResponseEntity<String>(HttpStatus.CONFLICT);
    }
    organizationRepository.save(Organization.builder().name(name).superUserId(superUserId).build());
    return new ResponseEntity<String>(HttpStatus.CREATED);
  }

  /**
   * front end should make suer user is authenticated before coming here, ideally, should implement jwt
   * @param superUserId
   * @return
   */
  @RequestMapping(value = "getOrg", method = RequestMethod.POST)
  public ResponseEntity<?> getOrg(@RequestParam int superUserId) {
    return new ResponseEntity<>(organizationRepository.findByUserId(superUserId), HttpStatus.CREATED);
  }
}
