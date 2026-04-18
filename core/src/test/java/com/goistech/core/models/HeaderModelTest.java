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

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;

@ExtendWith(AemContextExtension.class)
class HeaderModelTest {

    private final AemContext context = new AemContext();

    @BeforeEach
    void setUp() {
        context.addModelsForClasses(HeaderModel.class);
        context.create().page("/content/goistech/test-page");
        context.currentPage("/content/goistech/test-page");
    }

    @Test
    void testWithCompleteData() {
        context.create().resource("/content/goistech/test-page/jcr:content/header",
            "sling:resourceType", HeaderModel.RESOURCE_TYPE,
            "logoAltText", "Gois Tech Logo",
            "ctaText", "Fale Conosco",
            "ctaLink", "/content/goistech/contato");

        context.currentResource("/content/goistech/test-page/jcr:content/header");
        HeaderModel model = context.request().adaptTo(HeaderModel.class);

        assertNotNull(model);
        assertEquals("Gois Tech Logo", model.getLogoAltText());
        assertEquals("Fale Conosco", model.getCtaText());
        assertEquals("/content/goistech/contato", model.getCtaLink());
        assertEquals(HeaderModel.RESOURCE_TYPE, model.getExportedType());
    }

    @Test
    void testLogoAltText_DefaultsToGoisTech_WhenNotSet() {
        context.create().resource("/content/goistech/test-page/jcr:content/header",
            "sling:resourceType", HeaderModel.RESOURCE_TYPE);

        context.currentResource("/content/goistech/test-page/jcr:content/header");
        HeaderModel model = context.request().adaptTo(HeaderModel.class);

        assertNotNull(model);
        assertEquals("Gois Tech", model.getLogoAltText());
    }

    @Test
    void testCtaText_DefaultsToFaleConosco_WhenNotSet() {
        context.create().resource("/content/goistech/test-page/jcr:content/header",
            "sling:resourceType", HeaderModel.RESOURCE_TYPE);

        context.currentResource("/content/goistech/test-page/jcr:content/header");
        HeaderModel model = context.request().adaptTo(HeaderModel.class);

        assertNotNull(model);
        assertEquals("Fale Conosco", model.getCtaText());
        assertNull(model.getCtaLink());
    }
}
