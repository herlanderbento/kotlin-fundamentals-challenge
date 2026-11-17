interface ITaskManagerRepository<T> {
    fun insert(data: T)
    fun update(data: T)
    fun findAll(isCompleted: Boolean?): List<T>
    fun findById(id: Int): T?
    fun delete(id: Int)
}