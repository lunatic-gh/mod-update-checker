package me.lunatic.updatechecker.resultType;

import java.util.List;

public class ModResult extends Result {

    private final List<String> loaders;

    public ModResult(String fileVersion, List<String> gameVersions, String changelog, List<String> loaders) {
        super(fileVersion, gameVersions, changelog);
        this.loaders = loaders;
    }

    public List<String> getLoaders() {
        return loaders;
    }
}
