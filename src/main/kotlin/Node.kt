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

class StringExtractionNode(
    private val getLiteral: (MutableMap<String, Any?>) -> String,
) : StringFunctionNode {
    override fun eval(
        json: MutableMap<String, Any?>
    ): String = getLiteral(json)
}

class AndNode(
    private val lhs: Node,
    private val rhs: Node,
) : BooleanFunctionNode {

    override fun eval(json: MutableMap<String, Any?>): Boolean {
        val l = if (lhs is LiteralNode<*>) {
            (lhs as LiteralNode<Boolean>).value
        } else {
            (lhs as BooleanFunctionNode).eval(json)
        }

        val r = if (rhs is LiteralNode<*>) {
            (rhs as LiteralNode<Boolean>).value
        } else {
            (lhs as BooleanFunctionNode).eval(json)
        }

        return l && r
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
            (lhs as StringExtractionNode).eval(json)
        }

        val r = if (rhs is LiteralNode<*>) {
            (rhs as LiteralNode<String>).value
        } else {
            (lhs as StringExtractionNode).eval(json)
        }

        return l.contains(r)
    }
}