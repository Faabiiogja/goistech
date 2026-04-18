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

import static org.junit.jupiter.api.Assertions.*;

import org.apache.sling.api.resource.Resource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import com.day.cq.wcm.api.Page;
import io.wcm.testing.mock.aem.junit5.AemContext;
import io.wcm.testing.mock.aem.junit5.AemContextExtension;

@ExtendWith(AemContextExtension.class)
class TextImageModelTest {

    private final AemContext context = new AemContext();

    private Page page;
    private Resource componentResource;

    @BeforeEach
    void setUp() {
        context.addModelsForClasses(TextImageModel.class);
        page = context.create().page("/content/goistech/test-page");
    }

    @Test
    void testWithCompleteData() {
        componentResource = context.create().resource(page, "text-image",
            "sling:resourceType", "goistech/components/text-image",
            "title", "Test Title",
            "text", "<p>Test text content</p>",
            "imagePosition", "right",
            "ctaLink", "/content/goistech/page",
            "ctaLabel", "Learn More");

        context.currentResource(componentResource);
        TextImageModel model = context.request().adaptTo(TextImageModel.class);

        assertNotNull(model);
        assertEquals("Test Title", model.getTitle());
        assertEquals("<p>Test text content</p>", model.getText());
        assertEquals("right", model.getImagePosition());
        assertEquals("/content/goistech/page", model.getCtaLink());
        assertEquals("Learn More", model.getCtaLabel());
        assertTrue(model.hasContent());
    }

    @Test
    void testWhenEmpty() {
        componentResource = context.create().resource(page, "text-image",
            "sling:resourceType", "goistech/components/text-image");

        context.currentResource(componentResource);
        TextImageModel model = context.request().adaptTo(TextImageModel.class);

        assertNotNull(model);
        assertNull(model.getTitle());
        assertNull(model.getText());
        assertNull(model.getCtaLink());
        assertNull(model.getCtaLabel());
        assertFalse(model.hasContent());
    }

    @Test
    void testWithPartialData_TitleOnly() {
        componentResource = context.create().resource(page, "text-image",
            "sling:resourceType", "goistech/components/text-image",
            "title", "Only Title");

        context.currentResource(componentResource);
        TextImageModel model = context.request().adaptTo(TextImageModel.class);

        assertNotNull(model);
        assertEquals("Only Title", model.getTitle());
        assertNull(model.getText());
        assertTrue(model.hasContent());
    }

    @Test
    void testDefaultImagePosition() {
        componentResource = context.create().resource(page, "text-image",
            "sling:resourceType", "goistech/components/text-image",
            "title", "Title");

        context.currentResource(componentResource);
        TextImageModel model = context.request().adaptTo(TextImageModel.class);

        assertNotNull(model);
        assertEquals("left", model.getImagePosition());
    }

    @Test
    void testImagePositionRight() {
        componentResource = context.create().resource(page, "text-image",
            "sling:resourceType", "goistech/components/text-image",
            "title", "Title",
            "imagePosition", "right");

        context.currentResource(componentResource);
        TextImageModel model = context.request().adaptTo(TextImageModel.class);

        assertNotNull(model);
        assertEquals("right", model.getImagePosition());
    }

    @Test
    void testImageReference() {
        componentResource = context.create().resource(page, "text-image",
            "sling:resourceType", "goistech/components/text-image",
            "title", "Title");
        context.create().resource(componentResource, "image",
            "fileReference", "/content/dam/goistech/image.jpg");

        context.currentResource(componentResource);
        TextImageModel model = context.request().adaptTo(TextImageModel.class);

        assertNotNull(model);
        assertEquals("/content/dam/goistech/image.jpg", model.getImageReference());
    }

    @Test
    void testImageReferenceWhenNoImageChild() {
        componentResource = context.create().resource(page, "text-image",
            "sling:resourceType", "goistech/components/text-image",
            "title", "Title");

        context.currentResource(componentResource);
        TextImageModel model = context.request().adaptTo(TextImageModel.class);

        assertNotNull(model);
        assertNull(model.getImageReference());
    }
}
