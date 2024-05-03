package me.lunatic.updatechecker;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;
import me.lunatic.updatechecker.throwable.NoVersionsFoundException;

import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class UpdateChecker {

    private final String slug;
    private Predicate<Version> filter = version -> true;

    public UpdateChecker(String slug) {
        this.slug = slug;
    }

    public UpdateChecker setFilter(Predicate<Version> filter) {
        this.filter = filter;
        return this;
    }

    public List<Version> checkNow() {
        NoVersionsFoundException NO_VERSIONS_FOUND = new NoVersionsFoundException("Couldn't find any version for the slug '" + this.slug + "'. Does a mod with this slug exist?");
        List<Version> versions = new ArrayList<>();
        try {
            URL url = new URL("https://api.modrinth.com/v2/project/" + this.slug + "/version");
            JsonReader reader = new JsonReader(new InputStreamReader(url.openStream()));
            JsonArray baseArray = new Gson().fromJson(reader, JsonArray.class);
            baseArray.forEach(element -> {
                if (element instanceof JsonObject obj) {
                    String fileVersion = obj.get("version_number").getAsString();
                    List<String> gameVersions = obj.get("game_versions").getAsJsonArray().asList().stream().map(JsonElement::getAsString).toList();
                    List<String> loaders = obj.get("loaders").getAsJsonArray().asList().stream().map(JsonElement::getAsString).toList();
                    String changelog = obj.get("changelog").getAsString();
                    Version version = new Version(fileVersion, gameVersions, loaders, changelog);
                    if (this.filter.test(version)) {
                        versions.add(version);
                    }
                }
            });
        } catch (FileNotFoundException ex) {
            NO_VERSIONS_FOUND.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return versions;
    }
}
