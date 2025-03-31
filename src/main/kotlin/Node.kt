package org.example

interface Node

interface BooleanFunctionNode : Node {
    fun eval(json: MutableMap<String, Any?>): Boolean
}

interface StringFunctionNode : Node {
    fun eval(json: MutableMap<String, Any?>): String
}

interface LiteralNode<T> : Node {
    val value: T
}

class StringNode(
    override val value: String,
) : LiteralNode<String>

class StringFieldNode(
    private val fieldName: String,
) : StringFunctionNode {
    override fun eval(
        json: MutableMap<String, Any?>
    ): String = json[fieldName] as String
}

class AndNode(
    private vararg val args: Node,
) : BooleanFunctionNode {
    override fun eval(json: MutableMap<String, Any?>): Boolean {
        for (arg in args) {
            val res = if (arg is LiteralNode<*>) {
                (arg as LiteralNode<Boolean>).value
            } else {
                (arg as BooleanFunctionNode).eval(json)
            }
            if (!res) {
                return false
            }
        }
        return true
    }
}

class ContainsFunctionNode(
    private val lhs: Node,
    private val rhs: Node,
) : BooleanFunctionNode {

    override fun eval(json: MutableMap<String, Any?>): Boolean {
        val l = if (lhs is LiteralNode<*>) {
            (lhs as LiteralNode<String>).value
        } else {
            (lhs as StringFieldNode).eval(json)
        }

        val r = if (rhs is LiteralNode<*>) {
            (rhs as LiteralNode<String>).value
        } else {
            (lhs as StringFieldNode).eval(json)
        }

        return l.contains(r)
    }
}