package me.lunatic.updatechecker;

import me.lunatic.updatechecker.resultType.ModResult;
import me.lunatic.updatechecker.resultType.Result;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.function.Consumer;

public class UpdateChecker {

    private final Type type;
    private final String slug;

    public UpdateChecker(Type type, String slug) {
        this.type = type;
        this.slug = slug;
    }

    public void checkNow(Consumer<Result> onSuccess, Consumer<Throwable> onError) {
        try {
            URL url = new URL("https://api.modrinth.com/v2/project/" + slug + "/version");
            BufferedReader reader = new BufferedReader(new InputStreamReader((InputStream) url.getContent()));
            StringBuilder s = new StringBuilder();
            for (String s1 : reader.lines().toList()) {
                s.append(s1);
            }
            JSONArray array = new JSONArray(s.toString());
            if (array.isEmpty()) {
                //TODO: no versions found.
                return;
            }
            JSONObject obj = array.getJSONObject(0);
            if (type == Type.MOD) {
                JSONArray loaders = new JSONArray("[]");
                if (!obj.has("loaders") || (loaders = obj.getJSONArray("loaders")).isEmpty()) {
                    //TODO: no loaders found
                }
                JSONArray gameVersions = new JSONArray("[]");
                if (!obj.has("game_versions") || (gameVersions = obj.getJSONArray("game_versions")).isEmpty()) {
                    //TODO: no game versions found
                }
                try {
                    onSuccess.accept(new ModResult(obj.getString("version_number"), gameVersions.toList().stream().map(Object::toString).toList(), obj.getString("changelog"), loaders.toList().stream().map(Object::toString).toList()));
                } catch (Exception e) {
                    onError.accept(e);
                }
            }
        } catch (FileNotFoundException e) {
            onError.accept(new FileNotFoundException("Couldn't find api-result for mod '" + slug + "'"));
        } catch (Exception e) {
            onError.accept(e);
        }
    }

    public enum Type {
        MOD,
        PLUGIN;
    }
}
