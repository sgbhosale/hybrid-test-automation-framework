package utils;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import com.fasterxml.jackson.databind.ObjectMapper;

import listeners.XrayListener;

public class XrayJsonGenerator {

	public static void generateJson() {
		
		
        try {

            // 🔥 Read values from Jenkins
            String build = System.getProperty("BUILD_NUMBER", "LOCAL");
            String suite = System.getProperty("SUITE", "default-suite");
            String browser = System.getProperty("browser", "chrome");

            // 🔥 Create dynamic execution name
            String summary = "Automation Execution - Build " + build + " - " + suite + " - " + browser;

            // =========================
            // INFO BLOCK (IMPORTANT)
            // =========================
            Map<String, Object> info = new HashMap<>();
            info.put("summary", summary);
            info.put("description", "Triggered from Jenkins automation");           

            // =========================
            // MAIN PAYLOAD
            // =========================
            Map<String, Object> payload = new HashMap<>();
            payload.put("info", info);   // 👈 THIS IS KEY CHANGE
            // Wrap results in "tests" key (Xray expects this)
           
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
