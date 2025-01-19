package org.example

fun codegen(json: MutableMap<String, Any?>) {
    if ((json["a"] as String?)?.contains("a") == true && (json["b"] as String?)?.contains("b") == true) {
        json.computeIfAbsent("C") { 0 }
        json.computeIfPresent("C") { _, v -> if (v is Int) v + 1 else v }
    }
}

fun interpretation(json: MutableMap<String, Any?>) {
    if (INTERPRETATION_TREE.eval(json)) {
        json.computeIfAbsent("I") { 0 }
        json.computeIfPresent("I") { _, v -> if (v is Int) v + 1 else v }
    }
}

private val INTERPRETATION_TREE = AndNode(
    ContainsFunctionNode(
        StringExtractionNode { json -> (json["a"] as String?) ?: "" },
        StringNode("a"),
    ),
    ContainsFunctionNode(
        StringExtractionNode { json -> (json["b"] as String?) ?: "" },
        StringNode("b"),
    )
)