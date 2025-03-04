package filetests;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import model.JsonFile;

import java.io.InputStream;
import java.io.InputStreamReader;

public class JsonFileTest {

    private final ClassLoader cl = JsonFileTest.class.getClassLoader();
    ObjectMapper objectMapper = new ObjectMapper();

    @DisplayName("Проверка файла .json")
    @Test
    void chechJsonFileTest() throws Exception {
        try(InputStream is = cl.getResourceAsStream("Brand.json")){
            assert is != null;
            try(InputStreamReader isr = new InputStreamReader(is)) {
                JsonFile data;
                data = objectMapper.readValue(isr, JsonFile.class);
                Assertions.assertEquals(111,data.getBrand().getId());
                Assertions.assertEquals("Kia", data.getBrand().getName());
                Assertions.assertEquals("Ceed", data.getModels().get(0).getName());
                Assertions.assertEquals("Sorento", data.getModels().get(1).getName());
                Assertions.assertEquals("Rio", data.getModels().get(2).getName());
            }
        }
    }
}
