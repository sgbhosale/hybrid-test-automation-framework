package utils;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;

import listeners.XrayListener;

public class XrayJsonGenerator {

	public static void generateJson() {
        try {
            // Wrap results in "tests" key (Xray expects this)
            Map<String, Object> payload = new HashMap<>();
            payload.put("tests", XrayListener.results);

            ObjectMapper mapper = new ObjectMapper();

            // Create file in target folder
            File file = new File("target/xray-result.json");
            file.getParentFile().mkdirs();

            mapper.writerWithDefaultPrettyPrinter().writeValue(file, payload);

            System.out.println("✅ JSON generated at: " + file.getAbsolutePath());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
