package pl.alpheratzteam.obfuscator.core.configuration;

import org.jetbrains.annotations.NotNull;
import pl.alpheratzteam.obfuscator.api.basic.Obfuscator;
import pl.alpheratzteam.obfuscator.api.configuration.Configuration;
import pl.alpheratzteam.obfuscator.core.basic.ObfuscatorImpl;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;

/**
 * @author Unix on 01.09.2019.
 */
public class ConfigurationImpl implements Configuration {

    private final Obfuscator              obfuscator;
    private final JsonParser              jsonParser;
    private final Map<String, JsonObject> jsonObjects;

    public ConfigurationImpl(Obfuscator obfuscator) {
        this.obfuscator  = obfuscator;
        this.jsonParser  = new JsonParser();
        this.jsonObjects = new HashMap<>();
    }

    @Override
    public void checkConfigurationFiles(String... files) {
        Arrays.stream(files).forEach(fileName -> {
            fileName += ".json";

            try {
                final InputStream inputStream = ObfuscatorImpl.class.getResourceAsStream("/" + fileName);

                if (inputStream == null) {
                    this.obfuscator.getLogger().warning("Cannot find file with name " + fileName + " in jar file!");
                    System.exit(-1);
                    return;
                }

                final File file = new File(this.obfuscator.getDataFolder(), fileName);

                if (file.exists()) {
                    this.jsonObjects.put(fileName, this.readConfiguration(fileName));
                    return;
                }

                final FileOutputStream fileOutputStream = new FileOutputStream(fileName);

                int read;
                while ((read = inputStream.read()) != -1) {
                    fileOutputStream.write(read);
                }

                inputStream.close();
                fileOutputStream.close();

                this.jsonObjects.put(fileName, this.readConfiguration(fileName));
                this.obfuscator.getLogger().info("Loaded configuration file with name " + fileName + ".");
            } catch (Exception e) {
                this.obfuscator.getLogger().log(Level.SEVERE, "Cannot create configuration file with name " + fileName, e);
                System.exit(-1);
            }
        });
    }

    @Override
    public void reloadConfigurationFile(@NotNull String fileName) {
        if (!fileName.endsWith(".json")) fileName += ".json";

        if (!this.jsonObjects.containsKey(fileName)) {
            this.obfuscator.getLogger().warning("Configuration file with name " + fileName + " doesn't created before!");
            return;
        }

        this.jsonObjects.put(fileName, this.readConfiguration(fileName));
        this.obfuscator.getLogger().info("Successfully reloaded configuration with name " + fileName + "!");
    }

    @Override
    public JsonObject readConfiguration(String fileName) {
        try {
            final Reader reader = new BufferedReader(new InputStreamReader(new FileInputStream(new File(this.obfuscator.getDataFolder(), fileName))));
            return this.jsonParser.parse(reader).getAsJsonObject();
        } catch (Exception e) {
            this.obfuscator.getLogger().log(Level.SEVERE, "Cannot read configuration file with name " + fileName, e);
        }

        return null;
    }

    @Override
    public JsonObject getConfiguration(String fileName) {
        return this.jsonObjects.get(fileName);
    }
}