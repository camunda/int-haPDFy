package org.camunda.internal.hapdfy;

import com.itextpdf.forms.PdfAcroForm;
import com.itextpdf.forms.fields.PdfFormField;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
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
}
