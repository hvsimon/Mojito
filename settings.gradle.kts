// TODO: Remove below. Version catalogs is promoted to a stable feature in 7.4
enableFeaturePreview("VERSION_CATALOGS")
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        jcenter() // Warning: this repository is going to shut down soon
    }
}

rootProject.name = "Kiwi's Bar"
include(
    ":app",
    ":data",
    ":common-ui-resources",
    ":common-ui-compose",
    ":ui-onboarding",
    ":ui-recipe",
    ":ui-collection",
    ":ui-cocktail-list",
    ":ui-search",
    ":ui-about"
)