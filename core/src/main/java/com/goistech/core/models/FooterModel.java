/*
 * Copyright 2026 Gois Tech
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.goistech.core.models;

import javax.annotation.PostConstruct;

import org.apache.sling.api.SlingHttpServletRequest;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Exporter;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.ValueMapValue;

import com.adobe.cq.export.json.ComponentExporter;
import com.adobe.cq.export.json.ExporterConstants;

@Model(
    adaptables = SlingHttpServletRequest.class,
    adapters = { FooterModel.class, ComponentExporter.class },
    resourceType = FooterModel.RESOURCE_TYPE,
    defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL
)
@Exporter(
    name = ExporterConstants.SLING_MODEL_EXPORTER_NAME,
    extensions = ExporterConstants.SLING_MODEL_EXTENSION
)
public class FooterModel implements ComponentExporter {

    static final String RESOURCE_TYPE = "goistech/components/footer";

    @ValueMapValue
    private String linkedinUrl;

    @ValueMapValue
    private String githubUrl;

    @ValueMapValue
    private String instagramUrl;

    @ValueMapValue
    private String copyrightText;

    @PostConstruct
    protected void init() {
        if (copyrightText == null) {
            copyrightText = "© 2026 Gois Tech. Todos os direitos reservados.";
        }
    }

    public String getLinkedinUrl() {
        return linkedinUrl;
    }

    public String getGithubUrl() {
        return githubUrl;
    }

    public String getInstagramUrl() {
        return instagramUrl;
    }

    public String getCopyrightText() {
        return copyrightText;
    }

    @Override
    public String getExportedType() {
        return RESOURCE_TYPE;
    }
}
