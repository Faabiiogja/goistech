# CLAUDE.md — Gois Tech AEM Project

## Project Overview

AEM as a Cloud Service (AEMaaCS) project for **Gois Tech**, generated with `aem-project-archetype`.

- **Group ID:** `com.goistech`
- **Artifact ID:** `goistech`
- **Version:** `1.0.0-SNAPSHOT`
- **AEM Version:** cloud (AEMaaCS)
- **AEM SDK:** `2026.3.25194.20260330T181734Z-260300`
- **Core WCM Components:** `2.28.0`
- **Frontend:** React SPA (Adobe SPA SDK)
- **Data Layer:** enabled
- **Dispatcher config:** included

## Module Structure

| Module | Purpose |
|---|---|
| `core/` | Java bundle — OSGi services, Sling Models, servlets, schedulers, listeners, filters |
| `ui.apps/` | JCR content under `/apps` — components, templates, clientlibs |
| `ui.apps.structure/` | Repository structure package for AEMaaCS |
| `ui.content/` | Sample content using the components |
| `ui.config/` | Runmode-specific OSGi configs |
| `ui.frontend/` | React SPA build (CRA + aem-clientlib-generator) |
| `all/` | Embeds all modules into a single deployable content package |
| `it.tests/` | Java-based integration tests (HTTP against AEM API) |
| `ui.tests/` | Cypress end-to-end UI tests |
| `dispatcher/` | Dispatcher config and filters |

## Key Paths

- **Java package root:** `com.goistech.core`
- **Java source:** `core/src/main/java/com/goistech/core/`
  - `models/` — Sling Models
  - `servlets/` — Servlets
  - `schedulers/` — Scheduled tasks
  - `listeners/` — Resource change listeners
  - `filters/` — Request filters
- **Frontend source:** `ui.frontend/src/`
  - React components under `components/`
- **AEM components:** `ui.apps/src/main/content/jcr_root/apps/goistech/components/`

## Build Commands

```bash
# Build everything
mvn clean install

# Build + deploy to local author (port 4502)
mvn clean install -PautoInstallSinglePackage

# Build + deploy to local publish (port 4503)
mvn clean install -PautoInstallSinglePackagePublish

# Deploy only the OSGi bundle
mvn clean install -PautoInstallBundle

# Deploy only a single content package (run from submodule dir)
mvn clean install -PautoInstallPackage

# Unit tests only
mvn clean test

# Integration tests (requires local AEM running)
mvn clean verify -Plocal
```

## Local AEM Instances

| Instance | URL | User | Password |
|---|---|---|---|
| Author | `http://localhost:4502` | `admin` | `admin` |
| Publish | `http://localhost:4503` | `admin` | `admin` |

## Frontend Development

```bash
# Dev server (proxies to local AEM author)
cd ui.frontend && npm start

# Build and sync clientlibs
cd ui.frontend && npm run build

# Live sync to ui.apps (requires aemsync)
cd ui.frontend && npm run sync
```

## Tech Stack

- **Backend:** Java 11+, OSGi (Felix/SCR annotations), Sling Models, Apache Sling, Apache Felix
- **Build:** Maven 3, BND 6.4.0
- **Frontend:** React 16, Create React App, Adobe SPA SDK (`@adobe/aem-spa-page-model-manager`, `@adobe/aem-react-editable-components`)
- **UI Testing:** Cypress
- **Package mgr:** npm (frontend)

## AEMaaCS Specifics

- No mutable code under `/apps` that isn't bundled — use `ui.apps.structure` for repo init
- OSGi configs must be runmode-aware (placed in `ui.config`)
- Dispatcher rules are in `dispatcher/src/conf.dispatcher.d/`
- Use `analyse` profile to validate packages before cloud deployment
