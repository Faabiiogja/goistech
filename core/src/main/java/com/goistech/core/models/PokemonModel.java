package com.goistech.core.models;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Exporter;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.OSGiService;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import com.adobe.cq.export.json.ComponentExporter;
import com.adobe.cq.export.json.ExporterConstants;
import com.goistech.core.services.PokemonApiService;

@Model(
    adaptables = SlingHttpServletRequest.class,
    adapters = { PokemonModel.class, ComponentExporter.class },
    resourceType = PokemonModel.RESOURCE_TYPE,
    defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL
)
@Exporter(
    name = ExporterConstants.SLING_MODEL_EXPORTER_NAME,
    extensions = ExporterConstants.SLING_MODEL_EXTENSION
)
public class PokemonModel implements ComponentExporter {

    static final String RESOURCE_TYPE = "goistech/components/pokemon";

    @ValueMapValue
    private String pokemonName;

    @OSGiService
    private PokemonApiService pokemonApiService;

    private PokemonData pokemonData;

    @PostConstruct
    protected void init() {
        if (pokemonName != null && !pokemonName.trim().isEmpty()) {
            pokemonData = pokemonApiService.getPokemon(pokemonName);
        }
    }

    public String getPokemonName() {
        return pokemonName;
    }

    public int getPokemonId() {
        return pokemonData != null ? pokemonData.getId() : 0;
    }

    public String getDisplayName() {
        if (pokemonData == null) return pokemonName;
        String name = pokemonData.getName();
        return name.substring(0, 1).toUpperCase() + name.substring(1);
    }

    public String getImageUrl() {
        return pokemonData != null ? pokemonData.getImageUrl() : null;
    }

    public int getHeight() {
        return pokemonData != null ? pokemonData.getHeight() : 0;
    }

    public int getWeight() {
        return pokemonData != null ? pokemonData.getWeight() : 0;
    }

    public List<String> getTypes() {
        return pokemonData != null ? pokemonData.getTypes() : Collections.emptyList();
    }

    public List<String> getAbilities() {
        return pokemonData != null ? pokemonData.getAbilities() : Collections.emptyList();
    }

    public Map<String, Integer> getStats() {
        return pokemonData != null ? pokemonData.getStats() : Collections.emptyMap();
    }

    public boolean isFound() {
        return pokemonData != null;
    }

    @Override
    public String getExportedType() {
        return RESOURCE_TYPE;
    }
}
