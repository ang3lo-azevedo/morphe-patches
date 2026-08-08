package morphe.extensions

import morphe.patches.Patch

interface Extension {
    val name: String
    val patches: List<Patch>
}
