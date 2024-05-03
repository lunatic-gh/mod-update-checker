package me.lunatic.updatechecker.resultType;

import java.util.List;

public class Result {
    private final String fileVersion;
    private final List<String> gameVersions;
    private final String changelog;

    public Result(String fileVersion, List<String> gameVersions, String changelog) {
        this.fileVersion = fileVersion;
        this.gameVersions = gameVersions;
        this.changelog = changelog;
    }

    public String getFileVersion() {
        return fileVersion;
    }

    public List<String> getGameVersions() {
        return gameVersions;
    }

    public String getChangelog() {
        return changelog;
    }
}
