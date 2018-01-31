package org.camunda.internal.hapdfy;

import com.itextpdf.forms.PdfAcroForm;
import com.itextpdf.forms.fields.PdfFormField;
import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.html2pdf.HtmlConverter;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.Map;

/**
 * @author mschoe
 */
@Service
public class PdfService {

  public byte[] createPdfFileFromTemplate(byte[] template, Map<String, String> content) throws IOException {

    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
    PdfDocument pdf = new PdfDocument(new PdfReader(new ByteArrayInputStream(template)), new PdfWriter(byteArrayOutputStream));

    PdfAcroForm form = PdfAcroForm.getAcroForm(pdf, true);
    Map<String, PdfFormField> fields = form.getFormFields();

    for (Map.Entry<String, String> entry : content.entrySet()) {
      fields.get(entry.getKey()).setValue(entry.getValue());
    }
    form.flattenFields();
    pdf.close();

    return byteArrayOutputStream.toByteArray();
  }

  public byte[] createPdfFileFromHtmlTemplate(String template, String dest) throws IOException {

    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
    //HtmlConverter.convertToPdf(template, byteArrayOutputStream);

    HtmlConverter.convertToPdf(template, new FileOutputStream(dest));


    return null;
    //return byteArrayOutputStream.toByteArray();
  }

  public void createPdf(String baseUri, String src, String dest) throws IOException {
    ConverterProperties properties = new ConverterProperties();
    properties.setBaseUri(baseUri);
    HtmlConverter.convertToPdf(new FileInputStream(src), new FileOutputStream(dest), properties);
  }

  public void createPdf(String html, String dest) throws IOException {
    HtmlConverter.convertToPdf(html, new FileOutputStream(dest));
  }
}
