package morphe.patches

data class PatchOption(
    val key: String,
    val default: Any? = null,
    val title: String = "",
    val description: String = ""
)

interface Patch {
    val name: String
    val description: String
    val compatiblePackages: List<CompatiblePackage>
    val options: List<PatchOption>

    fun execute(context: PatchContext): PatchResult
}

data class CompatiblePackage(
    val name: String,
    val versions: List<String> = emptyList()
)

data class PatchContext(
    val apkPath: String,
    val options: Map<String, Any?> = emptyMap()
)

sealed class PatchResult {
    data class Success(val message: String = "Patch applied successfully") : PatchResult()
    data class Error(val message: String) : PatchResult()
}
