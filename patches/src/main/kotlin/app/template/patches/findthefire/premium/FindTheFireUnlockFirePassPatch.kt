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

/**
 * Find the Fire (Skimboarding) — Unlock FirePass Premium
 *
 * Architecture: Expo / React Native app with RevenueCat Purchases SDK
 * for subscription management. The premium tier is called "FirePass".
 *
 * RevenueCat entitlement: "firepass" (or similar identifier configured
 * in the RevenueCat dashboard). The JS side checks:
 *
 *   Purchases.getCustomerInfo().then(info => {
 *     const isFirePass = info.entitlements.active.firepass !== undefined;
 *   });
 *
 * Patch strategy:
 *
 *   Single point of truth: EntitlementInfo.isActive()
 *
 *   RevenueCat's EntitlementInfo class has a boolean `isActive` field that
 *   indicates whether the entitlement is currently valid (subscription active,
 *   not expired, not revoked). The getter reads this field directly.
 *
 *   By forcing isActive() to always return true, we make ALL entitlements
 *   appear active. The EntitlementInfos constructor uses this method to
 *   build its `active` map, so the FirePass entitlement will appear in
 *   the active entitlements list.
 *
 *   This cascades through the entire stack:
 *
 *     EntitlementInfo.isActive() → true (patched here)
 *       ↓
 *     EntitlementInfos constructor → includes in `active` map
 *       ↓
 *     CustomerInfo.entitlements → EntitlementInfos with active FirePass
 *       ↓
 *     CustomerInfoMapperKt.map() → JS-compatible WritableMap
 *       ↓
 *     RNPurchasesModule → sends to JavaScript via React Native bridge
 *       ↓
 *     JS: info.entitlements.active.firepass → EntitlementInfo { isActive: true }
 *
 *   Note: This requires that the "firepass" entitlement exists in the
 *   RevenueCat dashboard and is included in the CustomerInfo response.
 *   If the entitlement is completely absent for free users, an additional
 *   patch to CustomerInfoMapperKt.map() would be needed to inject the
 *   entitlement entry.
 */
@Suppress("unused")
val findTheFireUnlockFirePassPatch = bytecodePatch(
    name = "Unlock FirePass Premium",
    description = "Unlocks FirePass premium features in Skimboarding: spot filtering " +
        "by skill level, advanced map features, and session log tools.",
) {
    compatibleWith(FIND_THE_FIRE_COMPATIBILITY)

    execute {
        EntitlementInfoIsActiveFingerprint.method.returnEarly(true)
    }
}
