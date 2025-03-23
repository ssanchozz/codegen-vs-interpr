package org.example


val programs = setOf("bank app", "notepad")
val commands = mapOf(
    "bank app" to "./bank_app --password=SUPERSECRET",
    "notepad" to "./notepad file.txt",
)

fun extractField(field: String): (MutableMap<String, Any?>) -> String {
    return { json: MutableMap<String, Any?> -> (json[field] as String?) ?: "" }
}