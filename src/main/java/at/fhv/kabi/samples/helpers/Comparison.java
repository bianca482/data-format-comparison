package at.fhv.kabi.samples.helpers;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;

import static at.fhv.kabi.samples.Main.BASE_PATH;

public class Comparison {

    public static void compare() {
        File rootFolder = new File(BASE_PATH + "/results/");
        if (!rootFolder.isDirectory()) {
            System.out.println("Root directory not found: " + BASE_PATH);
            return;
        }

        File[] useCaseFolders = rootFolder.listFiles(File::isDirectory);
        if (useCaseFolders == null) {
            System.out.println("No use case folders found.");
            return;
        }

        for (File useCaseFolder : useCaseFolders) {
            File compFolder = new File(useCaseFolder, "comp");

            if (!compFolder.exists() || !compFolder.isDirectory()) {
                System.out.println("[SKIP] No 'comp' folder in " + useCaseFolder.getName());
                continue;
            }

            System.out.println("Comparing files in: " + useCaseFolder.getName());

            File[] mainFiles = useCaseFolder.listFiles(File::isFile);
            if (mainFiles == null) continue;

            for (File mainFile : mainFiles) {
                File compFile = new File(compFolder, mainFile.getName());

                if (compFile.exists()) {
                    boolean identical = compareFiles(mainFile, compFile);
                    if (identical) {
                        System.out.println("[IDENTICAL] " + mainFile.getName());
                    } else {
                        System.out.println("[DIFFERENT] " + mainFile.getName());
                    }
                } else {
                   // System.out.println("[MISSING] No corresponding file for " + mainFile.getName() + " in comp folder.");
                }
            }
            System.out.println();
        }
    }

    private static boolean compareFiles(File file1, File file2) {
        try {
            byte[] f1 = Files.readAllBytes(file1.toPath());
            byte[] f2 = Files.readAllBytes(file2.toPath());
            return Arrays.equals(f1, f2);
        } catch (IOException e) {
            System.err.println("[ERROR] Failed to read files: " + file1.getName() + " or " + file2.getName());
            return false;
        }
    }
}
