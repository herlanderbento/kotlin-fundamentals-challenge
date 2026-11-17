
class TaskManagerRepository: ITaskManagerRepository<Task>{

    private val repository = mutableListOf<Task>()

    override fun insert(data: Task) {
        repository.add(data)
    }

    override fun update(data: Task) {
        val index = repository.indexOfFirst { it.id == data.id }

        if (index != -1) {
            repository[index] = data
        }
    }

    override fun findAll(isCompleted: Boolean?): List<Task> {
        return repository.filter {
            isCompleted == null || it.isCompleted == isCompleted
        }.toList()
    }

    override fun findById(id: Int): Task? {
        return repository.find { it.id == id }
    }

    override fun delete(id: Int) {
        repository.removeIf { it.id == id }
    }
}