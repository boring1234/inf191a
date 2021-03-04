package Application.controllers;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import Application.entity.Donation;
import Application.repository.DonationRepository;

public class DonationController {
  @Autowired
  private DonationRepository donationRepository;
  @RequestMapping(value = "donate", method = RequestMethod.POST)
  public ResponseEntity<?> donate(@RequestParam double donationAmount, @RequestParam int frequency,@RequestParam Date dateTime,
                                  @RequestParam String campaign,@RequestParam String donationTo,
                                  @RequestParam double donationTaxable, @RequestParam String methodOfGiving) {

    donationRepository.save(Donation.builder().donationAmount(donationAmount).frequency(frequency).dateTime(dateTime)
      .campaign(campaign).donationTo(donationTo).donationTaxable(donationTaxable).methodOfGiving(methodOfGiving).build());
    return new ResponseEntity<String>(HttpStatus.CREATED);
  }
  @RequestMapping(value = "getDonation", method = RequestMethod.GET)
  public ResponseEntity<?> getDonation() throws IOException {
    Workbook workbook = createHeader();

    File currDir = new File(".");
    String path = currDir.getAbsolutePath();
    String fileLocation = path.substring(0, path.length() - 1) + "temp.xlsx";

    FileOutputStream outputStream = new FileOutputStream(fileLocation);
    workbook.write(outputStream);
    workbook.close();
    return new ResponseEntity<String>(HttpStatus.CREATED);
  }

  private Workbook createHeader(){

    Workbook workbook = new XSSFWorkbook();

    Sheet sheet = workbook.createSheet("donation");
    for (int i = 0; i < 6; i ++){
      sheet.setColumnWidth(i, 7000);
    }

    Row header = sheet.createRow(0);

    CellStyle headerStyle = workbook.createCellStyle();

    XSSFFont font = ((XSSFWorkbook) workbook).createFont();
    font.setFontName("Arial");
    font.setFontHeightInPoints((short) 16);
    font.setBold(true);
    headerStyle.setFont(font);

    Cell headerCell = header.createCell(0);
    headerCell.setCellValue("Donation Amount");
    headerCell.setCellStyle(headerStyle);

    headerCell = header.createCell(1);
    headerCell.setCellValue("Frequency");
    headerCell.setCellStyle(headerStyle);

    headerCell = header.createCell(2);
    headerCell.setCellValue("Date Time");
    headerCell.setCellStyle(headerStyle);

    headerCell = header.createCell(3);
    headerCell.setCellValue("Campaign");
    headerCell.setCellStyle(headerStyle);

    headerCell = header.createCell(4);
    headerCell.setCellValue("Donation To");
    headerCell.setCellStyle(headerStyle);

    headerCell = header.createCell(5);
    headerCell.setCellValue("Method Of Giving");
    headerCell.setCellStyle(headerStyle);

    return workbook;
  }

  private Workbook createDonationContent(Workbook workbook){

    List<Donation> donationList = donationRepository.findAll();
    for (int i = 0; i < donationList.size(); i ++){

    }
    return workbook;
  }
}
