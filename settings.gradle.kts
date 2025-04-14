pluginManagement {
    repositories {
        maven("https://maven.aliyun.com/repository/public")
        maven("https://maven.aliyun.com/repository/google")
        maven ("https://jitpack.io")
        maven ("https://artifact.bytedance.com/repository/releases/")

        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        maven("https://maven.aliyun.com/repository/public")
        maven("https://maven.aliyun.com/repository/google")
        maven ("https://jitpack.io")
        maven ("https://artifact.bytedance.com/repository/releases/")
        
        google()
        mavenCentral()
    }
}

rootProject.name = "WanAndroidCompose"
include(":app")
include(":lib_base")
 