package org.example

fun codegen(json: MutableMap<String, Any?>) {
    if (
        (json["event"] as String?)?.contains("run") == true
        && (json["program"] as String?)?.contains("bank") == true
        && (json["command"] as String?)?.contains("password") == true
    ) {
        json.computeIfPresent("command") { _, v ->
            (v as String).replace("SUPERSECRET", "********")
        }
    }
}

fun interpretation(json: MutableMap<String, Any?>) {
    if (
        INTERPRETATION_TREE.eval(json)
    ) {
        json.computeIfPresent("command") { _, v ->
            (v as String).replace("SUPERSECRET", "********")
        }
    }
}

private val INTERPRETATION_TREE =
    AndNode(
        ContainsFunctionNode(
            StringFieldNode("event"),
            StringNode("run"),
        ),
        ContainsFunctionNode(
            StringFieldNode("program"),
            StringNode("bank"),
        ),
        ContainsFunctionNode(
            StringFieldNode("command"),
            StringNode("password"),
        )
    )
