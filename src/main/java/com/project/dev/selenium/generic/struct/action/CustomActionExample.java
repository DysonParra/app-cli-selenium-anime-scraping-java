/*
 * @overview        {CustomActionExample}
 *
 * @version         2.0
 *
 * @author          Dyson Arley Parra Tilano <dysontilano@gmail.com>
 *
 * @copyright       Dyson Parra
 * @see             github.com/DysonParra
 *
 * History
 * @version 1.0     Implementation done.
 * @version 2.0     Documentation added.
 */
package com.project.dev.selenium.generic.struct.action;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.dev.selenium.generic.processor.NavigationProcessor;
import com.project.dev.selenium.generic.struct.Action;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.text.ParseException;
import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.ToString;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * TODO: Description of {@code CustomActionExample}.
 *
 * @author Dyson Parra
 * @since Java 17 (LTS), Gradle 7.3
 */
@AllArgsConstructor
@Builder
@Data
@NoArgsConstructor
@ToString(callSuper = true)
@JsonIgnoreProperties(ignoreUnknown = true)
public class CustomActionExample extends Action {

    @JsonProperty(value = "output-path")
    protected String outputPath;
    @JsonProperty(value = "output-file-name")
    protected String outputFileName;

    /**
     * Ejecuta una acción en el elemento de la página actual.
     *
     * @param driver   es el driver del navegador.
     * @param element  es el {@code WebElement} que se le va a ejecutar dicha acción.
     * @param flagsMap contiene las {@code Flag} pasadas por consola.
     * @return {@code true} si se ejecuta la acción correctamente.
     * @throws Exception si ocurre algún error ejecutando la acción indicada.
     */
    @Override
    public boolean executeAction(@NonNull WebDriver driver, @NonNull WebElement element, Map<String, String> flagsMap) throws Exception {
        System.out.println("        Current page: " + driver.getCurrentUrl());
        new File(outputPath).mkdirs();
        boolean result;
        if (outputFileName == null)
            result = NavigationProcessor.getPageSource(driver, outputPath);
        else
            result = NavigationProcessor.getPageSource(driver, new File(outputPath, outputFileName));

        if (result)
            return result;
        else
            throw new Exception("Page source could not be saved");
    }

}
