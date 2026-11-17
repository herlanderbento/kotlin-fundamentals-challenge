sealed class TaskResult {
    data class Success(val message: String) : TaskResult()
    data class Error(val message: String) : TaskResult()
}

class TaskManager(
    private val repository: ITaskManagerRepository<Task>
){
    fun addTask(title: String, description: String? = null): TaskResult {
        return try {
            require(title.isNotEmpty()){
                "O título da tarefa não pode estar vazio."
            }

            val task = Task(
                title = title,
                description = description,
            )

            repository.insert(task)

            TaskResult.Success(
                "Tarefa adicionada com sucesso! ID: ${task.id}"
            )

        }catch (exception: IllegalArgumentException) {
            TaskResult.Error(exception.message ?: "Erro ao adicionar tarefa.")
        }
    }

    fun getAllTasks(): List<Pair<String, Boolean>> {
        val tasks = repository.findAll(null)

        println("DEBUG: ${tasks.size} tasks found")

        return tasks.map { task ->
            task.title to task.isCompleted
        }
    }

    fun findTaskById(id: Int): Task? {
        return repository.findById(id)
    }

    fun updateTaskStatus(id: Int, isCompleted: Boolean): TaskResult {
        return try {
            val task = requireNotNull(repository.findById(id)){
                "Tarefa com ID $id não encontrada."
            }

            repository.update(task.copy(isCompleted = isCompleted))

            TaskResult.Success(
                "Status da tarefa ID $id atualizado para $isCompleted"
            )
        }  catch (exception: IllegalArgumentException) {
            TaskResult.Error(exception.message ?: "Erro ao atualizar tarefa.")
        }
    }

    fun deleteTask(id: Int): TaskResult {
        return try {
            requireNotNull(repository.findById(id)) {
                "Tarefa com ID $id não encontrada."
            }

            repository.delete(id)

            TaskResult.Success(
                "Tarefa ID $id excluída com sucesso!"
            )
        } catch (exception: IllegalArgumentException) {
            TaskResult.Error(exception.message ?: "Erro ao excluir tarefa.")
        }
    }

    fun getCompletedTasks(): List<Task> {
        return repository
            .findAll(null).filter { task -> task.isCompleted }
    }

    fun getPendingTasks(): List<Task> {
        return repository
            .findAll(null).filter { task -> !task.isCompleted }
    }

    val taskCount: Int
        get() = repository.findAll(null).size
}