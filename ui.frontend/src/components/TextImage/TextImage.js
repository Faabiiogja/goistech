/*~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
 ~ Copyright 2020 Adobe Systems Incorporated
 ~
 ~ Licensed under the Apache License, Version 2.0 (the "License");
 ~ you may not use this file except in compliance with the License.
 ~ You may obtain a copy of the License at
 ~
 ~     http://www.apache.org/licenses/LICENSE-2.0
 ~
 ~ Unless required by applicable law or agreed to in writing, software
 ~ distributed under the License is distributed on an "AS IS" BASIS,
 ~ WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 ~ See the License for the specific language governing permissions and
 ~ limitations under the License.
 ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/

import React, { Component } from 'react';
import sanitizeHtml from 'sanitize-html';
import sanitizeWhiteList from '../sanitize-html.whitelist';

require('./TextImage.css');

/**
 * TextImage React component.
 * Props (from AEM JSON model):
 *   title        {string} - Component title
 *   text         {string} - Rich text body (HTML)
 *   imagePosition {string} - "left" | "right"
 *   imageReference {string} - DAM asset path
 *   ctaLink      {string} - CTA destination path
 *   ctaLabel     {string} - CTA button label
 */
class TextImage extends Component {
    render() {
        const {
            title,
            text,
            imagePosition = 'left',
            imageReference,
            ctaLink,
            ctaLabel
        } = this.props;

        return (
            <section className={`cmp-text-image cmp-text-image--image-${imagePosition}`}>

                <div className="cmp-text-image__image">
                    {imageReference && (
                        <img
                            src={imageReference}
                            alt={title || ''}
                            loading="lazy"
                        />
                    )}
                </div>

                <div className="cmp-text-image__content">
                    {title && (
                        <h2 className="cmp-text-image__title">{title}</h2>
                    )}

                    {text && (
                        <div
                            className="cmp-text-image__text"
                            dangerouslySetInnerHTML={{
                                __html: sanitizeHtml(text, sanitizeWhiteList)
                            }}
                        />
                    )}

                    {ctaLink && ctaLabel && (
                        <a href={ctaLink} className="cmp-text-image__cta">
                            {ctaLabel}
                        </a>
                    )}
                </div>

            </section>
        );
    }
}

export default TextImage;
