package org.camunda.internal.hapdfy;

import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import org.junit.Ignore;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * @author mschoe
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class PdfServiceTest {

  @Autowired
  private PdfService pdfService;

  @Rule
  public TemporaryFolder folder = new TemporaryFolder();

  @Test
  public void shouldCreatePdfFileFromTemplate() throws Exception {
    String templateFileName = "./src/test/resources/pdfTemplate/customer-info_template_v1.pdf";
    Map<String, String> inputParameters = new HashMap<>();
    inputParameters.put("customerName", "Pillemann und Söhne GmbH & Cö. KG");
    inputParameters.put("welcome", "Welcome to Camunda BPM.");
    inputParameters.put("availabilityLevel1", "24/7");
    inputParameters.put("availabilityLevel2", "8/5");
    inputParameters.put("availabilityLevel3", "8/5");
    inputParameters.put("responseTimeLevel1", "2 clock hours");
    inputParameters.put("responseTimeLevel2", "8 business hours");
    inputParameters.put("responseTimeLevel3", "16 business hours");
    inputParameters.put("ticketingSystemL1", "24/7 Hotline");
    inputParameters.put("ticketingSystemL2", "Ticketing System (Monday-Friday, 9 a.m.-5 p.m. CET, German holidays apply)");
    inputParameters.put("ticketingSystemL3", "Ticketing System (Monday-Friday, 9 a.m.-5 p.m. CET, German holidays apply)");
    inputParameters.put("maxNumberSupportAccounts", "4");
    inputParameters.put("ticketPeriod_amount", "year: unlimited");
    inputParameters.put("expirationDate", "Evaluation expiration date: 30.09.2017");
    inputParameters.put("officeHotline", "Furthermore you may contact us via telephone: +49 30 6640409-19, Monday-Friday, 9 a.m.-5 p.m. CET, German holidays apply.");
    inputParameters.put("twentyfoursevenHotline", "Outside business hours (Monday-Friday, 9 a.m.-5 p.m. CET, German holidays apply) please contact our 24x7 support hotline for Level 1 Issues: +49 30 6640409-11.");
    inputParameters.put("customerAccountName", "pillemann_und_soehne_kg");
    inputParameters.put("customerAccountPassword", "lidnfloiernvroin");
    inputParameters.put("licenseKey", "--------------- BEGIN CAMUNDA BPM LICENSE KEY ---------------\n" +
            "PibVb/dg0DpAbdNmzSSsWM0nZb8FZIpovNN6B5uIW90RYUMGiKlzHIPsLo2GZ\n" +
            "MGOEhRKGmexpCu02eFbY1NmkMLlmi+kUD1MoOO2dvWjLut6EPhPhxPwIcpO8J\n" +
            "vFZAFPPuAFxy+iD37zMwGqiu9apFVM+0pAqJRrabBxnexRmhc=;\n" +
            "AEM Corporation;unlimited\n" +
            "--------------- END CAMUNDA BPM LICENSE KEY ---------------");

    Path pdfPath = Paths.get(templateFileName);

    OutputStream out = new FileOutputStream(folder.getRoot() + "/customer-info_output.pdf");
    out.write(pdfService.createPdfFileFromTemplate(Files.readAllBytes(pdfPath), inputParameters));
    out.close();

    File file = new File(folder.getRoot() + "/customer-info_output.pdf");
    assertTrue(file.exists());
  }

  @Test
  @Ignore
  public void shouldCreatePermanentPdfFileFromTemplate() throws Exception {
    String templateFileName = "./src/test/resources/pdfTemplate/customer-info_template_v1.pdf";
    Map<String, String> inputParameters = new HashMap<>();
    inputParameters.put("customerName", "Pillemann und Söhne GmbH & Cö. KG");
    inputParameters.put("welcome", "Welcome to Camunda BPM.");
    inputParameters.put("availabilityLevel1", "24/7");
    inputParameters.put("availabilityLevel2", "8/5");
    inputParameters.put("availabilityLevel3", "8/5");
    inputParameters.put("responseTimeLevel1", "2 clock hours");
    inputParameters.put("responseTimeLevel2", "8 business hours");
    inputParameters.put("responseTimeLevel3", "16 business hours");
    inputParameters.put("ticketingSystemL1", "24/7 Hotline");
    inputParameters.put("ticketingSystemL2", "Ticketing System (Monday-Friday, 9 a.m.-5 p.m. CET, German holidays apply)");
    inputParameters.put("ticketingSystemL3", "Ticketing System (Monday-Friday, 9 a.m.-5 p.m. CET, German holidays apply)");
    inputParameters.put("maxNumberSupportAccounts", "4");
    inputParameters.put("ticketPeriod_amount", "year: unlimited");
    inputParameters.put("expirationDate", "Evaluation expiration date: 30.09.2017");
    inputParameters.put("officeHotline", "Furthermore you may contact us via telephone: +49 30 6640409-19, Monday-Friday, 9 a.m.-5 p.m. CET, German holidays apply.");
    inputParameters.put("twentyfoursevenHotline", "Outside business hours (Monday-Friday, 9 a.m.-5 p.m. CET, German holidays apply) please contact our 24x7 support hotline for Level 1 Issues: +49 30 6640409-11.");
    inputParameters.put("customerAccountName", "pillemann_und_soehne_kg");
    inputParameters.put("customerAccountPassword", "lidnfloiernvroin");
    inputParameters.put("licenseKey", "--------------- BEGIN CAMUNDA BPM LICENSE KEY ---------------\n" +
            "PibVb/dg0DpAbdNmzSSsWM0nZb8FZIpovNN6B5uIW90RYUMGiKlzHIPsLo2GZ\n" +
            "MGOEhRKGmexpCu02eFbY1NmkMLlmi+kUD1MoOO2dvWjLut6EPhPhxPwIcpO8J\n" +
            "vFZAFPPuAFxy+iD37zMwGqiu9apFVM+0pAqJRrabBxnexRmhc=;\n" +
            "AEM Corporation;unlimited\n" +
            "--------------- END CAMUNDA BPM LICENSE KEY ---------------");

    Path pdfPath = Paths.get(templateFileName);

    OutputStream out = new FileOutputStream("./src/test/resources/pdfTemplate/customer-info_output.pdf");
    out.write(pdfService.createPdfFileFromTemplate(Files.readAllBytes(pdfPath), inputParameters));
    out.close();

    File file = new File("./src/test/resources/pdfTemplate/customer-info_output.pdf");
    assertTrue(file.exists());
  }

  @Test
  public void shouldCreatePdfFromHtmlTemplate() throws IOException {
    String templateFileName = "./src/test/resources/pdfTemplate/customer-info-template_v1.html";
    String htmlTemplate = readHtmlFile(templateFileName);

    pdfService.createPdf("./src/test/resources/pdfTemplate/", templateFileName, "target/output.pdf");
//    OutputStream out = new FileOutputStream("./src/test/resources/pdfTemplate/customer-info-html2pdf_output.pdf");
//    out.write(pdfService.createPdfFileFromHtmlTemplate(htmlTemplate));
//    out.close();
  }

  public String readHtmlFile(String location) throws IOException {
    StringBuilder bldr = new StringBuilder();
    String str;

    BufferedReader in = new BufferedReader(new FileReader(location));
    while ((str = in.readLine()) != null)
      bldr.append(str);

    in.close();

    return bldr.toString();
  }

  @Test
  public void simpleHtmlConverter() throws IOException {
    String templateDir = "./src/test/resources/pdfTemplate/";
    File htmlSource = new File(templateDir + "input.html");
    File pdfDest = new File(templateDir + "output.pdf");

    // pdfHTML specific code
    ConverterProperties converterProperties = new ConverterProperties();
    HtmlConverter.convertToPdf(new FileInputStream(htmlSource), new FileOutputStream(pdfDest), converterProperties);
  }
}