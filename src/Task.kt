import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


data class Task(
    val id: Int = generateId(),
    val title: String,
    val description: String? = null,
    val isCompleted: Boolean = false,
    val createdAt: LocalDateTime = LocalDateTime.now()
){
    fun toFormattedString(): String {
        val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")

        return """
            ID: $id
            Title: $title
            Description: ${description ?: "No description"}
            isCompleted: $isCompleted
            createdAt: ${createdAt.format(formatter)}"
        """.trimIndent()
    }

    companion object{
        private var nextId = 1

        private fun generateId(): Int = nextId++
    }
}