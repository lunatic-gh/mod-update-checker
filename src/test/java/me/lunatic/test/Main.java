package me.lunatic.test;

import me.lunatic.updatechecker.UpdateChecker;

public class Main {

    public static void main(String[] args) {
        // Create a new Update checker, in this example for the mod "modmenu", available at https://modrinth.com/mod/modmenu
        UpdateChecker updateChecker = new UpdateChecker("modmenu");
        // Set a filter.
        updateChecker.setFilter(version -> {
            // This will only queue versions that support the quilt loader. Only for the sake of the example, please don't use quilt :(
            boolean supportsQuilt = version.loaders().contains("quilt");
            // This will only queue versions that support 1.20.4.
            boolean supports_1_20_4 = version.gameVersions().contains("1.20.4");
            // return the filter result.
            return supportsQuilt && supports_1_20_4;
        });
        // Run a check. This will send an HTTP-Request via the modrinth api, to the given mod. It will run the below action for every version that matches the above filter.
        updateChecker.checkNow().forEach(version -> {
            System.out.println("Found: " + version.gameVersions() + " | " + version.loaders());
        });
    }
}
