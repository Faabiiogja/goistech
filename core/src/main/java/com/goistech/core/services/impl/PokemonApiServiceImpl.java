package com.goistech.core.services.impl;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.osgi.service.component.annotations.Activate;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Modified;
import org.osgi.service.metatype.annotations.AttributeDefinition;
import org.osgi.service.metatype.annotations.Designate;
import org.osgi.service.metatype.annotations.ObjectClassDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.goistech.core.models.PokemonData;
import com.goistech.core.services.PokemonApiService;

@Component(service = PokemonApiService.class, immediate = true)
@Designate(ocd = PokemonApiServiceImpl.Config.class)
public class PokemonApiServiceImpl implements PokemonApiService {

    private static final Logger log = LoggerFactory.getLogger(PokemonApiServiceImpl.class);

    @ObjectClassDefinition(name = "Gois Tech - Pokemon API Service")
    public @interface Config {
        @AttributeDefinition(name = "API Base URL", description = "Base URL for PokeAPI v2")
        String apiBaseUrl() default "https://pokeapi.co/api/v2";

        @AttributeDefinition(name = "Timeout (seconds)", description = "HTTP request timeout in seconds")
        int timeoutSeconds() default 10;
    }

    private String apiBaseUrl;
    private int timeoutSeconds;
    private HttpClient httpClient;

    @Activate
    @Modified
    protected void activate(Config config) {
        this.apiBaseUrl = config.apiBaseUrl();
        this.timeoutSeconds = config.timeoutSeconds();
        this.httpClient = HttpClient.newBuilder()
            .connectTimeout(Duration.ofSeconds(timeoutSeconds))
            .build();
    }

    @Override
    public PokemonData getPokemon(String nameOrId) {
        if (nameOrId == null || nameOrId.trim().isEmpty()) {
            return null;
        }

        String url = apiBaseUrl + "/pokemon/" + nameOrId.toLowerCase().trim();
        log.debug("Fetching pokemon from: {}", url);

        try {
            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .timeout(Duration.ofSeconds(timeoutSeconds))
                .GET()
                .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() != 200) {
                log.warn("PokeAPI returned status {} for pokemon '{}'", response.statusCode(), nameOrId);
                return null;
            }

            return parseResponse(response.body());

        } catch (IOException | InterruptedException e) {
            log.error("Failed to fetch pokemon '{}': {}", nameOrId, e.getMessage());
            if (e instanceof InterruptedException) {
                Thread.currentThread().interrupt();
            }
            return null;
        }
    }

    private PokemonData parseResponse(String json) {
        JsonObject root = JsonParser.parseString(json).getAsJsonObject();
        PokemonData data = new PokemonData();

        data.setId(root.get("id").getAsInt());
        data.setName(root.get("name").getAsString());
        data.setHeight(root.get("height").getAsInt());
        data.setWeight(root.get("weight").getAsInt());

        // official artwork image
        try {
            String imageUrl = root
                .getAsJsonObject("sprites")
                .getAsJsonObject("other")
                .getAsJsonObject("official-artwork")
                .get("front_default").getAsString();
            data.setImageUrl(imageUrl);
        } catch (Exception e) {
            // fallback to default sprite
            data.setImageUrl(root.getAsJsonObject("sprites").get("front_default").getAsString());
        }

        // types: ["electric", "fire", ...]
        List<String> types = new ArrayList<>();
        for (var entry : root.getAsJsonArray("types")) {
            types.add(entry.getAsJsonObject().getAsJsonObject("type").get("name").getAsString());
        }
        data.setTypes(types);

        // abilities (non-hidden only)
        List<String> abilities = new ArrayList<>();
        for (var entry : root.getAsJsonArray("abilities")) {
            JsonObject abilityEntry = entry.getAsJsonObject();
            if (!abilityEntry.get("is_hidden").getAsBoolean()) {
                abilities.add(abilityEntry.getAsJsonObject("ability").get("name").getAsString());
            }
        }
        data.setAbilities(abilities);

        // stats: {hp: 45, attack: 49, ...}
        Map<String, Integer> stats = new LinkedHashMap<>();
        JsonArray statsArray = root.getAsJsonArray("stats");
        for (var entry : statsArray) {
            JsonObject statEntry = entry.getAsJsonObject();
            String statName = statEntry.getAsJsonObject("stat").get("name").getAsString();
            int baseStat = statEntry.get("base_stat").getAsInt();
            stats.put(statName, baseStat);
        }
        data.setStats(stats);

        return data;
    }
}
