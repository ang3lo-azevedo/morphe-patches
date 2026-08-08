package app.template.patches.findthefire.premium

import app.morphe.patcher.Fingerprint
import com.android.tools.smali.dexlib2.AccessFlags

/**
 * Targets EntitlementInfo.isActive()Z — the RevenueCat entitlement activation gate.
 *
 * EntitlementInfo is a Kotlin data class in the RevenueCat Purchases SDK.
 * The `isActive` field is `private final boolean`, returned by `isActive()`.
 *
 * Smali (classes4.dex):
 *   .method public final isActive()Z
 *       .registers 2
 *       iget-boolean v0, p0, Lcom/revenuecat/purchases/EntitlementInfo;->isActive:Z
 *       return v0
 *   .end method
 */
object EntitlementInfoIsActiveFingerprint : Fingerprint(
    returnType = "Z",
    accessFlags = listOf(AccessFlags.PUBLIC, AccessFlags.FINAL),
    parameters = listOf(),
    definingClass = "Lcom/revenuecat/purchases/EntitlementInfo;",
    name = "isActive",
)

/**
 * Targets EntitlementInfos.getActive() — returns the active entitlements map.
 *
 * The EntitlementInfos constructor builds two maps:
 *   - `all`  — all entitlements (from constructor parameter)
 *   - `active` — only those where isActive() is true
 *
 * For non-subscribers, RevenueCat may still include the "firepass" entitlement
 * in the `all` map with isActive=false. By patching getActive() to return
 * getAll() instead, combined with patching isActive() to always return true,
 * the FirePass entitlement appears active in JS.
 *
 * Smali (classes4.dex):
 *   .method public final getActive()Ljava/util/Map;
 *       .registers 2
 *       iget-object v0, p0, Lcom/revenuecat/purchases/EntitlementInfos;->active:Ljava/util/Map;
 *       return-object v0
 *   .end method
 */
object EntitlementInfosGetActiveFingerprint : Fingerprint(
    returnType = "Ljava/util/Map;",
    accessFlags = listOf(AccessFlags.PUBLIC, AccessFlags.FINAL),
    parameters = listOf(),
    definingClass = "Lcom/revenuecat/purchases/EntitlementInfos;",
    name = "getActive",
)
