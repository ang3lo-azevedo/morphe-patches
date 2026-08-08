package morphe.patches

class ExamplePatch : Patch {
    override val name = "example-patch"
    override val description = "An example patch demonstrating the patch structure."
    override val compatiblePackages = listOf(
        CompatiblePackage("com.example.app", listOf("1.0.0"))
    )
    override val options = listOf(
        PatchOption("enabled", true, "Enabled", "Enable or disable this patch"),
        PatchOption("mode", "default", "Mode", "Sets the patch mode")
    )

    override fun execute(context: PatchContext): PatchResult {
        val enabled = context.options["enabled"] as? Boolean ?: true
        if (!enabled) {
            return PatchResult.Success("Patch is disabled, skipping.")
        }
        return PatchResult.Success("Example patch applied to ${context.apkPath}")
    }
}
