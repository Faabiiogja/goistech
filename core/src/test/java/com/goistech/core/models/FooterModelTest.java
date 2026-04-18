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
class FooterModelTest {

    private final AemContext context = new AemContext();

    @BeforeEach
    void setUp() {
        context.addModelsForClasses(FooterModel.class);
        context.create().page("/content/goistech/test-page");
        context.currentPage("/content/goistech/test-page");
    }

    @Test
    void testWithCompleteData() {
        context.create().resource("/content/goistech/test-page/jcr:content/footer",
            "sling:resourceType", FooterModel.RESOURCE_TYPE,
            "linkedinUrl", "https://linkedin.com/company/goistech",
            "githubUrl", "https://github.com/goistech",
            "instagramUrl", "https://instagram.com/goistech",
            "copyrightText", "© 2026 Gois Tech.");

        context.currentResource("/content/goistech/test-page/jcr:content/footer");
        FooterModel model = context.request().adaptTo(FooterModel.class);

        assertNotNull(model);
        assertEquals("https://linkedin.com/company/goistech", model.getLinkedinUrl());
        assertEquals("https://github.com/goistech", model.getGithubUrl());
        assertEquals("https://instagram.com/goistech", model.getInstagramUrl());
        assertEquals("© 2026 Gois Tech.", model.getCopyrightText());
        assertEquals(FooterModel.RESOURCE_TYPE, model.getExportedType());
    }

    @Test
    void testCopyrightText_DefaultsWhenNotSet() {
        context.create().resource("/content/goistech/test-page/jcr:content/footer",
            "sling:resourceType", FooterModel.RESOURCE_TYPE);

        context.currentResource("/content/goistech/test-page/jcr:content/footer");
        FooterModel model = context.request().adaptTo(FooterModel.class);

        assertNotNull(model);
        assertNotNull(model.getCopyrightText());
        assertTrue(model.getCopyrightText().contains("Gois Tech"));
    }

    @Test
    void testWithNoSocialLinks() {
        context.create().resource("/content/goistech/test-page/jcr:content/footer",
            "sling:resourceType", FooterModel.RESOURCE_TYPE,
            "copyrightText", "© 2026 Gois Tech.");

        context.currentResource("/content/goistech/test-page/jcr:content/footer");
        FooterModel model = context.request().adaptTo(FooterModel.class);

        assertNotNull(model);
        assertNull(model.getLinkedinUrl());
        assertNull(model.getGithubUrl());
        assertNull(model.getInstagramUrl());
    }
}
