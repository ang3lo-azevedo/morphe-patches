# Morphe Patches

A patching framework for Android applications, inspired by [Morphe](https://github.com/MorpheApp/morphe-patches) and [ReVanced](https://github.com/ReVanced).

## Structure

```
morphe-patches/
├── patches/          # Core patching logic and patch definitions
├── extensions/       # Third-party and app-specific extensions
├── patches-list.json # Registred patch metadata
├── patches-bundle.json # Bundle configuration
├── build.gradle.kts  # Root Gradle build
└── settings.gradle.kts
```

## Getting Started

### Prerequisites

- JDK 17+
- Gradle (or use the included wrapper)

### Building

```bash
./gradlew build
```

### Running Tests

```bash
./gradlew test
```

## Creating a Patch

Implement the `Patch` interface from `morphe.patches`:

```kotlin
package morphe.patches

class MyPatch : Patch {
    override val name = "my-patch"
    override val description = "Description of what this patch does"
    override val compatiblePackages = listOf(
        CompatiblePackage("com.example.app", listOf("1.0.0"))
    )
    override val options = emptyList<PatchOption>()

    override fun execute(context: PatchContext): PatchResult {
        return PatchResult.Success("Patch applied!")
    }
}
```

## License

GPL-3.0
