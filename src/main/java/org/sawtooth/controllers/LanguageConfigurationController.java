package org.sawtooth.controllers;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.json.JacksonJsonParser;
import org.springframework.boot.json.JsonParser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Objects;

@RestController
@RequestMapping("/language-configuration")
public class LanguageConfigurationController {
    @Value("${tes.configurations.folder}")
    private String configurationsFolder;

    @GetMapping("get")
    @ResponseBody
    public ArrayList<String> GetLaunchConfigurations() {
        File directory = new File(configurationsFolder);
        JsonParser parser = new JacksonJsonParser();
        ArrayList<String> configurations = new ArrayList<>();

        try {
            for (File file : Objects.requireNonNull(directory.listFiles()))
                configurations.add((String) parser.parseMap(Files.readString(file.toPath())).get("name"));
        }
        catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return configurations;
    }
}
