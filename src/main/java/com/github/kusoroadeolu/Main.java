package com.github.kusoroadeolu;

import com.github.kusoroadeolu.clique.Clique;
import com.github.kusoroadeolu.clique.config.ProgressBarConfiguration;
import com.github.kusoroadeolu.clique.progressbar.ProgressBar;

import java.util.List;
import java.util.Random;

import static java.lang.Thread.sleep;

class Main {
    public static void main(String[] args) throws InterruptedException {
// Simulate processing a batch of files
        List<String> files = List.of(
                "report_2024.pdf", "invoice_001.xlsx", "backup.zip",
                "presentation.pptx", "database.sql", "images.tar.gz",
                "logs.txt", "config.json", "readme.md", "archive.7z"
        );

        System.out.println("Starting file processing...\n");

        // Config with different states based on progress
        ProgressBarConfiguration config = ProgressBarConfiguration.immutableBuilder()
                .length(40)
                .styleWhen(p -> p < 30, "[yellow]:bar[/] [yellow]:percent%[/] - Starting up...")
                .styleWhen(p -> p >= 30 && p < 70, "[cyan]:bar[/] [cyan]:percent%[/] [:elapsed elapsed]")
                .styleWhen(p -> p >= 70 && p < 100, "[green]:bar[/] [green]:percent%[/] - Almost done!")
                .styleWhen(p -> p == 100, "[green, bold]:bar[/] [green]✓ Complete![/] [:elapsed total]")
                .build();

        ProgressBar progressBar = Clique.progressBar(files.size(), config);
        Random random = new Random();

        for (String file : files) {
            // Simulate variable processing time
            Thread.sleep(300 + random.nextInt(500));

            progressBar.tick();
            progressBar.render();
        }

        System.out.println("\nAll files processed successfully!");

    }

}



