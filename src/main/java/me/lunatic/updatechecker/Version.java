package me.lunatic.updatechecker;

import java.util.List;

public record Version(String fileVersion, List<String> gameVersions, List<String> loaders, String changelog) {
}
