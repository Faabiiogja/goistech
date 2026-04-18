/*
 *  Copyright 2015 Adobe Systems Incorporated
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.goistech.core.models;

import javax.annotation.PostConstruct;

import com.adobe.cq.export.json.ComponentExporter;
import com.adobe.cq.export.json.ExporterConstants;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.Default;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Exporter;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ChildResource;
import org.apache.sling.models.annotations.injectorspecific.Self;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

/**
 * Sling Model for the Text Image component.
 * Exports JSON for the React SPA and provides text, image position, and CTA properties.
 */
@Model(
    adaptables = SlingHttpServletRequest.class,
    adapters = {TextImageModel.class, ComponentExporter.class},
    resourceType = TextImageModel.RESOURCE_TYPE,
    defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL
)
@Exporter(
    name = ExporterConstants.SLING_MODEL_EXPORTER_NAME,
    extensions = ExporterConstants.SLING_MODEL_EXTENSION
)
public class TextImageModel implements ComponentExporter {

    static final String RESOURCE_TYPE = "goistech/components/text-image";

    @Self
    private SlingHttpServletRequest request;

    @ValueMapValue
    private String title;

    @ValueMapValue
    private String text;

    @ValueMapValue
    @Default(values = "left")
    private String imagePosition;

    @ValueMapValue
    private String ctaLink;

    @ValueMapValue
    private String ctaLabel;

    @ChildResource(name = "image")
    private Resource imageResource;

    private String imageReference;

    @PostConstruct
    protected void init() {
        if (imageResource != null) {
            imageReference = imageResource.getValueMap().get("fileReference", String.class);
        }
    }

    /**
     * Gets the title.
     * @return the title, or null if not set
     */
    public String getTitle() {
        return title;
    }

    /**
     * Gets the rich text body.
     * @return the text HTML, or null if not set
     */
    public String getText() {
        return text;
    }

    /**
     * Gets the image position (left or right).
     * @return "left" or "right"
     */
    public String getImagePosition() {
        return imagePosition;
    }

    /**
     * Gets the image DAM reference path.
     * @return the fileReference from the embedded image child node, or null
     */
    public String getImageReference() {
        return imageReference;
    }

    /**
     * Gets the CTA link path or URL.
     * @return the CTA link, or null if not set
     */
    public String getCtaLink() {
        return ctaLink;
    }

    /**
     * Gets the CTA button label.
     * @return the CTA label, or null if not set
     */
    public String getCtaLabel() {
        return ctaLabel;
    }

    /**
     * Returns true if the component has enough content to render.
     * @return true if title or text is present
     */
    @JsonIgnore
    public boolean hasContent() {
        return title != null || text != null;
    }

    @Override
    @JsonProperty(":type")
    public String getExportedType() {
        return RESOURCE_TYPE;
    }
}
