package me.lunatic.test;

import me.lunatic.updatechecker.UpdateChecker;

public class Main {

    @SuppressWarnings("Convert2MethodRef")
    public static void main(String[] args) {
        // Here we create a new checker.
        UpdateChecker updateChecker = new UpdateChecker(UpdateChecker.Type.MOD, "fabric-api");
        // Then, we run a check. This sends a request via the modrinth api, trying to find the latest version's information.
        updateChecker.checkNow(res -> {
            // If there was a version found, we print the result's actual version name/string.
            System.out.println("Done. Found mod version: " + res.getGameVersions());
        }, throwable -> {
            // If there was an error, print the stacktrace for it.
            throwable.printStackTrace();
        });
    }
}
