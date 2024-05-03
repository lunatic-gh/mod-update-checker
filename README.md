# Update Checker for mods hosted on Modrinth.

## How to use:
- Add the update-checker to your project:
  - Maven:
```xml
<repositories>
  ...
  <repository>
    <id>lunatic-maven-releases</id>
    <name>Lunatic Repository</name>
    <url>https://maven.chloedev.de/releases</url>
  </repository>
</repositories>

<dependencies>
  ...
  <dependency>
    <groupId>me.lunatic</groupId>
    <artifactId>mod-update-checker</artifactId>
    <version>1.1</version>
  </dependency>
</dependencies>
```
- Here's a basic example:
```java
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
        System.out.println("Checking for updates...");
        List<Version> versions = updateChecker.checkNow();
        if (!versions.isEmpty()) {
            System.out.println("Found the following matching versions:");
            versions.forEach(version -> System.out.println(version.fileVersion() + " | " + version.gameVersions() + " | " + version.loaders()));
        }
```
