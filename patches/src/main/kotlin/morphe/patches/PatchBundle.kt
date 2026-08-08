package morphe.patches

object PatchBundle {
    private val patches = mutableListOf<Patch>()

    fun register(patch: Patch) {
        patches.add(patch)
    }

    fun all(): List<Patch> = patches.toList()

    fun findByName(name: String): Patch? = patches.find { it.name == name }

    fun forPackage(packageName: String): List<Patch> =
        patches.filter { patch ->
            patch.compatiblePackages.any { it.name == packageName }
        }
}
