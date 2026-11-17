//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
fun main() {

    val repository = TaskManagerRepository()
    val taskManager = TaskManager(repository)

    while (true){
        println(
            """

            ===== TASK MANAGER =====

            1. Add task
            2. List all tasks
            3. Find task by ID
            4. Update task status
            5. Delete task
            6. List completed tasks
            7. List pending tasks
            8. Task count
            0. Exit

            Choose an option:
            """.trimIndent()
        )

        when (readlnOrNull()?.toIntOrNull()) {

            1 -> {
                print("Task title: ")
                val title = readln()

                print("Task description: ")
                val description = readln().ifBlank { null }

                println(taskManager.addTask(title, description))
            }

            2 -> {
                println("\nTasks:")

                for ((title, isCompleted) in taskManager.getAllTasks()) {
                    println("($title, $isCompleted)")
                }
            }

            3 -> {
                print("Enter task ID: ")
                val id = readln().toIntOrNull()

                if (id != null) {
                    println(taskManager.findTaskById(id)?.toFormattedString()
                        ?: "Task not found.")
                } else {
                    println("Invalid ID.")
                }
            }

            4 -> {
                print("Enter task ID: ")
                val id = readln().toIntOrNull()

                print("Is completed? (true/false): ")
                val isCompleted = readln().toBooleanStrictOrNull()

                if (id != null && isCompleted != null) {
                    println(taskManager.updateTaskStatus(id, isCompleted))
                } else {
                    println("Invalid input.")
                }
            }

            5 -> {
                print("Enter task ID: ")
                val id = readln().toIntOrNull()

                if (id != null) {
                    println(taskManager.deleteTask(id))
                } else {
                    println("Invalid ID.")
                }
            }

            6 -> {
                println("\nCompleted tasks:")

                taskManager.getCompletedTasks().forEach {
                    println(it.toFormattedString())
                }
            }

            7 -> {
                println("\nPending tasks:")

                taskManager.getPendingTasks().forEach {
                    println(it.toFormattedString())
                }
            }

            8 -> {
                println("Total tasks: ${taskManager.taskCount}")
            }

            0 -> {
                println("Goodbye!")
                break
            }

            else -> println("Invalid option. Try again.")
        }
    }
}