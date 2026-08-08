package app.template.patches.findthefire.premium

import app.morphe.patcher.Fingerprint
import com.android.tools.smali.dexlib2.AccessFlags

/**
 * Targets EntitlementInfo.isActive()Z — the RevenueCat entitlement activation gate.
 *
 * EntitlementInfo is a Kotlin data class in the RevenueCat Purchases SDK.
 * The `isActive` field is `private final boolean`, set at construction time
 * and returned by the getter `isActive()`.
 *
 * When a user is NOT subscribed to FirePass, this returns false for the
 * FirePass entitlement (or there is no FirePass entry at all).
 * When subscribed, it returns true.
 *
 * By patching this getter to always return true, we make ALL entitlements
 * appear active, including FirePass. This cascades through:
 *   - EntitlementInfos constructor (filters active entitlements)
 *   - EntitlementInfos.getActive() → includes FirePass
 *   - CustomerInfo.entitlements → includes active FirePass
 *   - RNPurchasesModule (React Native bridge) → JS sees active FirePass
 *   - CustomerInfoMapperKt → maps to JS-compatible format with active=true
 *
 * Smali (classes4.dex)
 *   .class public final Lcom/revenuecat/purchases/EntitlementInfo;
 *   .field private final isActive:Z
 *
 *   .method public final isActive()Z
 *       .registers 2
 *       iget-boolean v0, p0, Lcom/revenuecat/purchases/EntitlementInfo;->isActive:Z
 *       return v0
 *   .end method
 *
 * Access flags: PUBLIC FINAL
 * Return type:  Z
 * Parameters:   none (instance method)
 * Source file:  EntitlementInfo.kt
 *
 * Patch: replace body with "const/4 v0, 0x1; return v0"
 */
object EntitlementInfoIsActiveFingerprint : Fingerprint(
    returnType = "Z",
    accessFlags = listOf(AccessFlags.PUBLIC, AccessFlags.FINAL),
    parameters = listOf(),
    definingClass = "Lcom/revenuecat/purchases/EntitlementInfo;",
    name = "isActive",
)
