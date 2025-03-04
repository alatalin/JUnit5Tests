package filetests;

import com.codeborne.pdftest.PDF;
import com.codeborne.xlstest.XLS;
import com.opencsv.CSVReader;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;

import java.io.InputStreamReader;
import java.util.List;
import java.util.Objects;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


public class ZipFileTest {

    private final ClassLoader cl = ZipFileTest.class.getClassLoader();

    @DisplayName("Проверка файла .pdf в .zip архиве")
    @Test
    void zipTestPdf() throws Exception {
    try(ZipInputStream is = new ZipInputStream(
               Objects.requireNonNull(cl.getResourceAsStream("ExampleZip.zip")))){
        ZipEntry entry;
        while ((entry = is.getNextEntry()) != null) {
            if (entry.getName().contains(".pdf")) {
                PDF pdf = new PDF(is);
                assertThat(pdf.numberOfPages).isEqualTo(1);
                assertThat(pdf.text).contains("Пример документа в формате PDF");
                }
            }
        }
    }

    @DisplayName("Проверка файла .xls в .zip архиве")
    @Test
    void zipTestXlsx() throws Exception {
    try(ZipInputStream is = new ZipInputStream(
            Objects.requireNonNull(cl.getResourceAsStream("ExampleZip.zip")))){
        ZipEntry entry;
        while ((entry = is.getNextEntry()) != null) {
            if (entry.getName().contains(".xls")) {
                XLS xls = new XLS(is);
                String actualValue = xls.excel.getSheetAt(0).getRow(9).getCell(2).getStringCellValue();
                Assertions.assertTrue(actualValue.contains("Campaign Analysis"));
                }
            }
        }
    }

   @DisplayName("Проверка файла .csv в .zip архиве")
   @Test
   void zipTestCsv() throws Exception {
   try( ZipInputStream is = new ZipInputStream(
           Objects.requireNonNull(cl.getResourceAsStream("ExampleZip.zip")))) {
       ZipEntry entry;
       while ((entry = is.getNextEntry()) != null) {
           if (entry.getName().contains(".csv")) {
               CSVReader csvReader = new CSVReader(new InputStreamReader(is));
               List<String[]> data = csvReader.readAll();
               Assertions.assertEquals(4, data.size());
               Assertions.assertArrayEquals(
                       new String[]{"Name","Job Title","Address"},
                       data.get(0)
               );
               Assertions.assertArrayEquals(
                       new String[]{"Doe John","Designer","325 Pine Street"},
                       data.get(1)
               );
               Assertions.assertArrayEquals(
                       new String[]{"Green Edward","Developer","110 Pike Street"},
                       data.get(2)
               );
               Assertions.assertArrayEquals(
                       new String[]{"Black Alex","Builder","125 Rock Street"},
                       data.get(3)
               );
               }
           }
       }
   }
}
