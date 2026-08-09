package app.template.patches.findthefire.premium

import app.morphe.patcher.patch.bytecodePatch
import app.morphe.patcher.patch.Compatibility
import app.morphe.patcher.patch.AppTarget
import app.morphe.patcher.patch.ApkFileType
import app.template.patches.shared.returnEarly

val FIND_THE_FIRE_COMPATIBILITY = Compatibility(
    name = "Skimboarding",
    packageName = "com.findthefirellc.findthefire",
    apkFileType = ApkFileType.XAPK,
    appIconColor = 0xFF6B35,
    targets = listOf(
        AppTarget(version = "1.0.0", versionCode = 19)
    )
)

@Suppress("unused")
val findTheFireUnlockFirePassPatch = bytecodePatch(
    name = "Unlock FirePass Premium",
    description = "Unlocks FirePass premium features in Skimboarding: spot filtering by skill level, advanced map features, and session log tools.",
) {
    compatibleWith(FIND_THE_FIRE_COMPATIBILITY)

    execute {
        EntitlementInfoIsActiveFingerprint.method.returnEarly(true)
    }
}
