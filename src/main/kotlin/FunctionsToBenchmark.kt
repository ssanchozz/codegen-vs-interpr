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
        AndNode(
            ContainsFunctionNode(
                StringExtractionNode(extractField("event")),
                StringNode("run"),
            ),
            ContainsFunctionNode(
                StringExtractionNode(extractField("program")),
                StringNode("bank"),
            )
        ),
        ContainsFunctionNode(
            StringExtractionNode(extractField("command")),
            StringNode("password"),
        )
    )
