package pl.alpheratzteam.obfuscator.api.configuration;

import com.google.gson.JsonObject;

/**
 * @author Unix on 01.09.2019.
 */
public interface Configuration {

    void checkConfigurationFiles(String... files);

    void reloadConfigurationFile(String fileName);

    JsonObject readConfiguration(String fileName);

    JsonObject getConfiguration(String fileName);

}