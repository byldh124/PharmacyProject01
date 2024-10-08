pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        //maven { url = uri("https://naver.jfrog.io/artifactory/maven/") }
        maven {url = uri("https://repository.map.naver.com/archive/maven")}
    }
}

rootProject.name = "PharmacyProject01"
include(":app")