/*
 * @overview        {NyaaScraping}
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
import com.project.dev.file.generic.FileFunction;
import com.project.dev.file.generic.FileProcessor;
import com.project.dev.selenium.generic.struct.Action;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.ToString;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * TODO: Description of {@code NyaaScraping}.
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
public class NyaaScraping extends Action {

    @JsonProperty(value = "excluded-series-file-path")
    protected String excludedSeriesFilePath;
    @JsonProperty(value = "min-torrents-date")
    protected String minTorrentsDate;
    @JsonProperty(value = "dom-table-element-xpath")
    protected String domTableElementXpath;
    @JsonProperty(value = "dom-table-element-title-xpath")
    protected String domTableElementTitleXpath;
    @JsonProperty(value = "dom-table-element-date-xpath")
    protected String domTableElementDateXpath;
    @JsonProperty(value = "dom-table-element-download-button-xpath")
    protected String domTableElementDownloadButtonXpath;
    @JsonProperty(value = "delay-time-before-actions")
    protected Integer delayTimeBeforeActions;
    @JsonProperty(value = "delay-time-beetween-downloads")
    protected Integer delayTimeBeetweenDownloads;

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
        boolean result = false;
        try {
            SimpleDateFormat dateParser = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            Date minTorrentsDateDt = dateParser.parse(minTorrentsDate);

            result = FileProcessor.validateFile(excludedSeriesFilePath);
            if (result) {
                List<String> excludedSeries = new ArrayList<>();
                FileProcessor.forEachLine(excludedSeriesFilePath, FileFunction::addLineToList, excludedSeries);

                Thread.sleep(delayTimeBeforeActions);
                List<WebElement> table = driver.findElements(By.xpath(domTableElementXpath));
                System.out.println("        Min Torrents date: " + minTorrentsDate);
                System.out.println("        Found elements: " + table.size());
                for (WebElement tableElement : table) {
                    try {
                        String currentTitle = tableElement.findElement(By.xpath(domTableElementTitleXpath)).getText();
                        String currentDate = tableElement.findElement(By.xpath(domTableElementDateXpath)).getText();
                        WebElement button = tableElement.findElement(By.xpath(domTableElementDownloadButtonXpath));
                        Date currentDateDt = dateParser.parse(currentDate);
                        Boolean excluded = false;
                        if (currentDateDt.compareTo(minTorrentsDateDt) >= 0) {
                            System.out.println("");
                            System.out.println("        " + currentTitle);
                            System.out.println("        " + currentDate);

                            for (String serie : excludedSeries)
                                if (currentTitle.matches(".*" + serie + ".*")) {
                                    excluded = true;
                                    break;
                                }

                            if (excluded)
                                System.out.println("        Excluded");
                            else {
                                System.out.println("        Downloading...");
                                button.click();
                                Thread.sleep(delayTimeBeetweenDownloads);
                            }
                        } else
                            break;
                    } catch (Exception e) {

                    }
                }
            }
        } catch (Exception e) {
            throw e;
        }
        return result;
    }

}
